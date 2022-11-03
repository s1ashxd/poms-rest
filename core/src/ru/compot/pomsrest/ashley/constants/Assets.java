package ru.compot.pomsrest.ashley.constants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {

    public static final String BACKGROUND = "textures/background.png";
    public static final String PLAYER_LLAMA = "textures/player-llama/player-llama.atlas";
    public static final String RIGHT_RUNNING_REGIONS = "right-running";
    public static final String RIGHT_IDLE_REGION = "right-running";
    public static final String LEFT_RUNNING_REGIONS = "left-running";
    public static final String LEFT_IDLE_REGION = "left-running";
    public static final String ENTERING_IDLE_REGION = "entering";
    public static final String ENTERING_REGIONS = "entering";

    public static final Class<Texture> TEXTURE = Texture.class;
    public static final Class<TextureAtlas> TEXTURE_ATLAS = TextureAtlas.class;

    private Assets() {
    }
}
