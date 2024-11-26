package ru.otus.hw.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.hw.services.CocktailService;

@Component
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {

    private final CocktailService cocktailService;

    @Override
    public void run(String... args) {

        cocktailService.startGenerateCocktailBaseLoop();
    }
}
