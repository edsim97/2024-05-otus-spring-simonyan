package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.BookComment;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class BookCommentConverter {

    public String bookCommentToString(BookComment comment) {

        return Optional.ofNullable(comment)
            .map(c -> "{ Book: %s, Text: %s }".formatted(c.getBook().getId(), c.getText()))
            .orElse("null");
    }
}
