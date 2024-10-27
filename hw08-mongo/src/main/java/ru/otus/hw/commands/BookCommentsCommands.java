package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.BookCommentConverter;
import ru.otus.hw.models.BookComment;
import ru.otus.hw.services.BookCommentService;

import java.util.stream.Collectors;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RequiredArgsConstructor
@ShellComponent
public class BookCommentsCommands {

    private final BookCommentService bookCommentService;

    private final BookCommentConverter bookCommentConverter;

    @ShellMethod(value = "Find all comments for book", key = "abc")
    public String findAllBooks(String id) {

        return bookCommentService.getBookComments(id).stream()
                .map(bookCommentConverter::bookCommentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Add new comment to the book", key = "bcins")
    public String addCommentToBook(String bookId, String commentText) {

        BookComment bookComment = bookCommentService.saveBookComment(bookId, commentText);
        return bookCommentConverter.bookCommentToString(bookComment);
    }
}
