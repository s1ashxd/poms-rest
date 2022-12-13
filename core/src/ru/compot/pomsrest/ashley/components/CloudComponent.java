package ru.compot.pomsrest.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

public class CloudComponent implements Component, Pool.Poolable {

    public Entity foodEntity;

    @Override
    public void reset() {

    }
}
