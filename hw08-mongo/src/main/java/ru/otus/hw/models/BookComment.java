package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "books_comments")
public class BookComment {

    @Id
    private String id;

    @DocumentReference(collection = "books")
    private Book book;

    private String text;
}
