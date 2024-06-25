package ru.otus.hw.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("classpath:application.properties")
public class AppProperties implements TestConfig, TestFileNameProvider {

    // Injecting the integer property from application.properties
    @Value("${test.rightAnswersCountToPass}")
    private int rightAnswersCountToPass;

    // Injecting the string property from application.properties
    @Value("${test.fileName}")
    @Getter
    private String testFileName;
}
