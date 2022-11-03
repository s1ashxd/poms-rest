package ru.compot.pomsrest.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable {

    public boolean interactRequested;
    public boolean moveBlocked;

    @Override
    public void reset() {
        interactRequested = false;
        moveBlocked = false;
    }
}
