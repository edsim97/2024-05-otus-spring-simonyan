package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Book;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BookConverter {
    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    private final BookCommentConverter bookCommentConverter;

    public String bookToString(Book book) {

        if (book == null) {

            return "null";
        }

        var genresString = book.getGenres().stream()
            .map(genreConverter::genreToString)
            .collect(Collectors.joining(", ", "[", "]"));

        return "{ Id: %s, title: %s, author: %s, genres: %s }".formatted(
            book.getId(),
            book.getTitle(),
            authorConverter.authorToString(book.getAuthor()),
            genresString
        );
    }
}
