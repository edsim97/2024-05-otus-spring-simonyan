package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/books")
    public List<Book> getBooks() {

        return bookService.findAll();
    }

    @PostMapping("/api/books")
    public void saveBook(@RequestBody Book book) {

        bookService.save(book);
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable("id") long id) {

        bookService.deleteById(id);
    }
}
