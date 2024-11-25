package ru.otus.hw.repositories.jpa;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import ru.otus.hw.models.jpa.JpaBook;

import java.util.List;
import java.util.Optional;

public interface JpaBookRepository extends JpaRepository<JpaBook, Long> {

    @EntityGraph(attributePaths = { "author", "genres" })
    Optional<JpaBook> findById(long id);

    @EntityGraph(attributePaths = { "author", "genres" })
    @Nonnull
    List<JpaBook> findAll();

    @Nonnull
    JpaBook save(JpaBook book);

    @Modifying
    void deleteById(long id);
}
