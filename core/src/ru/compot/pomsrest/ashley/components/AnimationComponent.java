package ru.compot.pomsrest.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

import java.util.HashMap;
import java.util.Map;

public class AnimationComponent implements Component, Pool.Poolable {

    public final Map<String, AnimationData> animations = new HashMap<>();
    public AnimationData currentAnimationData, lastAnimationData;
    public float estimatedTime;
    public boolean looping;

    public void start(String id, boolean looping) {
        this.reset();
        this.currentAnimationData = this.lastAnimationData = animations.get(id);
        this.looping = looping;
    }

    @Override
    public void reset() {
        currentAnimationData = null;
        estimatedTime = 0;
        looping = false;
    }

    public static class AnimationData {
        public final Animation<TextureRegion> animation;
        public final TextureRegion idleTexture;

        public AnimationData(Animation<TextureRegion> animation, TextureRegion idleTexture) {
            this.animation = animation;
            this.idleTexture = idleTexture;
        }
    }
}
