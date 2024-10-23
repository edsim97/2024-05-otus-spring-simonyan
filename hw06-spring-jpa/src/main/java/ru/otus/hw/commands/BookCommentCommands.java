package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.BookCommentConverter;
import ru.otus.hw.services.BookCommentService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class BookCommentCommands {

    private final BookCommentService bookCommentService;

    private final BookCommentConverter bookCommentConverter;

    @ShellMethod(value = "Find book comment by id", key = "bcbid")
    public String findBookCommentById(long id) {
        return bookCommentService.findById(id)
            .map(bookCommentConverter::bookCommentToString)
            .orElse("Book with id %d not found".formatted(id));
    }

    @ShellMethod(value = "Find all comments for book", key = "acfb")
    public String findAllCommentsForBook(long bookId) {
        return bookCommentService.findAllForBook(bookId).stream()
                .map(bookCommentConverter::bookCommentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }
}
