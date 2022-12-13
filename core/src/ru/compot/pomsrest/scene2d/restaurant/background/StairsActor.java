package ru.compot.pomsrest.scene2d.restaurant.background;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class StairsActor extends Actor {

    private final TextureRegion stair;

    public StairsActor(TextureRegion stair, float x, float y, float height) {
        this.stair = stair;
        setPosition(x, y);
        setSize(stair.getRegionWidth(), height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float currentHeight = getHeight();
        while (currentHeight > 0f) {
            batch.draw(stair, getX(), getY() + currentHeight);
            currentHeight -= stair.getRegionHeight();
        }
    }
}
