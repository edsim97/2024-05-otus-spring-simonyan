package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.BookService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/books")
    public List<Book> getBooks() {

        return bookService.findAll();
    }

    @GetMapping("/api/books/{id}")
    public Book getBooks(@PathVariable("id") long id) {

        return bookService.findById(id).orElse(null);
    }

    @PostMapping("/api/books")
    @PreAuthorize("hasAuthority('DBWriter')")
    public void saveBook(@RequestBody Book book) {

        bookService.save(book);
    }

    @DeleteMapping("/api/books/{id}")
    @PreAuthorize("hasAuthority('DBWriter')")
    public void deleteBook(@PathVariable("id") long id) {

        bookService.deleteById(id);
    }
}
