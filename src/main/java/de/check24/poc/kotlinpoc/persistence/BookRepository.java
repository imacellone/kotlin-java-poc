package de.check24.poc.kotlinpoc.persistence;

import de.check24.poc.kotlinpoc.model.Book;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookRepository {

    private static long CURRENT_ID = 0;
    private static final Map<Long, Book> BOOK_DATABASE = new HashMap<>();

    public Optional<Book> save(Book book) {
        if (book == null) {
            return Optional.empty();
        }
        if (book.getId() == null) {
            Long newId = ++CURRENT_ID;
            book.setId(newId);
        }
        BOOK_DATABASE.put(book.getId(), book);
        return Optional.of(book);
    }

    public Optional<Book> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(BOOK_DATABASE.get(id));
    }

    public List<Book> findAll() {
        return BOOK_DATABASE
                .values()
                .stream()
                .filter(book -> book.getId() != null)
                .sorted(Comparator.comparing(Book::getId))
                .toList();
    }

    public Optional<Book> deleteById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(BOOK_DATABASE.remove(id));
    }

    public static Long getLastUsedId() {
        return CURRENT_ID;
    }
}
