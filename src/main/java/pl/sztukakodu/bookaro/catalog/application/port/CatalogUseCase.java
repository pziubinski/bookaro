package pl.sztukakodu.bookaro.catalog.application.port;

import lombok.Value;
import pl.sztukakodu.bookaro.catalog.domain.Book;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface CatalogUseCase {
    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);

    List<Book> findAll();

    Optional<Book> findOneByTitleAndAuthor(String title, String author);

    void addBook(CreateBookCommand command);

    void removeById(Long id);

    UpdateBookResponse updateBook(UpdateBookCommand command);

    @Value
    class CreateBookCommand {
        String title;
        String author;
        Integer year;
    }

    @Value
    class UpdateBookCommand {
        Long id;
        String title;
        String author;
        Integer year;
    }

    @Value
    class UpdateBookResponse {
        public static UpdateBookResponse SUCCESS = new UpdateBookResponse(true, Collections.emptyList());
        boolean success;
        List<String> errors;
    }
}
