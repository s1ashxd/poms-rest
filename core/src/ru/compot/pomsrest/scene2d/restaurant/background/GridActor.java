package ru.compot.pomsrest.scene2d.restaurant.background;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GridActor extends Actor {
    private final TextureRegion region;
    private int horCells, vertCells;

    public GridActor(TextureRegion region, float x, float y, float width, float height) {
        this.region = region;
        setPosition(x, y);
        setSize(width, height);
        updateCells();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int i = 0; i < horCells; i++) {
            for (int j = 0; j < vertCells; j++) {
                batch.draw(region, getX() + i * region.getRegionWidth(), getY() + j * region.getRegionHeight());
            }
        }
    }

    private void updateCells() {
        horCells = (int) Math.ceil(getWidth() / region.getRegionWidth());
        vertCells = (int) Math.ceil(getHeight() / region.getRegionHeight());
    }
}
