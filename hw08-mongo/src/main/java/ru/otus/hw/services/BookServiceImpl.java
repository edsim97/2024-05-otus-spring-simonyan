package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookConverter bookConverter;

    private final BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public String findAllString() {
        return bookRepository.findAll()
            .stream()
            .map(book -> "%s".formatted(bookConverter.bookToString(book)))
            .collect(Collectors.joining(",\n\t", "[\n\t", "\n]"));
    }
}
