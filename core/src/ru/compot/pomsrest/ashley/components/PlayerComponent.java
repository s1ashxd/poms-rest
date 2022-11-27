package ru.compot.pomsrest.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import ru.compot.pomsrest.utils.Animated2DCamera;

public class PlayerComponent implements Component, Pool.Poolable {

    public final Vector2 interactPoint = new Vector2();
    public final Vector2 mousePoint = new Vector2();
    public boolean dragging;
    public Screen screen;
    public Animated2DCamera camera;
    public boolean interactRequested;
    public boolean moveBlocked;

    @Override
    public void reset() {
        interactPoint.set(0f, 0f);
        interactRequested = false;
        moveBlocked = false;
        dragging = false;
    }

    public void interact(float x, float y) {
        interactPoint.set(x, y);
        interactRequested = true;
    }
}
