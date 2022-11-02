package ru.compot.pomsrest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.compot.pomsrest.screens.GameScreen;

import static ru.compot.pomsrest.assets.AssetConstants.*;

public class GameCore extends Game {

    public static final GameCore INSTANCE = new GameCore();

    private AssetManager assetManager;
    private float screenWidth, screenHeight;

    private GameCore() {
    }

    public static <T> T getAsset(String name) {
        return INSTANCE.assetManager.get(name);
    }

    public static float getScreenWidth() {
        return INSTANCE.screenWidth;
    }

    public static float getScreenHeight() {
        return INSTANCE.screenHeight;
    }

    @Override
    public void create() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        assetManager = new AssetManager();
        assetManager.load(BACKGROUND, TEXTURE);
        assetManager.load(PLAYER_LLAMA, TEXTURE_ATLAS);
        assetManager.finishLoading();
        setScreen(new GameScreen());
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        super.render();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
