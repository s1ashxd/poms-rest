package ru.compot.pomsrest.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import ru.compot.pomsrest.utils.constants.OtherConstants;

public class AnimatedCamera extends OrthographicCamera {

    private VectorAnimation animation;
    private float estimatedTime;

    public void animate(float x, float y) {
        animation = animation == null
                ? new VectorAnimation(OtherConstants.PLAYER_SPEED, position.x, position.y, x, y, Interpolation.sine)
                : animation.copy(x, y, estimatedTime);
        estimatedTime = 0f;
    }

    public void updateAnimation(float delta) {
        if (animation == null) return;
        Vector2 state = animation.getKeyFrame(estimatedTime);
        position.set(state.x, state.y, 0f);
        if (animation.isAnimationFinished(estimatedTime)) {
            animation = null;
            estimatedTime = 0f;
            return;
        }
        estimatedTime += delta;
    }
}
