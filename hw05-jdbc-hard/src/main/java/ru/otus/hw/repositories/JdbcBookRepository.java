package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SimplePropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcOperations jdbc;

    private final GenreRepository genreRepository;

    @Override
    public Optional<Book> findById(long id) {

        final Book book = this.jdbc.query(
            """
                select
                    b.id book__id,
                    b.title book__title,
                    a.id author__id,
                    a.full_name author__full_name,
                    g.id genre__id,
                    g.name genre__name
                from books as b
                left join authors a on b.author_id = a.id
                left join books_genres bg on b.id = bg.book_id
                left join genres g on bg.genre_id = g.id
                where b.id = :id
                """,
            Map.of("id", id),
            new BookResultSetExtractor()
        );

        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        var genres = genreRepository.findAll();
        var relations = getAllGenreRelations();
        var books = getAllBooksWithoutGenres();
        mergeBooksInfo(books, genres, relations);
        return books;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {

        final Map<String, Object> params = Map.of("id", id);

        this.jdbc.update("delete from books_genres where book_id = :id", params);
        this.jdbc.update("delete from books where id = :id", params);
    }

    private List<Book> getAllBooksWithoutGenres() {

        return this.jdbc.query(
            """
                select
                    books.id as book__id,
                    books.title as book__title,
                    authors.id as author__id,
                    authors.full_name as author__full_name
                from books
                left join authors on books.author_id = authors.id
                """,
            new BookRowMapper()
        );
    }

    private List<BookGenreRelation> getAllGenreRelations() {
        return this.jdbc.query(
            "select book_id, genre_id from books_genres",
            (rs, i) -> new BookGenreRelation(rs.getLong("book_id"), rs.getLong("genre_id"))
        );
    }

    private void mergeBooksInfo(
        List<Book> booksWithoutGenres,
        List<Genre> genres,
        List<BookGenreRelation> relations
    ) {
        for (Book book : booksWithoutGenres) {

            final List<Genre> bookGenres = relations.stream()
                .filter(relation -> relation.bookId() == book.getId())
                .map(BookGenreRelation::genreId)
                .distinct()
                .map(genreId -> genres.stream()
                    .filter(genre -> genre.getId() == genreId)
                    .findFirst()
                    .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Genre with id %s not found", genreId))
                    )
                )
                .collect(Collectors.toList());

            book.setGenres(bookGenres);
        }
        // Добавить книгам (booksWithoutGenres) жанры (genres) в соответствии со связями (relations)
    }

    private Book insert(Book book) {

        var keyHolder = new GeneratedKeyHolder();
        var params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());

        this.jdbc.update(
            "insert into books (title, author_id) values (:title, :author_id)",
            params,
            keyHolder,
            new String[]{"id"}
        );

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        batchInsertGenresRelationsFor(book);
        return book;
    }

    private Book update(Book book) {

        var params = Map.of(
            "id", book.getId(),
            "title", book.getTitle(),
            "author_id", book.getAuthor().getId()
        );

        int updatedEntities = this.jdbc.update(
            "update books set title = :title, author_id = :author_id where id = :id",
            params
        );

        if (updatedEntities == 0) {

            throw new EntityNotFoundException(String.format("Book with id %s not found", book.getId()));
        }

        // Выбросить EntityNotFoundException если не обновлено ни одной записи в БД
        removeGenresRelationsFor(book);
        batchInsertGenresRelationsFor(book);

        return book;
    }

    private void batchInsertGenresRelationsFor(Book book) {

        final SimplePropertySqlParameterSource[] bookGenreRelations = book.getGenres()
            .stream()
            .map(genre -> new BookGenreRelation(book.getId(), genre.getId()))
            .map(SimplePropertySqlParameterSource::new)
            .toArray(SimplePropertySqlParameterSource[]::new);

        this.jdbc.batchUpdate(
            "insert into books_genres (book_id, genre_id) values (:bookId, :genreId)",
            bookGenreRelations
        );
    }

    private void removeGenresRelationsFor(Book book) {

        this.jdbc.update("delete from books_genres where book_id = :id", Map.of("id", book.getId()));
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {

            new JdbcAuthorRepository.AuthorRowMapper().mapRow(rs, rowNum);

            return new Book(
                rs.getLong("book__id"),
                rs.getString("book__title"),
                new Author(rs.getLong("author__id"), rs.getString("author__full_name")),
                Collections.emptyList()
            );
        }
    }

    // Использовать для findById
    @SuppressWarnings("ClassCanBeRecord")
    @RequiredArgsConstructor
    private static class BookResultSetExtractor implements ResultSetExtractor<Book> {

        @Override
        public Book extractData(ResultSet rs) throws SQLException, DataAccessException {

            Book book = null;
            final List<Genre> genres = new ArrayList<>();

            while (rs.next()) {

                if (book == null) {

                    book = new Book(
                        rs.getLong("book__id"),
                        rs.getString("book__title"),
                        new Author(rs.getLong("author__id"), rs.getString("author__full_name")),
                        genres
                    );
                }

                genres.add(new Genre(rs.getLong("genre__id"), rs.getString("genre__name")));
            }

            return book;
        }
    }

    private record BookGenreRelation(long bookId, long genreId) {
    }
}
