package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.BookComment;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> { }
