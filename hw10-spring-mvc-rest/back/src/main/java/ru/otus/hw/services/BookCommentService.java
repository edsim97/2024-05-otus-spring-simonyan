package ru.otus.hw.services;

import ru.otus.hw.models.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentService {
    List<BookComment> findAllForBook(long bookId);

    Optional<BookComment> findById(long id);
}
