package ru.compot.pomsrest;

import com.badlogic.gdx.assets.AssetManager;
import ru.compot.pomsrest.ashley.config.PlayerConfig;
import ru.compot.pomsrest.screens.StageScreen;

import static ru.compot.pomsrest.utils.constants.Assets.*;

public class GameCore {

    public static final GameCore INSTANCE = new GameCore();
    public static final float SCREEN_WIDTH = 400, SCREEN_HEIGHT = 700;
    public static final float CAMERA_WIDTH = SCREEN_WIDTH / 2f;

    public final PlayerConfig playerConfig = new PlayerConfig();
    private final AssetManager assetManager = new AssetManager();

    private GameCore() {
    }

    public void init() {
        assetManager.load(WORLD_BACKGROUND, TEXTURE);
        assetManager.load(RESTAURANT_BACKGROUND, TEXTURE);
        assetManager.load(PLAYER_LLAMA, TEXTURE_ATLAS);
        assetManager.finishLoading();
    }

    public <T> T getAsset(String name) {
        return assetManager.get(name);
    }

    public void setCurrentScreen(StageScreen currentScreen) {
        Application.INSTANCE.setScreenWithTransition(currentScreen);
    }

    public void dispose() {
        assetManager.dispose();
    }
}
