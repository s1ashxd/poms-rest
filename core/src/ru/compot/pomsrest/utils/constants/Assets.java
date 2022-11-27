package ru.compot.pomsrest.utils.constants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ru.compot.pomsrest.GameCore;

public class Assets {

    public static final float PLAYER_FRAME_DURATION = .3f;
    public static final String UI_SKIN = "skins/ui.json";
    public static final String WORLD_BACKGROUND = "textures/world/background.png";
    public static final String RECIPE_BOOK_BACKGROUND = "textures/restaurant/recipe-book/background.png";
    public static final String MINIGAME_BACKGROUND = "textures/minigame/background.png";

    public static final String PLAYER_LLAMA = "textures/player-llama/player-llama.atlas";
    public static final String RIGHT_RUNNING_FOLDER = "right-running";
    public static final String RIGHT_IDLE_REGION = "right-running/1";
    public static final String LEFT_RUNNING_FOLDER = "left-running";
    public static final String LEFT_IDLE_REGION = "left-running/1";
    public static final String FORWARD_RUNNING = "forward";
    public static final String FORWARD_IDLE_REGION = "forward/1";

    public static final String RESTAURANT_ATLAS = "textures/restaurant/restaurant.atlas";
    public static final String RESTAURANT_SINK = "sink";
    public static final String RESTAURANT_CUPBOARD = "cupboard";
    public static final String RESTAURANT_STOVE = "stove";
    public static final String RESTAURANT_FRIDGE = "fridge";
    public static final String RESTAURANT_STAIR = "stair";
    public static final String RESTAURANT_CHAIR = "chair";
    public static final String RESTAURANT_POTS = "flowerpots";
    public static final String RESTAURANT_TABLE = "table";
    public static final String RESTAURANT_FLOWER_FOLDER = "chair-flowers/";
    public static final String RESTAURANT_LEFT_LAMP = "lamps/left";
    public static final String RESTAURANT_RIGHT_LAMP = "lamps/right";
    public static final String RESTAURANT_LIGHT_CELL = "cells/light";
    public static final String RESTAURANT_DARK_CELL = "cells/dark";
    public static final String RESTAURANT_KITCHEN_WALL_CELL = "kitchen-wall";
    public static final String RESTAURANT_WALL_CELL = "wall";
    public static final String RESTAURANT_LEFT_BAR = "bars/left";
    public static final String RESTAURANT_RIGHT_BAR = "bars/right";

    public static final String RECIPES_ATLAS = "textures/restaurant/recipe-book/recipes/recipes.atlas";
    public static final String RECIPE_BUYABES = "buyabes";

    public static final String MINIGAME_ATLAS = "textures/minigame/minigame.atlas";
    public static final String NORMAL_FOOD = "normal-food";
    public static final String SHIT_FOOD = "shit-food";
    public static final String INGREDIENT_ICE_CREAM = "ingredients/ice-cream";
    public static final String INGREDIENT_TOMATO = "ingredients/tomato";
    public static final String INGREDIENT_WINE = "ingredients/wine";
    public static final String INGREDIENT_OCTOPUS = "ingredients/octopus";
    public static final String INGREDIENT_WHIPPING_CREAM = "ingredients/whipping-cream";
    public static final String INGREDIENT_EGG = "ingredients/egg";
    public static final String INGREDIENT_LEMON = "ingredients/lemon";
    public static final String INGREDIENT_BISCUIT = "ingredients/biscuit";
    public static final String INGREDIENT_CHICKEN = "ingredients/chicken";
    public static final String INGREDIENT_CURD = "ingredients/curd";
    public static final String INGREDIENT_LOBSTER = "ingredients/lobster-mini";
    public static final String INGREDIENT_CHERRY = "ingredients/cherry";
    public static final String INGREDIENT_TOPPING = "ingredients/topping";
    public static final String INGREDIENT_FLOUR = "ingredients/flour";
    public static final String INGREDIENT_MUSSEL = "ingredients/mussel";
    public static final String INGREDIENT_CRACKERS = "ingredients/crackers";
    public static final String INGREDIENT_SPICES = "ingredients/spices";
    public static final String INGREDIENT_JUICE = "ingredients/juice";
    public static final String INGREDIENT_SHRIMP = "ingredients/shrimp";
    public static final String INGREDIENT_ONION = "ingredients/onion";
    public static final String INGREDIENT_PUFF_PASTRY = "ingredients/puff-pastry";
    public static final String INGREDIENT_SUGAR = "ingredients/sugar";

    public static final Class<Texture> TEXTURE = Texture.class;
    public static final Class<TextureAtlas> TEXTURE_ATLAS = TextureAtlas.class;
    public static final Class<Skin> SKIN = Skin.class;

    private Assets() {
    }

    public static <T> T getAsset(String name) {
        return GameCore.INSTANCE.getAsset(name);
    }
}
