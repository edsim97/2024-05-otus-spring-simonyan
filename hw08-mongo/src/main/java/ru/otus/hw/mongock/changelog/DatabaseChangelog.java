package ru.otus.hw.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.BookComment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookCommentRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "edsim", runAlways = true)
    public void dropDb(MongoDatabase db) {

        db.drop();
    }

    @ChangeSet(order = "002", id = "initAuthorCollection", author = "edsim")
    public void insertAuthors(MongoDatabase db) {

        MongoCollection<Document> authors = db.getCollection("authors");

        var docs = List.of(
            new Document().append("full_name", "Author_1"),
            new Document().append("full_name", "Author_2"),
            new Document().append("full_name", "Author_3"),
            new Document().append("full_name", "Author_4")
        );

        authors.insertMany(docs);
    }

    @ChangeSet(order = "003", id = "initGenresCollection", author = "edsim")
    public void insertGenres(MongoDatabase db) {

        MongoCollection<Document> genres = db.getCollection("genres");

        var docs = List.of(
            new Document().append("name", "Genre_1"),
            new Document().append("name", "Genre_2"),
            new Document().append("name", "Genre_3"),
            new Document().append("name", "Genre_4"),
            new Document().append("name", "Genre_5"),
            new Document().append("name", "Genre_6")
        );

        genres.insertMany(docs);
    }

    @ChangeSet(order = "004", id = "initBooksCollection", author = "edsim")
    public void insertBooks(
        BookRepository bookRepository,
        AuthorRepository authorRepository,
        GenreRepository genreRepository
    ) {
        List<Author> authors = authorRepository.findAll();
        List<Genre> genres = genreRepository.findAll();

        var docs = List.of(
           createBook(1, authors.get(0), List.of(genres.get(0), genres.get(1))),
           createBook(2, authors.get(1), List.of(genres.get(2), genres.get(3))),
           createBook(3, authors.get(2), List.of(genres.get(4), genres.get(5)))
        );

        bookRepository.insert(docs);
    }

    @ChangeSet(order = "005", id = "initBooksCommentsCollection", author = "edsim")
    public void insertBooksComments(BookCommentRepository bookCommentRepository, BookRepository bookRepository) {

        List<BookComment> comments = bookRepository.findAll().stream()
            .map(
                book -> List.of(
                    BookComment.builder().book(book).text("Comment_1").build(),
                    BookComment.builder().book(book).text("Comment_2").build()
                )
            )
            .flatMap(List::stream)
            .collect(Collectors.toList());

        bookCommentRepository.insert(comments);
    }

    private Book createBook(long index, Author author, List<Genre> genres) {

        return Book.builder().title("Book_" + index).author(author).genres(genres).build();
    }
}
