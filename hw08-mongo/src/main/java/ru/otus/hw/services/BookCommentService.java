package ru.otus.hw.services;

import ru.otus.hw.models.BookComment;

import java.util.List;

public interface BookCommentService {

    List<BookComment> getBookComments(String bookId);

    BookComment saveBookComment(String bookId, String commentText);
}
