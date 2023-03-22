package pl.sztukakodu.bookaro.catalog.application.port;

import lombok.Builder;
import lombok.Value;
import pl.sztukakodu.bookaro.catalog.domain.Book;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface CatalogUseCase {
    List<Book> findAll();

    Optional<Book> findById(Long id);

    Optional<Book> findOneByTitle(String title);

    List<Book> findByTitle(String title);

    Optional<Book> findOneByAuthor(String author);

    List<Book> findByAuthor(String author);

    Optional<Book> findOneByTitleAndAuthor(String title, String author);

    List<Book> findByTitleAndAuthor(String title, String author);

    void addBook(CreateBookCommand command);

    UpdateBookResponse updateBook(UpdateBookCommand command);

    void removeById(Long id);

    @Value
    class CreateBookCommand {
        String title;
        String author;
        Integer year;
        BigDecimal price;

        public Book toBook() {
            return new Book(title, author, year, price);
        }
    }

    @Value
    @Builder
    class UpdateBookCommand {
        Long id;
        String title;
        String author;
        Integer year;

       public Book updateFields(Book book) {
            if (title != null) {
                book.setTitle(title);
            }
            if (author != null) {
                book.setAuthor(author);
            }
            if (year != null) {
                book.setYear(year);
            }
            return book;
        }
    }

    @Value
    class UpdateBookResponse {
        public static UpdateBookResponse SUCCESS = new UpdateBookResponse(true, Collections.emptyList());
        boolean success;
        List<String> errors;
    }
}
