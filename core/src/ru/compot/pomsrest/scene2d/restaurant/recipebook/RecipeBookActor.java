package ru.compot.pomsrest.scene2d.restaurant.recipebook;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.compot.pomsrest.scene2d.restaurant.RecipeData;
import ru.compot.pomsrest.scene2d.restaurant.RecipesRegistry;
import ru.compot.pomsrest.screens.restaurant.RestaurantScreen;
import ru.compot.pomsrest.utils.constants.Assets;

public class RecipeBookActor extends Actor {

    private final ShapeRenderer renderer = new ShapeRenderer();
    private final Group children = new Group();
    private final RestaurantScreen screen;
    private final TextureAtlas atlas;
    private final TextureRegion background;
    private final float prefX;
    private boolean opened;

    public RecipeBookActor(RestaurantScreen screen, TextureRegion background, float prefX, float y) {
        this.screen = screen;
        this.background = background;
        this.prefX = prefX;
        this.atlas = Assets.getAsset(Assets.RECIPES_ATLAS);
        setColor(new Color(0f, 0f, 0f, 0f));
        setPosition(Gdx.graphics.getWidth(), y);
        setSize(background.getRegionWidth() / 2f, background.getRegionHeight());
        switchRecipe(RecipesRegistry.BUYABES);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!opened) return;
        Camera camera = getStage().getViewport().getCamera();
        float halfViewportWidth = camera.viewportWidth / 2f;
        float halfViewportHeight = camera.viewportHeight / 2f;
        children.setPosition(getX() + camera.position.x - halfViewportWidth, getY() + camera.position.y - halfViewportHeight);
        batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(new Color(0f, 0f, 0f, getColor().a));
        renderer.rect(camera.position.x - halfViewportWidth, camera.position.y - halfViewportHeight, camera.viewportWidth, camera.viewportHeight);
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        batch.begin();
        batch.draw(background, getX() + camera.position.x - halfViewportWidth, getY() + camera.position.y - halfViewportHeight);
    }

    public boolean isOpened() {
        return opened;
    }

    public void switchRecipe(RecipeData data) {
        children.clear();
        Skin skin = Assets.getAsset(Assets.UI_SKIN);
        Label label = new Label(data.getTitle(), skin);
        label.setPosition(getWidth() / 2f - label.getWidth() / 2f + 10f, getHeight() - label.getHeight() - 40f);
        children.addActor(label);
        Image image = new Image(atlas.findRegion(data.getAtlasRegion()));
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Stage s = screen.getForegroundStage();
                s.clear();
                screen.openMinigame(data);
            }
        });
        image.setPosition(getWidth() / 2f - image.getWidth() / 2f + 10f, getHeight() - label.getHeight() - image.getHeight() - 20f);
        children.addActor(image);
        float cy = 0;
        for (RecipeData.RecipeComponent component : data.getRecipeComponents()) {
            ImageTextButton lab = new ImageTextButton(component.displayName, skin);
            lab.setPosition(getWidth() / 2f - lab.getWidth() / 2f + 10f, getHeight() - lab.getHeight() - image.getHeight() - cy - 40f);
            cy += lab.getHeight() + 10f;
            children.addActor(lab);
        }
    }

    public Group getChildren() {
        return children;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void open() {
        opened = true;
        addAction(Actions.parallel(Actions.alpha(0.5f, 1f), Actions.moveTo(prefX, getY(), 1f, Interpolation.sine)));
        setTouchable(Touchable.enabled);
    }

    public void close() {
        addAction(Actions.sequence(Actions.parallel(Actions.moveTo(Gdx.graphics.getWidth(), getY(), 1f, Interpolation.sine), Actions.alpha(0f, 1f)), Actions.run(() -> {
            opened = false;
            screen.restoreCache();
        })));
        setTouchable(Touchable.disabled);
    }
}
