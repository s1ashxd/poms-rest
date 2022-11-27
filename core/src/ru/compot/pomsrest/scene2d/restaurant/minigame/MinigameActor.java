package ru.compot.pomsrest.scene2d.restaurant.minigame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.compot.pomsrest.scene2d.restaurant.RecipeData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.compot.pomsrest.utils.constants.Assets.*;

public class MinigameActor extends Actor {

    private static final int IDLE = 0, NORMAL = 1, SHIT = 2;

    private final ShapeRenderer renderer = new ShapeRenderer();
    private final Group children = new Group();
    private final Map<String, Image> map = new HashMap<>();
    private final TextureAtlas atlas = getAsset(MINIGAME_ATLAS);
    private final TextureRegion background = new TextureRegion((Texture) getAsset(MINIGAME_BACKGROUND));
    private final TextureRegion normalFood = atlas.findRegion(NORMAL_FOOD);
    private final TextureRegion shitFood = atlas.findRegion(SHIT_FOOD);
    private final RecipeData recipe;
    private final List<String> ingredients = new ArrayList<>();
    private int cookingState;

    public MinigameActor(RecipeData recipe) {
        this.recipe = recipe;
        map.put("ice_cream",      addListenerTo(new Image(atlas.findRegion(INGREDIENT_ICE_CREAM)), new MinigameListener(INGREDIENT_ICE_CREAM, this)));
        map.put("tomato",         addListenerTo(new Image(atlas.findRegion(INGREDIENT_TOMATO)), new MinigameListener(INGREDIENT_TOMATO, this)));
        map.put("wine",           addListenerTo(new Image(atlas.findRegion(INGREDIENT_WINE)), new MinigameListener(INGREDIENT_WINE, this)));
        map.put("octopus",        addListenerTo(new Image(atlas.findRegion(INGREDIENT_OCTOPUS)), new MinigameListener(INGREDIENT_OCTOPUS, this)));
        map.put("whipping_cream", addListenerTo(new Image(atlas.findRegion(INGREDIENT_WHIPPING_CREAM)), new MinigameListener(INGREDIENT_WHIPPING_CREAM, this)));
        map.put("egg",            addListenerTo(new Image(atlas.findRegion(INGREDIENT_EGG)), new MinigameListener(INGREDIENT_EGG, this)));
        map.put("lemon",          addListenerTo(new Image(atlas.findRegion(INGREDIENT_LEMON)), new MinigameListener(INGREDIENT_LEMON, this)));
        map.put("biscuit",        addListenerTo(new Image(atlas.findRegion(INGREDIENT_BISCUIT)), new MinigameListener(INGREDIENT_BISCUIT, this)));
        map.put("chicken",        addListenerTo(new Image(atlas.findRegion(INGREDIENT_CHICKEN)), new MinigameListener(INGREDIENT_CHICKEN, this)));
        map.put("curd",           addListenerTo(new Image(atlas.findRegion(INGREDIENT_CURD)), new MinigameListener(INGREDIENT_CURD, this)));
        map.put("lobster",        addListenerTo(new Image(atlas.findRegion(INGREDIENT_LOBSTER)), new MinigameListener(INGREDIENT_LOBSTER, this)));
        map.put("cherry",         addListenerTo(new Image(atlas.findRegion(INGREDIENT_CHERRY)), new MinigameListener(INGREDIENT_CHERRY, this)));
        map.put("topping",        addListenerTo(new Image(atlas.findRegion(INGREDIENT_TOPPING)), new MinigameListener(INGREDIENT_TOPPING, this)));
        map.put("flour",          addListenerTo(new Image(atlas.findRegion(INGREDIENT_FLOUR)), new MinigameListener(INGREDIENT_FLOUR, this)));
        map.put("mussel",         addListenerTo(new Image(atlas.findRegion(INGREDIENT_MUSSEL)), new MinigameListener(INGREDIENT_MUSSEL, this)));
        map.put("crackers",       addListenerTo(new Image(atlas.findRegion(INGREDIENT_CRACKERS)), new MinigameListener(INGREDIENT_CRACKERS, this)));
        map.put("spices",         addListenerTo(new Image(atlas.findRegion(INGREDIENT_SPICES)), new MinigameListener(INGREDIENT_SPICES, this)));
        map.put("juice",          addListenerTo(new Image(atlas.findRegion(INGREDIENT_JUICE)), new MinigameListener(INGREDIENT_JUICE, this)));
        map.put("shrimp",         addListenerTo(new Image(atlas.findRegion(INGREDIENT_SHRIMP)), new MinigameListener(INGREDIENT_SHRIMP, this)));
        map.put("onion",          addListenerTo(new Image(atlas.findRegion(INGREDIENT_ONION)), new MinigameListener(INGREDIENT_ONION, this)));
        map.put("puff_pastry",    addListenerTo(new Image(atlas.findRegion(INGREDIENT_PUFF_PASTRY)), new MinigameListener(INGREDIENT_PUFF_PASTRY, this)));
        map.put("sugar",          addListenerTo(new Image(atlas.findRegion(INGREDIENT_SUGAR)), new MinigameListener(INGREDIENT_SUGAR, this)));
        setPosition(Gdx.graphics.getWidth() / 2f - background.getRegionWidth() / 2f, Gdx.graphics.getHeight() / 2f - background.getRegionHeight() / 2f);
        float comX = 10f, comY = 10f;
        for (Map.Entry<String, Image> entry : map.entrySet()) {
            Image com = entry.getValue();
            children.addActor(com);
            if (entry.getKey().equals("lobster")) {
                com.setPosition(380 - com.getWidth(), 30);
                continue;
            }
            com.setPosition(5 + comX, comY);
            comX += com.getWidth()+5;
            if (comX > 370 - com.getWidth()) {
                comX = 10f;
                comY += com.getHeight();
                com.setPosition(5 + comX, comY);
            }
        }
    }

    private static Image addListenerTo(Image actor, ClickListener listener) {
        actor.addListener(listener);
        return actor;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Camera camera = getStage().getViewport().getCamera();
        float halfViewportWidth = camera.viewportWidth / 2f;
        float halfViewportHeight = camera.viewportHeight / 2f;
        float actorX = getX() + camera.position.x - halfViewportWidth;
        float actorY = getY() + camera.position.y - halfViewportHeight;
        children.setPosition(actorX, actorY);
        batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(new Color(0f, 0f, 0f, 0.5f));
        renderer.rect(camera.position.x - halfViewportWidth, camera.position.y - halfViewportHeight, camera.viewportWidth, camera.viewportHeight);
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        batch.begin();
        batch.draw(background, actorX, actorY);
        switch (cookingState) {
            case NORMAL:
                batch.draw(normalFood, actorX+160, actorY+350);
                break;
            case SHIT:
                batch.draw(shitFood, actorX+153, actorY+345);
                break;
        }
    }

    public Group getChildren() {
        return children;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void onAddIngredient() {
        cookingState = NORMAL;
        for (String ing : ingredients) {
            for (RecipeData.RecipeComponent rc : recipe.getRecipeComponents()) {
                if (!rc.id.equals(ing)) {
                    cookingState = SHIT;
                    return;
                }
            }
        }
    }
}
