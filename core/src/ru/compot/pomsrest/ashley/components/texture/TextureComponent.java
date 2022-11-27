package ru.compot.pomsrest.ashley.components.texture;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

public class TextureComponent implements Component, Pool.Poolable {

    public TextureRegion region;
    public int zIndex;
    public float width, height;
    public boolean visible = true;

    @Override
    public void reset() {
        visible = false;
    }
}
