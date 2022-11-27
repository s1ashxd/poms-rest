package ru.compot.pomsrest.scene2d.restaurant;

public class RecipesRegistry {
    public static final RecipeData BUYABES = new RecipeData("buyabes", "buyabes", new RecipeData.RecipeComponent[]{new RecipeData.RecipeComponent("ice_cream", "ice cream")});

    public static final RecipeData[] RECIPES = new RecipeData[] {
            BUYABES
    };
}
