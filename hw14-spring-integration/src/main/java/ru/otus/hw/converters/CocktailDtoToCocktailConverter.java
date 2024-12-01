package ru.otus.hw.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.CocktailDto;
import ru.otus.hw.models.Cocktail;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CocktailDtoToCocktailConverter {

    public Cocktail convert(CocktailDto cocktail) {

        return new Cocktail(
            cocktail.getId(),
            cocktail.getName(),
            getIngredients(cocktail).stream()
                .filter(ingredient -> ingredient.getName() != null)
                .collect(Collectors.toList()),
            cocktail.getInstructions()
        );
    }

    private List<Cocktail.Ingredient> getIngredients(CocktailDto cocktail) {

        return List.of(
            new Cocktail.Ingredient(cocktail.getIngredient1(), cocktail.getMeasure1()),
            new Cocktail.Ingredient(cocktail.getIngredient2(), cocktail.getMeasure2()),
            new Cocktail.Ingredient(cocktail.getIngredient3(), cocktail.getMeasure3()),
            new Cocktail.Ingredient(cocktail.getIngredient4(), cocktail.getMeasure4()),
            new Cocktail.Ingredient(cocktail.getIngredient5(), cocktail.getMeasure5()),
            new Cocktail.Ingredient(cocktail.getIngredient6(), cocktail.getMeasure6()),
            new Cocktail.Ingredient(cocktail.getIngredient7(), cocktail.getMeasure7()),
            new Cocktail.Ingredient(cocktail.getIngredient8(), cocktail.getMeasure8()),
            new Cocktail.Ingredient(cocktail.getIngredient9(), cocktail.getMeasure9()),
            new Cocktail.Ingredient(cocktail.getIngredient10(), cocktail.getMeasure10()),
            new Cocktail.Ingredient(cocktail.getIngredient11(), cocktail.getMeasure11()),
            new Cocktail.Ingredient(cocktail.getIngredient12(), cocktail.getMeasure12()),
            new Cocktail.Ingredient(cocktail.getIngredient13(), cocktail.getMeasure13()),
            new Cocktail.Ingredient(cocktail.getIngredient14(), cocktail.getMeasure14()),
            new Cocktail.Ingredient(cocktail.getIngredient15(), cocktail.getMeasure15())
        );
    }
}
