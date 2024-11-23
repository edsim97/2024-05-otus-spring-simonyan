package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.configurations.SecurityConfiguration;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер с методами для работы с книгами должен")
@WebMvcTest(BookController.class)
@Import({SecurityConfiguration.class})
public class BookControllerRolesTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private UserDetailsService userDetailsService;

    private List<Book> bookList;

    @BeforeEach
    void beforeEach() {

        bookList = List.of(new Book(1, "Test1", new Author(1, ""), List.of()));
        given(bookService.findAll()).willReturn(bookList);
        given(bookService.findById(anyLong())).willReturn(Optional.of(bookList.get(0)));
        given(bookService.save(any())).willReturn(bookList.get(0));
        doNothing().when(bookService).deleteById(anyLong());
    }

    @DisplayName("возвращать книги при наличии роли DBReader")
    @WithMockUser(username = "admin", password = "admin", authorities = {"DBReader"})
    @Test
    void shouldReturnAllBooksToDBReader() throws Exception {

        mvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(bookList)));
    }

    @DisplayName("возвращать книгу при наличии роли DBReader")
    @WithMockUser(username = "admin", password = "admin", authorities = {"DBReader"})
    @Test
    void shouldReturnBookToDBReader() throws Exception {

        mvc.perform(get("/api/books/1"))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(bookList.get(0))));
    }

    @DisplayName("сохранять книгу при наличии роли DBWriter")
    @WithMockUser(username = "admin", password = "admin", authorities = {"DBWriter"})
    @Test
    void shouldSaveBookForDBWriter() throws Exception {

        mvc
            .perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookList.get(0)))
            )
            .andExpect(status().isOk());
    }

    @DisplayName("удалять книгу при наличии роли DBWriter")
    @WithMockUser(username = "admin", password = "admin", authorities = {"DBWriter"})
    @Test
    void shouldDeleteBookForDBWriter() throws Exception {

        mvc.perform(delete("/api/books/1")).andExpect(status().isOk());
    }

    @DisplayName("возвращать ошибку авторизации при отсутствии ролей DBReader и DBWriter")
    @WithMockUser(username = "user", password = "user")
    @Test
    void shouldNotReturnAllBooksToNotDBReader() throws Exception {

        mvc.perform(get("/api/books")).andExpect(status().isForbidden());
        mvc.perform(get("/api/books/1")).andExpect(status().isForbidden());
        mvc
            .perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookList.get(0)))
            )
            .andExpect(status().isForbidden());
        mvc.perform(delete("/api/books/1")).andExpect(status().isForbidden());
    }
}
