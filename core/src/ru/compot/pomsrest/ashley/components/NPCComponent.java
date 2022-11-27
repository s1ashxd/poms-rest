package ru.compot.pomsrest.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class NPCComponent implements Component, Pool.Poolable {

    public long lastTimeSpawn;
    public long lastTimeEat;
    public int food = -1;
    public int requestedFood = -1;

    @Override
    public void reset() {
        requestedFood = food = -1;
    }
}
