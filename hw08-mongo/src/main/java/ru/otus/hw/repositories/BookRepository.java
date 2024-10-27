package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, String> {

    @Nonnull
    List<Book> findAll();

    @Nonnull
    Optional<Book> findById(@Nonnull String id);
}
