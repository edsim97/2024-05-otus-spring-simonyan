package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/book")
    public String getBooks(Model model) {

        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/book/create")
    public String createBookPage(Model model) {

        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());

        return "edit";
    }

    @GetMapping("/book/edit")
    public String editBookPage(@RequestParam("id") long id, Model model) {

        Book book = bookService.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());

        return "edit";
    }

    @PostMapping("/book")
    public String saveBook(
        @ModelAttribute Book book,
        @RequestParam("author") Long authorId,
        @RequestParam(value = "genres", required = false) List<Long> genreIds
    ) {
        Author author = authorService.findById(authorId);
        List<Genre> genres = genreService.findAllByIds(Optional.ofNullable(genreIds).orElse(List.of()));
        bookService.save(book.toBuilder().author(author).genres(genres).build());

        return "redirect:/book";
    }

    // Через DeleteMapping не получилось сделать. Часа 3 гуглил, пробовал разные подходы, но ничего не помогло :(
    @PostMapping("/book/{id}/delete")
    public String deleteBook(@PathVariable("id") long id, Model model) {

        bookService.deleteById(id);

        return "redirect:/book";
    }
}
