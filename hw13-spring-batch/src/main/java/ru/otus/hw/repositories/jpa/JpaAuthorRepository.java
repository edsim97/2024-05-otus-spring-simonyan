package ru.otus.hw.repositories.jpa;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.jpa.JpaAuthor;

import java.util.List;
import java.util.Optional;

public interface JpaAuthorRepository extends JpaRepository<JpaAuthor, Long> {

    @Nonnull
    List<JpaAuthor> findAll();

    Optional<JpaAuthor> findById(long id);
}
