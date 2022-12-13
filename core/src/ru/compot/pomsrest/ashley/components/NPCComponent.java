package ru.compot.pomsrest.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;
import ru.compot.pomsrest.scene2d.restaurant.RecipeData;

public class NPCComponent implements Component, Pool.Poolable {

    public float targetX, targetY;
    public float xSitOffset, ySitOffset;
    public RecipeData food;
    public long lastTimeSpawn;
    public boolean spawned;
    public Entity cloud;

    public long startTimeEat = -1L;

    @Override
    public void reset() {
        food = null;
        spawned = false;
        lastTimeSpawn = 0L;
        startTimeEat = -1L;
    }
}
