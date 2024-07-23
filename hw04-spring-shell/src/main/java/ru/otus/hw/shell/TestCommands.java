package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.CommandAvailability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.security.LoginContext;
import ru.otus.hw.service.LocalizedIOService;
import ru.otus.hw.service.TestRunnerService;

@Command(group = "Test app commands")
@RequiredArgsConstructor
public class TestCommands {

    private final LocalizedIOService ioService;

    private final LoginContext loginContext;

    private final TestRunnerService testRunnerService;

    @Command(description = "Set first name and last name of current student", command = "login", alias = "l")
    public String login(@ShellOption String firstName, @ShellOption String lastName) {

        this.loginContext.login(firstName, lastName);
        return this.ioService.getMessage("TestCommands.login.success", firstName, lastName);
    }

    @Command(description = "Starts student testing", command = "start", alias = {"s", "run"})
    @CommandAvailability(provider = "startCommandAvailabilityProvider")
    public void start() {

        this.testRunnerService.run();
    }
}
