package ru.compot.pomsrest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import ru.compot.pomsrest.GameCore;

public abstract class StageScreen extends ScreenAdapter {

    protected final SpriteBatch batch = new SpriteBatch();
    protected final FillViewport viewport = new FillViewport(GameCore.getScreenWidth(), GameCore.getScreenHeight());
    protected final Stage stage = new Stage(viewport, batch);

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
}
