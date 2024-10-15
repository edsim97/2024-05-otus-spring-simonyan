package ru.otus.hw.repositories.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Primary
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Book> findById(long id) {

        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {

        return em.createQuery("select b from Book b", Book.class).getResultList();
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
