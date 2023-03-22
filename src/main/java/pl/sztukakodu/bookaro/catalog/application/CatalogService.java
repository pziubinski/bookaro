package pl.sztukakodu.bookaro.catalog.application;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sztukakodu.bookaro.catalog.application.port.CatalogUseCase;
import pl.sztukakodu.bookaro.catalog.domain.Book;
import pl.sztukakodu.bookaro.catalog.domain.CatalogRepository;

@Service
@AllArgsConstructor
class CatalogService implements CatalogUseCase {
    private final CatalogRepository repository;

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Book> findOneByTitle(String title) {
        return repository.findAll()
            .stream()
            .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
            .findFirst();
    }

    @Override
    public List<Book> findByTitle(String title) {
        return repository.findAll()
            .stream()
            .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findOneByAuthor(String author) {
        return repository.findAll()
            .stream()
            .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
            .findFirst();
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return repository.findAll()
            .stream()
            .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findOneByTitleAndAuthor(String title, String author) {
        return repository.findAll()
            .stream()
            .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
            .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
            .findFirst();
    }

    @Override
    public List<Book> findByTitleAndAuthor(String title, String author) {
        return repository.findAll()
            .stream()
            .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
            .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
            .collect(Collectors.toList());
    }

    @Override
    public Book addBook(CreateBookCommand command) {
        Book book = command.toBook();
        return repository.save(book);
    }

    @Override
    public void removeById(Long id) {
        repository.removeById(id);
    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookCommand command) {
        return repository.findById(command.getId())
            .map(book -> {
                Book updatedBook = command.updateFields(book);
                repository.save(updatedBook);
                return UpdateBookResponse.SUCCESS;
            })
            .orElseGet(() -> new UpdateBookResponse(false, Arrays.asList("Book not found with id: " + command.getId())));
    }
}
