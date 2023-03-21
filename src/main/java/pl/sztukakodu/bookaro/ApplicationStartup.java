package pl.sztukakodu.bookaro;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import pl.sztukakodu.bookaro.catalog.domain.Book;
import pl.sztukakodu.bookaro.catalog.application.port.CatalogUseCase;
import pl.sztukakodu.bookaro.catalog.application.port.CatalogUseCase.CreateBookCommand;
import pl.sztukakodu.bookaro.order.application.port.PlaceOrderUseCase;
import pl.sztukakodu.bookaro.order.application.port.QueryOrderUseCase;
import pl.sztukakodu.bookaro.order.domain.OrderItem;
import pl.sztukakodu.bookaro.order.domain.Recipient;

import static pl.sztukakodu.bookaro.catalog.application.port.CatalogUseCase.*;
import static pl.sztukakodu.bookaro.order.application.port.PlaceOrderUseCase.*;

@Component
public class ApplicationStartup implements CommandLineRunner {
    private final CatalogUseCase catalog;
    private final PlaceOrderUseCase placeOrder;
    private final QueryOrderUseCase queryOrder;
    private final String title;
    private final String author;
    private final Long limit;

    public ApplicationStartup(
            CatalogUseCase catalog,
            PlaceOrderUseCase placeOrder,
            QueryOrderUseCase queryOrder,
            @Value("${bookaro.catalog.query}") String title,
            @Value("${bookaro.catalog.author}") String author,
            @Value("${bookaro.catalog.limit:3}") Long limit) {
        this.catalog = catalog;
        this.placeOrder = placeOrder;
        this.queryOrder = queryOrder;
        this.title = title;
        this.author = author;
        this.limit = limit;
    }

    @Override
    public void run(String... args) {
        initData();
        searchCatalog();
        placeOrder();
    }

    private void placeOrder() {
        Book panTadeusz = catalog.findOneByTitle("Pan Tadeusz")
                .orElseThrow(() -> new IllegalStateException("Cannot find a book"));
        Book chłopi = catalog.findOneByTitle("Chłopi")
                .orElseThrow(() -> new IllegalStateException("Cannot find a book"));

        Recipient recipient = Recipient
                .builder()
                .name("Jan Kowalski")
                .phone("123-456-789")
                .street("Armii Krajowej 1")
                .city("Kraków")
                .zipCode("30-150")
                .email("jan@example.com")
                .build();

        PlaceOrderCommand command = PlaceOrderCommand
                .builder()
                .recipient(recipient)
                .item(new OrderItem(panTadeusz, 16))
                .item(new OrderItem(chłopi, 7))
                .build();

        PlaceOrderResponse response = placeOrder.placeOrder(command);
        System.out.println("Created ORDER with id: " + response.getOrderId());

        queryOrder.findAll()
                .forEach(order -> {
                    System.out.println("GOT ORDER WITH TOTAL PRICE: " + order.totalPrice() + " DETAILS: " + order);
                });

    }

    private void searchCatalog() {
        findByTitle();
        findAndUpdate();
        findByTitle();
        findByAuthor();
    }

    private void initData() {
        catalog.addBook(new CreateBookCommand("Pan Tadeusz", "Adam Mickiewicz", 1834, new BigDecimal("19.90")));
        catalog.addBook(new CreateBookCommand("Ogniem i mieczem", "Henryk Sienkiewicz", 1884, new BigDecimal("29.90")));
        catalog.addBook(new CreateBookCommand("Chłopi", "Władysław Reymont", 1904, new BigDecimal("11.90")));
        catalog.addBook(new CreateBookCommand("Pan Wołodyjowski", "Henryk Sienkiewicz", 1887, new BigDecimal("14.90")));
    }

    private void findByAuthor() {
        List<Book> booksByAuthor = catalog.findByAuthor(author);
        System.out.println("findByAuthor");
        booksByAuthor.stream().limit(limit).forEach(System.out::println);
    }

    private void findAndUpdate() {
        System.out.println("Updating book...");
        catalog.findOneByTitleAndAuthor("Pan Tadeusz", "Adam Mickiewicz")
                .ifPresent(book -> {
                    UpdateBookCommand command = UpdateBookCommand
                            .builder()
                            .id(book.getId())
                            .title("Pan Tadeusz, czyli Ostatni Zajazd na Litwie")
                            .build();
                    UpdateBookResponse response = catalog.updateBook(command);
                    System.out.println("Updating book result: " + response.isSuccess());
                });
    }

    private void findByTitle() {
        List<Book> booksByTitle = catalog.findByTitle(title);
        System.out.println("findByTitle");
        booksByTitle.stream().limit(limit).forEach(System.out::println);
    }
}
