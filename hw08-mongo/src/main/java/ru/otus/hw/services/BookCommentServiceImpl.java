package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.BookComment;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookCommentServiceImpl implements BookCommentService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public List<BookComment> getBookComments(String bookId) {

        return bookRepository.findById(bookId)
            .map(Book::getComments)
            .orElseThrow(() -> new EntityNotFoundException("Can't find book with id %s".formatted(bookId)));
    }

    @Transactional
    @Override
    public BookComment saveBookComment(String bookId, String commentText) {

        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new EntityNotFoundException("Can't find book with id %s".formatted(bookId)));
        BookComment comment = BookComment.builder().text(commentText).build();
        book.getComments().add(comment);
        bookRepository.save(book);

        return comment;
    }
}
