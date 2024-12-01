package ru.otus.hw.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.models.Cocktail;
import ru.otus.hw.models.CocktailBase;

@MessagingGateway
public interface TransformationGateway {

    @Gateway(requestChannel  = "cocktailBaseChannel", replyChannel = "cocktailChannel")
    Cocktail process(CocktailBase cocktailBase);
}
