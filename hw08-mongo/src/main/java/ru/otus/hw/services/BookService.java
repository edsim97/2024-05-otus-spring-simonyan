package ru.otus.hw.services;

import ru.otus.hw.models.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    String findAllString();
}
