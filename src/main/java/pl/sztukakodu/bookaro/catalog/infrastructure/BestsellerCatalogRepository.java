package pl.sztukakodu.bookaro.catalog.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import pl.sztukakodu.bookaro.catalog.domain.Book;
import pl.sztukakodu.bookaro.catalog.domain.CatalogRepository;

@Repository
public class BestsellerCatalogRepository implements CatalogRepository {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    public BestsellerCatalogRepository() {
            storage.put(1L, new Book(1L, "Harry Potter i Komnata Tajemnic", "JK Rowling", 1998));
            storage.put(2L, new Book(2L, "Władca Pierścieni", "JRR Tolkien", 1954));
            storage.put(3L, new Book(3L, "Sezon Burz", "Andrzej Sapkowski", 2013));
        }

        @Override
        public List<Book> findAll() {
            return new ArrayList<>(storage.values());
        }
}
