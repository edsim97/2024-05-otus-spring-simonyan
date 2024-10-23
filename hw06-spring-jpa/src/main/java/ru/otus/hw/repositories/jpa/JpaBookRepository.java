package ru.otus.hw.repositories.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Book> findById(long id) {

        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public Optional<Book> findByIdFetchAll(long id) {

        final TypedQuery<Book> query = em.createQuery(
            "select b from Book b join fetch b.author join fetch b.genres where b.id = :id",
            Book.class
        );
        query.setParameter("id", id);

        try {

            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {

            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {

        return em.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Override
    public List<Book> findAllFetchAll() {

        return em.createQuery("select b from Book b join fetch b.author join fetch b.genres", Book.class)
            .getResultList();
    }

    @Override
    public Book save(Book book) {

        if (book.getId() == 0) {

            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public void deleteById(long id) {

        em.remove(em.find(Book.class, id));
    }
}
