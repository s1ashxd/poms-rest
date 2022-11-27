package ru.compot.pomsrest.scene2d.restaurant;

public class RecipeData {

    private final String title;
    private final String atlasRegion;
    private final RecipeComponent[] recipeComponents;

    public RecipeData(String title, String atlasRegion, RecipeComponent[] recipeComponents) {
        this.title = title;
        this.atlasRegion = atlasRegion;
        this.recipeComponents = recipeComponents;
    }

    public String getTitle() {
        return title;
    }

    public String getAtlasRegion() {
        return atlasRegion;
    }

    public RecipeComponent[] getRecipeComponents() {
        return recipeComponents;
    }

    public static class RecipeComponent {
        public String id, displayName;

        public RecipeComponent(String id, String displayName) {
            this.id = id;
            this.displayName = displayName;
        }
    }
}
