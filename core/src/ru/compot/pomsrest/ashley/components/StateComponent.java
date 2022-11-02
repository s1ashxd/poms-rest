package ru.compot.pomsrest.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class StateComponent implements Component, Pool.Poolable {

    public float movePositionX = 0;
    public float speed;

    @Override
    public void reset() {
    }
}
