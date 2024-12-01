package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.configurations.SecurityConfiguration;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.GenreService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер с методами доступа к web страницам для работы с книгами должен")
@WebMvcTest(BookPagesController.class)
@Import({SecurityConfiguration.class})
public class BookPagesControllerRolesTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserDetailsService userDetailsService;

    @DisplayName("возвращать страницу со списком книг при наличии роли DBReader")
    @WithMockUser(username = "admin", password = "admin", authorities = {"DBReader"})
    @Test
    void shouldReturnBookListPageToDBReader() throws Exception {

        mvc.perform(get("/books")).andExpect(status().isOk());
    }

    @DisplayName("возвращать страницу со списком книг при наличии роли DBReader")
    @WithMockUser(username = "admin", password = "admin", authorities = {"DBWriter"})
    @Test
    void shouldReturnBookEditPageToDBWriter() throws Exception {

        mvc.perform(get("/books/edit?id=1")).andExpect(status().isOk());
    }

    @DisplayName("возвращать ошибку авторизации при отсутствии роли DBReader или DBWriter")
    @WithMockUser(username = "user", password = "user")
    @Test
    void shouldNotReturnPages() throws Exception {

        mvc.perform(get("/books")).andExpect(status().isForbidden());
        mvc.perform(get("/books/edit?id=1")).andExpect(status().isForbidden());
    }
}
