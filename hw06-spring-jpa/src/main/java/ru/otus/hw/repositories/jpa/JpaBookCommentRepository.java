package ru.otus.hw.repositories.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.BookComment;
import ru.otus.hw.repositories.BookCommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaBookCommentRepository implements BookCommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<BookComment> findAllForBook(long bookId) {

        return em.createQuery("select bc from BookComment bc where bc.book.id = :bookId", BookComment.class)
            .setParameter("bookId", bookId)
            .getResultList();
    }

    @Override
    public Optional<BookComment> findById(long id) {

        return Optional.ofNullable(em.find(BookComment.class, id));
    }
}
