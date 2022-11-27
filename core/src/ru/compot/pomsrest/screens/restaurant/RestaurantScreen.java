package ru.compot.pomsrest.screens.restaurant;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import ru.compot.pomsrest.ai.GraphImpl;
import ru.compot.pomsrest.ashley.systems.InteractSystem;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.ashley.utils.constants.enums.InteractType;
import ru.compot.pomsrest.ashley.utils.factory.EntityFactory;
import ru.compot.pomsrest.scene2d.restaurant.RecipeData;
import ru.compot.pomsrest.scene2d.restaurant.background.GridActor;
import ru.compot.pomsrest.scene2d.restaurant.background.KitchenActor;
import ru.compot.pomsrest.scene2d.restaurant.background.StairsActor;
import ru.compot.pomsrest.scene2d.restaurant.minigame.MinigameActor;
import ru.compot.pomsrest.scene2d.restaurant.recipebook.RecipeBookActor;
import ru.compot.pomsrest.screens.GameScreen;
import ru.compot.pomsrest.utils.Animated2DCamera;
import ru.compot.pomsrest.utils.constants.Assets;

public class RestaurantScreen extends GameScreen {
    private static final float STAIRS_Y = 190f;

    private final GraphImpl graph;
    private final RecipeBookActor recipeBookActor;
    private final float restaurantWidth;
    private MinigameActor minigameActor;
    private Array<Actor> cachedUi;

