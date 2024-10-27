package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.hw.models.Genre;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, String> {

    @Nonnull
    List<Genre> findAll();

    @Nonnull
    List<Genre> findAllById(@Nonnull @Param("ids") Iterable<String> ids);
}
