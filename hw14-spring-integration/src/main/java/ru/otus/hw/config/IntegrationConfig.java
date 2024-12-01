package ru.otus.hw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.hw.services.CocktailDbApiClient;

@Configuration
@EnableIntegration
public class IntegrationConfig {

    @Bean
    public MessageChannelSpec<?, ?> cocktailBaseChannel() {
        return MessageChannels.queue(10);
    }

    @Bean
    public MessageChannelSpec<?, ?> cocktailChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2);
    }

    @Bean
    public IntegrationFlow cocktailFlow(CocktailDbApiClient cocktailDbApiClient) {
        return IntegrationFlow.from(cocktailBaseChannel())
            .split()
            .transform(cocktailDbApiClient::findRandomForBase)
            .aggregate()
            .channel(cocktailChannel())
            .get();
    }
}

