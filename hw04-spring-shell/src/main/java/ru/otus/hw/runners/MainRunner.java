package ru.otus.hw.runners;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.hw.service.TestRunnerService;

@AllArgsConstructor
@Component
public class MainRunner implements CommandLineRunner {

    private final TestRunnerService testRunnerService;

    @Override
    public void run(String... args) throws Exception {

        this.testRunnerService.run();
    }
}
