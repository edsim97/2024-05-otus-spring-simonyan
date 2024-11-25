package ru.otus.hw.repositories.jpa;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.jpa.JpaGenre;

import java.util.List;
import java.util.Set;

public interface JpaGenreRepository extends JpaRepository<JpaGenre, Long> {

    @Nonnull
    List<JpaGenre> findAll();

    List<JpaGenre> findByIdIn(Set<Long> ids);
}
