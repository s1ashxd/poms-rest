package ru.compot.pomsrest.ashley.components.transform;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TransformComponent implements Component, Pool.Poolable {

    public float x, y, width, height;
    public float scaleX = 1f, scaleY = 1f;

    @Override
    public void reset() {
    }
}
