package ru.otus.hw.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.jpa.JpaBookComment;

import java.util.List;
import java.util.Optional;

public interface JpaBookCommentRepository extends JpaRepository<JpaBookComment, Long> {

    List<JpaBookComment> findByBookId(long bookId);

    Optional<JpaBookComment> findById(long id);
}
