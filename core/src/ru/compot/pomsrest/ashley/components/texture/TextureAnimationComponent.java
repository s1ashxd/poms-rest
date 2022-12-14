package ru.compot.pomsrest.ashley.components.texture;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

import java.util.concurrent.ConcurrentHashMap;

public class TextureAnimationComponent implements Component, Pool.Poolable {

    public final ConcurrentHashMap<Integer, AnimationData> animations = new ConcurrentHashMap<>();
    public Animation<TextureRegion> currentAnimation;
    public TextureRegion idleTexture;
    public float estimatedTime;
    public boolean looping;

    public void animate(int id, boolean looping) {
        this.reset();
        AnimationData data = animations.get(id);
        this.currentAnimation = data.animation;
        this.idleTexture = data.idleTexture;
        this.looping = looping;
    }

    public void add(int id, Animation<TextureRegion> region, TextureRegion idleTexture) {
        animations.put(id, new AnimationData(region, idleTexture));
    }

    @Override
    public void reset() {
        currentAnimation = null;
        estimatedTime = 0f;
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
