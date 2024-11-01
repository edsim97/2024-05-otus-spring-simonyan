package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.converters.BookCommentConverter;
import ru.otus.hw.repositories.BookCommentRepository;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentConverter bookCommentConverter;

    private final BookCommentRepository bookCommentRepository;

    @Override
    public String getBookCommentsString(String bookId) {

        return bookCommentRepository.findByBook(bookId)
            .stream()
            .map(bookCommentConverter::bookCommentToString)
            .collect(Collectors.joining(",\n\t", "[\n\t", "\n]"));
    }
}
