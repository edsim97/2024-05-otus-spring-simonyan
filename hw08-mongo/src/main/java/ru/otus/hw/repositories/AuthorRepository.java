package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.models.Author;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, String> {

    @Nonnull
    List<Author> findAll();
}
