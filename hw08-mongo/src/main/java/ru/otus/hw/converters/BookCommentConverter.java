package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.BookComment;

@RequiredArgsConstructor
@Component
public class BookCommentConverter {

    public String bookCommentToString(BookComment comment) {

        return "{ Text: %s }".formatted(comment.getText());
    }
}
