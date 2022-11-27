package ru.compot.pomsrest.ashley.utils.constants.enums;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public enum NPCType {
    PURPLE(null, null, false),
    BLUE(null, null, false),
    TIGER(null, null, false),
    PINKY(null, null, false),
    WHITE(null, null, false),
    SUNSET(null, null, false),
    LIGHT_PURPLE(null, null, true),
    COSMIC_BLUE(null, null, true),
    AGENT(null, null, true),
    PINK(null, null, true),
    COSMIC_GREEN(null, null, true),
    ELVIS(null, null, true);

    private final TextureRegion sit;
    private final Array<TextureRegion> run;
    private final boolean direction;

    NPCType(TextureRegion sit, Array<TextureRegion> run, boolean direction) {
        this.sit = sit;
        this.run = run;
        this.direction = direction;
    }

    public TextureRegion getSit() {
        return sit;
    }

    public Array<TextureRegion> getRun() {
        return run;
    }

    public boolean getDirection() {
        return direction;
    }
}