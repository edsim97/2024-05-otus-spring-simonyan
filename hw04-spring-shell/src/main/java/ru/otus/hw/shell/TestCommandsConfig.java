package ru.otus.hw.shell;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.Availability;
import org.springframework.shell.AvailabilityProvider;
import ru.otus.hw.security.LoginContext;
import ru.otus.hw.service.LocalizedIOService;

@Configuration
public class TestCommandsConfig {

    @Bean
    public AvailabilityProvider startCommandAvailabilityProvider(
        LoginContext loginContext,
        LocalizedIOService ioService
    ) {
        return () -> loginContext.isUserLoggedIn()
            ? Availability.available()
            : Availability.unavailable(ioService.getMessage("TestCommands.start.notLoggedIn"));
    }
}
