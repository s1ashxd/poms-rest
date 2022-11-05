package ru.compot.pomsrest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import ru.compot.pomsrest.screens.StageScreen;
import ru.compot.pomsrest.screens.TestScreen;
import ru.compot.pomsrest.screens.world.WorldScreen;

import static com.badlogic.gdx.Application.LOG_DEBUG;

public class Application extends Game {

    public static final Application INSTANCE = new Application();

    private Application() {

    }

    @Override
    public void create() {
        GameCore.INSTANCE.init();
        setScreen(new WorldScreen(10f));
    }

    public void setScreenWithTransition(Screen screen) {
        if (this.screen == null) return;
        if (this.screen instanceof StageScreen)
            ((StageScreen) this.screen).getTransition().startTransition(screen);
        else setScreen(screen);
    }
}
