package ru.compot.pomsrest.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class FoodComponent implements Component, Pool.Poolable {

    public int food;
    public int index;
    public boolean dragging;
    public float initialX, initialY;

    @Override
    public void reset() {
        dragging = false;
    }
}
