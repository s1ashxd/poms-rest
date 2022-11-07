package ru.compot.pomsrest.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class AnimatedCamera extends OrthographicCamera {

    private VectorAnimation animation;
    private float estimatedTime;

    public void animate(float x, float y) {
        if (animation != null) {
            animation.setEnd(x, y, estimatedTime);
            estimatedTime = 0;
            return;
        }
        animation = new VectorAnimation(100f, position.x, position.y, x, y, Interpolation.sine);
        estimatedTime = 0;
    }

    public void updateAnimation(float delta) {
        if (animation != null) {
            if (animation.isAnimationFinished(estimatedTime)) {
                animation = null;
                estimatedTime = 0;
            } else {
                estimatedTime += delta;
                Vector2 state = animation.getKeyFrame(estimatedTime);
                position.set(state.x, state.y, 0f);
            }
        }
    }
}
