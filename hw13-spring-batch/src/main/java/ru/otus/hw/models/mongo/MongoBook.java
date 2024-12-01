package ru.otus.hw.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "books")
public class MongoBook {

    @Id
    private String id;

    @Field("title")
    private String title;

    @DocumentReference(collection = "authors")
    private MongoAuthor author;

    @DocumentReference(collection = "genres")
    private List<MongoGenre> genres;
}
