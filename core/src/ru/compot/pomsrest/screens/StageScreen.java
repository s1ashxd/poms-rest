package ru.compot.pomsrest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import ru.compot.pomsrest.GameCore;
import ru.compot.pomsrest.scene2d.TransitionActor;

public abstract class StageScreen extends ScreenAdapter {

    protected final SpriteBatch batch = new SpriteBatch();
    protected final ExtendViewport viewport = new ExtendViewport(GameCore.SCREEN_WIDTH, GameCore.SCREEN_HEIGHT);
    protected final Stage stage = new Stage(viewport, batch);
    protected final TransitionActor transition = TransitionActor.addTransitionToScreen(this, 0f, 0f, 0f);

    protected StageScreen() {
        stage.addActor(transition);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        batch.setProjectionMatrix(viewport.getCamera().combined);
    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act(delta);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    public Stage getStage() {
        return stage;
    }

    public TransitionActor getTransition() {
        return transition;
    }
}
