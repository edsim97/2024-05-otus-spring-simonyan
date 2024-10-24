package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.hw.repositories.AuthorRepository;

@EnableMongoRepositories
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		AuthorRepository authorRepository = context.getBean(AuthorRepository.class);

		authorRepository.findAll().forEach(
			author -> System.out.println(
				"{ id: %s, full_name: %s }".formatted(author.getId(), author.getFullName())
			)
		);
	}

}