    public RestaurantScreen() {
        super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new Animated2DCamera()));
        // assets
        TextureAtlas atlas = Assets.getAsset(Assets.RESTAURANT_ATLAS);
        TextureRegion stairRegion = atlas.findRegion(Assets.RESTAURANT_STAIR);
        TextureRegion wallRegion = atlas.findRegion(Assets.RESTAURANT_WALL_CELL);
        TextureRegion kitchenWallRegion = atlas.findRegion(Assets.RESTAURANT_KITCHEN_WALL_CELL);
        TextureRegion leftBarRegion = atlas.findRegion(Assets.RESTAURANT_LEFT_BAR);
        TextureRegion rightBarRegion = atlas.findRegion(Assets.RESTAURANT_RIGHT_BAR);
        TextureRegion tableRegion = atlas.findRegion(Assets.RESTAURANT_TABLE);
        TextureRegion chairRegion = atlas.findRegion(Assets.RESTAURANT_CHAIR);
        TextureRegion stoveRegion = atlas.findRegion(Assets.RESTAURANT_STOVE);
        TextureRegion recipeBookRegion = new TextureRegion(Assets.<Texture>getAsset(Assets.RECIPE_BOOK_BACKGROUND));
        // coordinates
        float kitchenWallY = Gdx.graphics.getHeight() - kitchenWallRegion.getRegionWidth();
        float floorHeight = kitchenWallY / 2f;
        float maxStairsX = wallRegion.getRegionWidth() + stairRegion.getRegionWidth();
        // actors
        KitchenActor kitchen = new KitchenActor(
                atlas.findRegion(Assets.RESTAURANT_SINK),
                atlas.findRegion(Assets.RESTAURANT_CUPBOARD),
                stoveRegion,
                atlas.findRegion(Assets.RESTAURANT_FRIDGE),
                maxStairsX,
                Gdx.graphics.getWidth() - maxStairsX
        );
        kitchen.setY(Gdx.graphics.getHeight() - kitchen.getHeight());
        this.restaurantWidth = wallRegion.getRegionWidth() + stairRegion.getRegionWidth() + kitchen.getWidth();
        StairsActor stairs = new StairsActor(
                atlas.findRegion(Assets.RESTAURANT_STAIR),
                wallRegion.getRegionWidth(),
                STAIRS_Y,
                Gdx.graphics.getHeight() - STAIRS_Y
        );
        GridActor kitchenWall = new GridActor(
                atlas.findRegion(Assets.RESTAURANT_KITCHEN_WALL_CELL),
                maxStairsX,
                kitchenWallY,
                kitchen.getWidth(),
                kitchenWallRegion.getRegionHeight()
        );
        GridActor kitchenFloor = new GridActor(
                atlas.findRegion(Assets.RESTAURANT_LIGHT_CELL),
                0f,
                floorHeight,
                restaurantWidth,
                floorHeight
        );
        GridActor hallFloor = new GridActor(
                atlas.findRegion(Assets.RESTAURANT_DARK_CELL),
                0f,
                0f,
                restaurantWidth,
                floorHeight
        );
        GridActor bottomWall = new GridActor(
                atlas.findRegion(Assets.RESTAURANT_WALL_CELL),
                0f,
                0f,
                wallRegion.getRegionWidth(),
                20f
        );
        GridActor topWall = new GridActor(
                atlas.findRegion(Assets.RESTAURANT_WALL_CELL),
                0f,
                STAIRS_Y - 40f,
                wallRegion.getRegionWidth(),
                Gdx.graphics.getHeight() - STAIRS_Y + 40f
        );
        Image leftBar = new Image(leftBarRegion);
        leftBar.setPosition(wallRegion.getRegionWidth() + stairRegion.getRegionWidth(), floorHeight);
        Image rightBar = new Image(rightBarRegion);
        float maxRightX = restaurantWidth - rightBarRegion.getRegionWidth();
        rightBar.setPosition(maxRightX, floorHeight);
        Image table = new Image(tableRegion);
        table.setPosition(maxRightX, floorHeight + 2f * tableRegion.getRegionHeight());
        Image flowerpots = new Image(atlas.findRegion(Assets.RESTAURANT_POTS));
        flowerpots.setX(40f);
        flowerpots.setTouchable(Touchable.disabled);
        // background
        backgroundStage.addActor(kitchenWall);
        backgroundStage.addActor(kitchenFloor);
        backgroundStage.addActor(hallFloor);
        backgroundStage.addActor(bottomWall);
        backgroundStage.addActor(topWall);
        backgroundStage.addActor(leftBar);
        backgroundStage.addActor(rightBar);
        backgroundStage.addActor(table);
        backgroundStage.addActor(stairs);
        backgroundStage.addActor(kitchen);
        foregroundStage.addActor(flowerpots);
        // pathfinder graph and flowers
        float chairGapX = (kitchen.getWidth() - chairRegion.getRegionWidth() * 2f) / 3f;
        float chairGapY = (floorHeight - chairRegion.getRegionHeight() * 3f) / 4f;
        Rectangle[] obstacles = new Rectangle[13];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                Group chairGroup = new Group();
                chairGroup.setPosition(
                        j * (chairRegion.getRegionWidth() + chairGapX) + chairGapX + maxStairsX,
                        i * (chairRegion.getRegionHeight() + chairGapY) + chairGapY
                );
                chairGroup.addActor(new Image(chairRegion));
                for (int f = 1; f <= 3; f++) {
                    Image flower = new Image(atlas.findRegion(Assets.RESTAURANT_FLOWER_FOLDER + f));
                    flower.setPosition(
                            (float) (Math.random() * (chairRegion.getRegionWidth() - 10f)),
                            chairRegion.getRegionHeight()
                    );
                    chairGroup.addActor(flower);
                }
                backgroundStage.addActor(chairGroup);
                obstacles[i * 2 + j] = new Rectangle(chairGroup.getX(), chairGroup.getY(), chairRegion.getRegionWidth(), chairRegion.getRegionHeight());
            }
        }
        obstacles[6] = new Rectangle(leftBar.getX(), leftBar.getY(), leftBarRegion.getRegionWidth(), leftBarRegion.getRegionHeight());
        obstacles[7] = new Rectangle(rightBar.getX(), rightBar.getY(), rightBarRegion.getRegionWidth(), rightBarRegion.getRegionHeight());
        obstacles[8] = new Rectangle(stairs.getX(), stairs.getY(), stairRegion.getRegionWidth(), stairs.getHeight());
        obstacles[9] = new Rectangle(kitchen.getX(), kitchen.getY(), kitchen.getWidth(), kitchen.getHeight());
        obstacles[10] = new Rectangle(table.getX(), table.getY(), tableRegion.getRegionWidth(), tableRegion.getRegionHeight());
        obstacles[11] = new Rectangle(0, topWall.getY(), wallRegion.getRegionWidth(), Gdx.graphics.getHeight());
        obstacles[12] = new Rectangle(restaurantWidth, 0, 1, Gdx.graphics.getHeight());
        // recipe book
        this.recipeBookActor = new RecipeBookActor(this, recipeBookRegion, Gdx.graphics.getWidth() / 2f - recipeBookRegion.getRegionWidth() / 2f / 2f, (Gdx.graphics.getHeight() - recipeBookRegion.getRegionHeight()) / 2f);
        Entity player = EntityFactory.buildPlayerEntity(engine, 10f, 40f, this, (Animated2DCamera) viewport.getCamera());
        EntityFactory.createInteractArea(engine, kitchen.getX() + kitchen.getStoveX(), kitchen.getY(), stoveRegion.getRegionWidth(), stoveRegion.getRegionHeight(), InteractType.RECIPE_BOOK_AREA);
        engine.addSystem(new InteractSystem(player));
        this.graph = new GraphImpl(1f, Mappers.TRANSFORM_MAPPER.get(player).width, obstacles);
        foregroundStage.addListener(new RestaurantInputListener(player, graph, new Entity[0]));
    }

    public GraphImpl getGraph() {
        return graph;
    }

    public RecipeBookActor getRecipeBookActor() {
        return recipeBookActor;
    }

    public float getRestaurantWidth() {
        return restaurantWidth;
    }

    public void cacheForeground() {
        if (recipeBookActor.isOpened()) return;
        cachedUi = foregroundStage.getActors();
        foregroundStage.clear();
    }

    public void restoreCache() {
        if (cachedUi == null) return;
        foregroundStage.clear();
        for (int i = 0; i < cachedUi.size; i++) {
            foregroundStage.addActor(cachedUi.get(i));
        }
    }

    public void openRecipeBook() {
        foregroundStage.addActor(recipeBookActor);
        foregroundStage.addActor(recipeBookActor.getChildren());
    }

    public MinigameActor getMinigameActor() {
        return minigameActor;
    }

    public void openMinigame(RecipeData data) {
        this.minigameActor = new MinigameActor(data);
        foregroundStage.addActor(minigameActor);
        foregroundStage.addActor(minigameActor.getChildren());
    }
}
