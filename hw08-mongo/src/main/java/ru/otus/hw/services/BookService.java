package ru.otus.hw.services;

import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Set;

public interface BookService {

    String findById(String id);

    List<Book> findAll();

    String findAllString();

    Book insert(String title, String authorId, Set<String> genresIds);

    Book update(String id, String title, String authorId, Set<String> genresIds);

    void deleteById(String id);
}
