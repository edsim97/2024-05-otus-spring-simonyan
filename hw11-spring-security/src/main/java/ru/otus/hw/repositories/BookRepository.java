package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = { "author", "genres" })
    Optional<Book> findById(long id);

    @EntityGraph(attributePaths = { "author", "genres" })
    @Nonnull
    List<Book> findAll();

    @Nonnull
    Book save(Book book);

    @Modifying
    void deleteById(long id);
}
