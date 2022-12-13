package ru.compot.pomsrest.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import ru.compot.pomsrest.scene2d.restaurant.RecipeData;

public class FoodComponent implements Component, Pool.Poolable {

    public RecipeData recipe;
    public int position;
    public float positionX, positionY;
    public boolean dragging;

    @Override
    public void reset() {
        dragging = false;
    }
}
