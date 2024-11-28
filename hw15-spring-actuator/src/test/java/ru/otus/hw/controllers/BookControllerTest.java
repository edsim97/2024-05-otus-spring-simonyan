package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.configurations.SecurityConfiguration;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер с методами для работы с книгами должен")
@WithMockUser(username = "admin", password = "admin", authorities = {"DBReader", "DBWriter"})
@Import({SecurityConfiguration.class})
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private UserDetailsService userDetailsService;

    @DisplayName("возвращать все книги")
    @Test
    void shouldReturnAllBooks() throws Exception {

        List<Book> bookList = List.of(
            new Book(1, "Test1", new Author(1, "Test1"), List.of(new Genre(1, "Test1"), new Genre(2, "Test2"))),
            new Book(2, "Test2", new Author(2, "Test2"), List.of(new Genre(3, "Test3"), new Genre(4, "Test4"))),
            new Book(3, "Test3", new Author(3, "Test3"), List.of(new Genre(5, "Test5"), new Genre(6, "Test6")))
        );
        given(bookService.findAll()).willReturn(bookList);

        mvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(bookList)));
    }

    @DisplayName("возвращать одну книгу")
    @Test
    void shouldReturnBook() throws Exception {

        Book book = new Book(1, "Test1", new Author(1, "Test1"), List.of(new Genre(1, "Test1"), new Genre(2, "Test2")));
        given(bookService.findById(anyLong())).willReturn(Optional.of(book));

        mvc.perform(get("/api/books/1"))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(book)));
    }

    @DisplayName("сохранять книгу")
    @Test
    void shouldSaveBook() throws Exception {

        List<Book> savedBooks = new ArrayList<>();
        Book book = new Book(1, "TEST", null, null);

        given(bookService.save(any())).will((InvocationOnMock invocation) -> {
            savedBooks.add(invocation.getArgument(0));
            return invocation.getArgument(0);
        });

        mvc
            .perform(post("/api/books")
                .content(mapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());

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

        mvc.perform(delete("/api/books/1")).andExpect(status().isOk());

        assertThat(books).hasSize(0);
    }
}
