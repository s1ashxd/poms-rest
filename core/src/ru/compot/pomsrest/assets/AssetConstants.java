package ru.compot.pomsrest.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetConstants {

    public static final String BACKGROUND = "textures/background.png";
    public static final String PLAYER_LLAMA = "textures/player-llama/player-llama.atlas";
    public static final String RIGHT_RUNNING_REGIONS = "right-running";
    public static final String RIGHT_IDLE_REGION = "right-running";
    public static final String LEFT_RUNNING_REGIONS = "left-running";
    public static final String LEFT_IDLE_REGION = "left-running";

    public static final Class<Texture> TEXTURE = Texture.class;
    public static final Class<TextureAtlas> TEXTURE_ATLAS = TextureAtlas.class;

    private AssetConstants() {
    }
}
