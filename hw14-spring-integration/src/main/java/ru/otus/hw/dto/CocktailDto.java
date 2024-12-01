package ru.otus.hw.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CocktailDto {

    @JsonProperty("idDrink")
    private String id;

    @JsonProperty("strDrink")
    private String name;

    @JsonProperty("strDrinkAlternate")
    private String alternative;

    @JsonProperty("strTags")
    private String tags;

    @JsonProperty("strVideo")
    private String videoUrl;

    @JsonProperty("strCategory")
    private String category;

    @JsonProperty("strIBA")
    private String iba;

    @JsonProperty("strAlcoholic")
    private String alcoholic;

    @JsonProperty("strGlass")
    private String glass;

    @JsonProperty("strInstructions")
    private String instructions;

    @JsonProperty("strInstructionsES")
    private String instructionsES;

    @JsonProperty("strInstructionsDE")
    private String instructionsDE;

    @JsonProperty("strInstructionsFR")
    private String instructionsFR;

    @JsonProperty("strInstructionsIT")
    private String instructionsIT;

    @JsonProperty("strInstructionsZH-HANS")
    private String instructionsZHHANS;

    @JsonProperty("strInstructionsZH-HANT")
    private String instructionsZHHANT;

    @JsonProperty("strDrinkThumb")
    private String thumbnail;

    @JsonProperty("strIngredient1")
    private String ingredient1;

    @JsonProperty("strIngredient2")
    private String ingredient2;

    @JsonProperty("strIngredient3")
    private String ingredient3;

    @JsonProperty("strIngredient4")
    private String ingredient4;

    @JsonProperty("strIngredient5")
    private String ingredient5;

    @JsonProperty("strIngredient6")
    private String ingredient6;

    @JsonProperty("strIngredient7")
    private String ingredient7;

    @JsonProperty("strIngredient8")
    private String ingredient8;

    @JsonProperty("strIngredient9")
    private String ingredient9;

    @JsonProperty("strIngredient10")
    private String ingredient10;

    @JsonProperty("strIngredient11")
    private String ingredient11;

    @JsonProperty("strIngredient12")
    private String ingredient12;

    @JsonProperty("strIngredient13")
    private String ingredient13;

    @JsonProperty("strIngredient14")
    private String ingredient14;

    @JsonProperty("strIngredient15")
    private String ingredient15;

    @JsonProperty("strMeasure1")
    private String measure1;

    @JsonProperty("strMeasure2")
    private String measure2;

    @JsonProperty("strMeasure3")
    private String measure3;

    @JsonProperty("strMeasure4")
    private String measure4;

    @JsonProperty("strMeasure5")
    private String measure5;

    @JsonProperty("strMeasure6")
    private String measure6;

    @JsonProperty("strMeasure7")
    private String measure7;

    @JsonProperty("strMeasure8")
    private String measure8;

    @JsonProperty("strMeasure9")
    private String measure9;

    @JsonProperty("strMeasure10")
    private String measure10;

    @JsonProperty("strMeasure11")
    private String measure11;

    @JsonProperty("strMeasure12")
    private String measure12;

    @JsonProperty("strMeasure13")
    private String measure13;

    @JsonProperty("strMeasure14")
    private String measure14;

    @JsonProperty("strMeasure15")
    private String measure15;

    @JsonProperty("strImageSource")
    private String imageSource;

    @JsonProperty("strImageAttribution")
    private String imageAttribution;

    @JsonProperty("strCreativeCommonsConfirmed")
    private String creativeCommonsConfirmed;

    @JsonProperty("dateModified")
    private String dateModified;
}
