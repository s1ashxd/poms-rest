package ru.compot.pomsrest.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class AtlasUtils {

    private AtlasUtils() {
    }

    public static Array<TextureAtlas.AtlasRegion> findRegionFolder(TextureAtlas atlas, String name) {
        Array<TextureAtlas.AtlasRegion> result = new Array<>();
        Array<TextureAtlas.AtlasRegion> regions = atlas.getRegions();
        for (int i = 0; i < regions.size; i++) {
            TextureAtlas.AtlasRegion region = regions.get(i);
            if (region.name.startsWith(name)) result.add(region);
        }
        return result;
    }

}
