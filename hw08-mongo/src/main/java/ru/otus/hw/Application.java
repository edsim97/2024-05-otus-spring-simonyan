package ru.otus.hw;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookCommentService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.stream.Collectors;

@EnableMongock
@EnableMongoRepositories
@SpringBootApplication
public class Application {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		BookService bookService = context.getBean(BookService.class);

		displayAuthors(context.getBean(AuthorService.class));
		displayGenres(context.getBean(GenreService.class));
		displayBooks(bookService);
		displayBooksComments(bookService, context.getBean(BookCommentService.class));
	}

	private static void displayBooksComments(BookService bookService, BookCommentService bookCommentService) {
		System.out.printf(
			"""
			%n---------------------------------------------
			------ ALL BOOK COMMENTS --------------------
			---------------------------------------------
			
			%s
			
			---------------------------------------------
			""",
			bookService.findAll().stream()
				.map(book -> "BookId: %s. Comments: %s".formatted(
					book.getId(),
					bookCommentService.getBookCommentsString(book.getId())
				))
				.collect(Collectors.joining(System.lineSeparator()))
		);
	}

	private static void displayBooks(BookService bookService) {
		System.out.printf(
			"""
			%n---------------------------------------------
			------ ALL BOOKS ----------------------------
			---------------------------------------------
			
			%s
			
			---------------------------------------------
			""",
			bookService.findAllString()
		);
	}

	private static void displayGenres(GenreService genreService) {
		System.out.printf(
			"""
			%n---------------------------------------------
			------ ALL GENRES ---------------------------
			---------------------------------------------
			
			%s
			
			---------------------------------------------
			""",
			genreService.findAllString()
		);
	}

	private static void displayAuthors(AuthorService authorService) {
		System.out.printf(
			"""
			%n---------------------------------------------
			------ ALL AUTHORS --------------------------
			---------------------------------------------
			
			%s
			
			---------------------------------------------
			""",
			authorService.findAllString()
		);
	}
}
