package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.GenreService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер с методами для работы с жанрами должен")
@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GenreService genreService;

    @DisplayName("возвращать все жанры")
    @Test
    void shouldReturnAllGenres() throws Exception {

        List<Genre> genreList = List.of(
            new Genre(1, "Test1"),
            new Genre(2, "Test2"),
            new Genre(3, "Test3")
        );
        given(genreService.findAll()).willReturn(genreList);

        mvc.perform(get("/api/genre"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(genreList)));
    }
}
