package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyList;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер с методами для работы с книгами должен")
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private BookService bookService;

    @MockBean
    private GenreService genreService;

    @DisplayName("возвращать все книги")
    @Test
    void shouldReturnAllBooks() throws Exception {

        List<Book> bookList = List.of(
            new Book(1, "Test1", new Author(1, "Test1"), List.of(new Genre(1, "Test1"), new Genre(2, "Test2"))),
            new Book(2, "Test2", new Author(2, "Test2"), List.of(new Genre(3, "Test3"), new Genre(4, "Test4"))),
            new Book(3, "Test3", new Author(3, "Test3"), List.of(new Genre(5, "Test5"), new Genre(6, "Test6")))
        );
        given(bookService.findAll()).willReturn(bookList);

        mvc.perform(get("/book"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("books", bookList));
    }

    @DisplayName("возвращать модели для создания книги")
    @Test
    void shouldReturnCreateBookPageModels() throws Exception {

        List<Author> authorList = List.of(new Author(1, "Test1"), new Author(2, "Test2"), new Author(3, "Test3"));
        List<Genre> genreList = List.of(new Genre(1, "Test1"), new Genre(2, "Test2"), new Genre(3, "Test3"));
        given(authorService.findAll()).willReturn(authorList);
        given(genreService.findAll()).willReturn(genreList);

        mvc.perform(get("/book/create"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("book", new Book()))
            .andExpect(model().attribute("authors", authorList))
            .andExpect(model().attribute("genres", genreList));
    }

    @DisplayName("возвращать модели для редактирования книги")
    @Test
    void shouldReturnEditBookPageModels() throws Exception {

        List<Author> authorList = List.of(new Author(1, "Test1"), new Author(2, "Test2"), new Author(3, "Test3"));
        List<Genre> genreList = List.of(new Genre(1, "Test1"), new Genre(2, "Test2"), new Genre(3, "Test3"));
        Book book = new Book(1, "TEST", new Author(1, "Test1"), genreList);

        given(authorService.findAll()).willReturn(authorList);
        given(genreService.findAll()).willReturn(genreList);
        given(bookService.findById(anyLong())).willReturn(Optional.of(book));

        mvc.perform(get("/book/edit").queryParam("id", "1"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("book", book))
            .andExpect(model().attribute("authors", authorList))
            .andExpect(model().attribute("genres", genreList));
    }

    @DisplayName("сохранять книгу")
    @Test
    void shouldSaveBook() throws Exception {

        List<Book> savedBooks = new ArrayList<>();
        Book book = new Book(1, "TEST", null, null);

        given(authorService.findById(anyLong())).willReturn(null);
        given(genreService.findAllByIds(anyList())).willReturn(null);
        given(bookService.save(any())).will((InvocationOnMock invocation) -> {
            savedBooks.add(invocation.getArgument(0));
            return invocation.getArgument(0);
        });

        mvc
            .perform(post("/book")
                .queryParam("id", "1")
                .queryParam("title", "TEST")
                .queryParam("authorId", "0")
                .queryParam("genresIds", "0")
            )
            .andExpect(status().isFound());

        assertThat(savedBooks)
            .hasSize(1)
            .element(0)
            .isEqualTo(book);
    }

    @DisplayName("удалять книгу")
    @Test
    void shouldDeleteBook() throws Exception {

        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "TEST", null, null));

        doAnswer(invocation -> books.removeIf(b -> b.getId() == ((Long) invocation.getArgument(0))))
            .when(bookService).deleteById(anyLong());

        mvc.perform(post("/book/1/delete")).andExpect(status().isFound());

        assertThat(books).hasSize(0);
    }
}
