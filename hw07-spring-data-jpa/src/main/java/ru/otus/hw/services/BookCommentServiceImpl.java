package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.BookComment;
import ru.otus.hw.repositories.BookCommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentRepository bookCommentRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<BookComment> findById(long id) {

        return this.bookCommentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookComment> findAllForBook(long bookId) {

        return this.bookCommentRepository.findAllForBook(bookId);
    }
}
