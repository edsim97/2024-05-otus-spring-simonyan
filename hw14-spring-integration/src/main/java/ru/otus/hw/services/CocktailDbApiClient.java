package ru.otus.hw.services;

import ru.otus.hw.models.Cocktail;
import ru.otus.hw.models.CocktailBase;

public interface CocktailDbApiClient {

    Cocktail findRandomForBase(CocktailBase base);
}
