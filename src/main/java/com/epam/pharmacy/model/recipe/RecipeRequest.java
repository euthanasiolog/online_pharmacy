package com.epam.pharmacy.model.recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class RecipeRequest {
    private int drugId;
    private RecipeType recipeType;
}
