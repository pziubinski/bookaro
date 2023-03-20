package pl.sztukakodu.bookaro;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import pl.sztukakodu.bookaro.catalog.application.CatalogController;
import pl.sztukakodu.bookaro.catalog.domain.Book;

@Component
public class ApplicationStartup implements CommandLineRunner {
    private final CatalogController catalogController;
    private final String title;
    private final String author;
    private final Long limit;

    public ApplicationStartup(
            CatalogController catalogController,
            @Value("${bookaro.catalog.query}") String title,
            @Value("${bookaro.catalog.author}") String author,
            @Value("${bookaro.catalog.limit:3}") Long limit) {
        this.catalogController = catalogController;
        this.title = title;
        this.author = author;
        this.limit = limit;
    }

    @Override
    public void run(String... args) {
        List<Book> booksByTitle = catalogController.findByTitle(title);
        booksByTitle.stream().limit(limit).forEach(System.out::println);

        List<Book> booksByAuthor = catalogController.findByAuthor(author);
        booksByAuthor.stream().limit(limit).forEach(System.out::println);
    }
}
