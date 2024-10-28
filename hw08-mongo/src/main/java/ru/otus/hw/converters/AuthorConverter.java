package ru.otus.hw.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.models.Author;

import java.util.Optional;

@Component
public class AuthorConverter {
    public String authorToString(Author author) {

        return Optional.ofNullable(author)
            .map(a -> "{ Id: %s, FullName: %s }".formatted(a.getId(), a.getFullName()))
            .orElse("null");
    }
}
