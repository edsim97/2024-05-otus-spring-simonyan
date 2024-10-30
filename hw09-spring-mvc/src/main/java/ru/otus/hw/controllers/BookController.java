package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.BookService;

import java.util.List;

@Controller()
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("book")
    public String getBooks(Model model) {

        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("book/create")
    public String createBookPage(Model model) {

        return "create";
    }

    @PutMapping("book")
    public String createBook(Book book, Model model) {

        bookService.insert(book);
        
        return "redirect:/";
    }

    @GetMapping("book/update")
    public String updateBookPage(@RequestParam("id") long id, Model model) {

        Book book = bookService.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", book);

        return "update";
    }

    @PostMapping("book")
    public String updateBook(Book book, Model model) {

        bookService.update(book);

        return "redirect:/";
    }

    @DeleteMapping("book")
    public String deleteBook(@RequestParam("id") long id, Model model) {

        bookService.deleteById(id);

        return "redirect:/";
    }
}
