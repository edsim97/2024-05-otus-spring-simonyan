package ru.otus.hw.configurations;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.models.jpa.JpaAuthor;
import ru.otus.hw.models.jpa.JpaBook;
import ru.otus.hw.models.jpa.JpaBookComment;
import ru.otus.hw.models.jpa.JpaGenre;
import ru.otus.hw.models.mongo.MongoAuthor;
import ru.otus.hw.models.mongo.MongoBook;
import ru.otus.hw.models.mongo.MongoBookComment;
import ru.otus.hw.models.mongo.MongoGenre;

import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Configuration
@SuppressWarnings("unused")
public class JobConfig {

    private static final int CHUNK_SIZE = 5;

    private final MongoTemplate mongoTemplate;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    //region readers

    @StepScope
    @Bean
    public JpaCursorItemReader<JpaAuthor> authorReader(EntityManagerFactory entityManagerFactory) {

        final JpaCursorItemReader<JpaAuthor> reader = new JpaCursorItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("SELECT a from JpaAuthor a");
        reader.setName("authorReader");

        return reader;
    }

    @StepScope
    @Bean
    public JpaCursorItemReader<JpaGenre> genreReader(EntityManagerFactory entityManagerFactory) {

        final JpaCursorItemReader<JpaGenre> reader = new JpaCursorItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("SELECT g from JpaGenre g");
        reader.setName("genreReader");

        return reader;
    }

    @StepScope
    @Bean
    public JpaCursorItemReader<JpaBook> bookReader(EntityManagerFactory entityManagerFactory) {

        final JpaCursorItemReader<JpaBook> reader = new JpaCursorItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("SELECT b from JpaBook b");
        reader.setName("bookReader");

        return reader;
    }

    @StepScope
    @Bean
    public JpaCursorItemReader<JpaBookComment> bookCommentReader(EntityManagerFactory entityManagerFactory) {

        final JpaCursorItemReader<JpaBookComment> reader = new JpaCursorItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("SELECT bc from JpaBookComment bc");
        reader.setName("bookCommentReader");

        return reader;
    }

    //endregion
    //region processors

    @StepScope
    @Bean
    public ItemProcessor<JpaAuthor, MongoAuthor> authorProcessor() {

        return jpaAuthor -> new MongoAuthor(String.valueOf(jpaAuthor.getId()), jpaAuthor.getFullName());
    }

    @StepScope
    @Bean
    public ItemProcessor<JpaGenre, MongoGenre> genreProcessor() {

        return jpaGenre -> new MongoGenre(String.valueOf(jpaGenre.getId()), jpaGenre.getName());
    }

    @StepScope
    @Bean
    public ItemProcessor<JpaBook, MongoBook> bookProcessor() {

        return jpaBook -> new MongoBook(
            String.valueOf(jpaBook.getId()),
            jpaBook.getTitle(),
            MongoAuthor.builder().id(String.valueOf(jpaBook.getAuthor().getId())).build(),
            jpaBook.getGenres().stream()
                .map(jpaGenre -> MongoGenre.builder().id(String.valueOf(jpaGenre.getId())).build())
                .collect(Collectors.toList())
        );
    }

    @StepScope
    @Bean
    public ItemProcessor<JpaBookComment, MongoBookComment> bookCommentProcessor() {

        return jpaComment -> new MongoBookComment(
            String.valueOf(jpaComment.getId()),
            MongoBook.builder().id(String.valueOf(jpaComment.getBook().getId())).build(),
            jpaComment.getText()
        );
    }

    //endregion
    //region writers

    @StepScope
    @Bean
    public MongoItemWriter<MongoAuthor> authorWriter() {

        return new MongoItemWriterBuilder<MongoAuthor>()
            .template(mongoTemplate)
            .collection("authors")
            .build();
    }

    @StepScope
    @Bean
    public MongoItemWriter<MongoGenre> genreWriter() {

        return new MongoItemWriterBuilder<MongoGenre>()
            .template(mongoTemplate)
            .collection("genres")
            .build();
    }

    @StepScope
    @Bean
    public MongoItemWriter<MongoBook> bookWriter() {

        return new MongoItemWriterBuilder<MongoBook>()
            .template(mongoTemplate)
            .collection("books")
            .build();
    }

    @StepScope
    @Bean
    public MongoItemWriter<MongoBookComment> bookCommentWriter() {

        return new MongoItemWriterBuilder<MongoBookComment>()
            .template(mongoTemplate)
            .collection("bookComments")
            .build();
    }

    //endregion
    //region transformers

    @Bean
    public Step transformAuthorStep(
        JpaCursorItemReader<JpaAuthor> reader,
        ItemProcessor<JpaAuthor, MongoAuthor> processor,
        MongoItemWriter<MongoAuthor> writer
    ) {
        return new StepBuilder("transformAuthorStep", jobRepository)
            .<JpaAuthor, MongoAuthor>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

    @Bean
    public Step transformGenreStep(
        JpaCursorItemReader<JpaGenre> reader,
        ItemProcessor<JpaGenre, MongoGenre> processor,
        MongoItemWriter<MongoGenre> writer
    ) {
        return new StepBuilder("transformGenreStep", jobRepository)
            .<JpaGenre, MongoGenre>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

    @Bean
    public Step transformBookStep(
        JpaCursorItemReader<JpaBook> reader,
        ItemProcessor<JpaBook, MongoBook> processor,
        MongoItemWriter<MongoBook> writer
    ) {
        return new StepBuilder("transformBookStep", jobRepository)
            .<JpaBook, MongoBook>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

    @Bean
    public Step transformBookCommentStep(
        JpaCursorItemReader<JpaBookComment> reader,
        ItemProcessor<JpaBookComment, MongoBookComment> processor,
        MongoItemWriter<MongoBookComment> writer
    ) {
        return new StepBuilder("transformBookCommentStep", jobRepository)
            .<JpaBookComment, MongoBookComment>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

    //endregion

    @Bean
    public Job migrateJpaToMongoJob(
        Step transformAuthorStep,
        Step transformGenreStep,
        Step transformBookStep,
        Step transformBookCommentStep
    ) {
        return new JobBuilder("migrateJpaToMongoJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .flow(transformAuthorStep)
            .next(transformGenreStep)
            .next(transformBookStep)
            .next(transformBookCommentStep)
            .end()
            .build();
    }
}
