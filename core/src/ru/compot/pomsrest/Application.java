package ru.compot.pomsrest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.compot.pomsrest.screens.StageScreen;
import ru.compot.pomsrest.screens.world.WorldScreen;

public class Application extends Game {

    public static final Application INSTANCE = new Application();

    private Application() {
    }

    @Override
    public void create() {
        GameCore.INSTANCE.init();
        setScreen(new WorldScreen(WorldScreen.CAMERA_MOVE_OFFSET));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f, true);
        super.render();
    }

    @Override
    public void dispose() {
        GameCore.INSTANCE.dispose();
    }

    public void setScreenWithTransition(Screen screen) {
        if (this.screen == null) return;
        if (this.screen instanceof StageScreen)
            ((StageScreen) this.screen).getTransition().startTransition(screen);
        else setScreen(screen);
    }
}
