package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@ToString
@Getter
public class Cocktail {

    private final String id;

    private final String name;

    private final List<Ingredient> ingredients;

    private final String instructions;

    @AllArgsConstructor
    @ToString
    @Getter
    public static class Ingredient {

        private final String name;

        private final String measure;
    }
}
