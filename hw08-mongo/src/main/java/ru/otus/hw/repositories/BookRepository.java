package ru.otus.hw.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.models.Book;

public interface BookRepository extends CrudRepository<Book, String> { }
