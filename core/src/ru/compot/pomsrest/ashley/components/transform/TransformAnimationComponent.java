package ru.compot.pomsrest.ashley.components.transform;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import ru.compot.pomsrest.ashley.utils.constants.enums.TransformAnimationType;
import ru.compot.pomsrest.ashley.utils.VectorAnimation;

public class TransformAnimationComponent implements Component, Pool.Poolable {

    public VectorAnimation animation;
    public TransformAnimationType type;
    public Runnable onFinish;
    public float estimatedTime;

    @Override
    public void reset() {
        animation = null;
        type = null;
        estimatedTime = 0;
    }

    public void animate(TransformAnimationType type, float speed, TransformComponent transform, float destX, float destY) {
        this.reset();
        this.type = type;
        this.animation = new VectorAnimation(speed, transform.x, transform.y, destX, destY);
    }
}
