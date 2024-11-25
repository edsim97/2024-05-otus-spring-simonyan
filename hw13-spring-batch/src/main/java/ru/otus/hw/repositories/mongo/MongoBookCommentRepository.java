package ru.otus.hw.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.hw.models.mongo.MongoBookComment;

import java.util.List;

public interface MongoBookCommentRepository extends MongoRepository<MongoBookComment, String> {

    @Query("{ 'book_id': :#{#bookId} }")
    List<MongoBookComment> findByBook(@Param("bookId") String bookId);
}
