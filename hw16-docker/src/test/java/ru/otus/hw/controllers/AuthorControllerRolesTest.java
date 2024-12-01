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
import ru.otus.hw.models.Author;
import ru.otus.hw.services.AuthorService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер с методами для работы с авторами должен")
@WebMvcTest(AuthorController.class)
@Import({SecurityConfiguration.class})
public class AuthorControllerRolesTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private UserDetailsService userDetailsService;

    private List<Author> authorList;

    @BeforeEach
    void beforeEach() {

        authorList = List.of(new Author(1, "Test1"));
        given(authorService.findAll()).willReturn(authorList);
    }

    @DisplayName("возвращать авторов при наличии роли DBReader")
    @WithMockUser(username = "admin", password = "admin", authorities = {"DBReader"})
    @Test
    void shouldReturnAllBooksToDBReader() throws Exception {

        mvc.perform(get("/api/authors"))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(authorList)));
    }

    @DisplayName("возвращать ошибку авторизации при отсутствии роли DBReader")
    @WithMockUser(username = "user", password = "user")
    @Test
    void shouldNotReturnAllBooksToNotDBReader() throws Exception {

        mvc.perform(get("/api/authors"))
            .andExpect(status().isForbidden());
    }
}
