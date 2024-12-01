package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.hw.converters.CocktailDtoToCocktailConverter;
import ru.otus.hw.dto.CocktailDto;
import ru.otus.hw.dto.CocktailListDto;
import ru.otus.hw.models.Cocktail;
import ru.otus.hw.models.CocktailBase;

import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class CocktailDbApiClientImpl implements CocktailDbApiClient {

    private final CocktailDtoToCocktailConverter cocktailConverter;

    private final RestTemplate restTemplate;

    @Override
    public Cocktail findRandomForBase(CocktailBase base) {

        CocktailListDto cocktailList = findCocktailList(base);

        if (cocktailList == null) {
            return null;
        }
        int cocktailIndex = new Random().nextInt(cocktailList.getDrinks().size());

        CocktailDto cocktail = findCocktail(cocktailList.getDrinks().get(cocktailIndex).getName());

        if (cocktail == null) {
            return null;
        }

        return cocktailConverter.convert(cocktail);
    }

    private CocktailListDto findCocktailList(CocktailBase base) {

        return restTemplate.getForObject(
            "https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=" + base.name(),
            CocktailListDto.class
        );
    }

    private CocktailDto findCocktail(String name) {

        CocktailListDto foundList = restTemplate.getForObject(
            "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + name,
            CocktailListDto.class
        );
        return Optional.ofNullable(foundList)
            .map(CocktailListDto::getDrinks)
            .map(list -> list.get(0))
            .orElse(null);
    }
}
