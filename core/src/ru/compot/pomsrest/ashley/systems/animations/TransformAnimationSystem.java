package ru.compot.pomsrest.ashley.systems.animations;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.constants.Mappers;
import ru.compot.pomsrest.ashley.constants.Priorities;
import ru.compot.pomsrest.ashley.constants.enums.TransformAnimationType;

public class TransformAnimationSystem extends IteratingSystem {

    public TransformAnimationSystem() {
        super(Family.all(TransformComponent.class, TransformAnimationComponent.class).get(), Priorities.TRANSFORM_ANIMATION_SYSTEM);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformAnimationComponent transformAnimation = Mappers.TRANSFORM_ANIMATION_MAPPER.get(entity);
        if (transformAnimation.animation == null || transformAnimation.type == null) {
            transformAnimation.reset();
            return;
        }
        TransformComponent transform = Mappers.TRANSFORM_MAPPER.get(entity);
        TransformAnimationType type = transformAnimation.type;
        Vector2 key;
        boolean finished;
        if (transformAnimation.animation.isAnimationFinished(transformAnimation.estimatedTime)) {
            key = transformAnimation.animation.getEnd();
            finished = true;
        } else {
            key = transformAnimation.animation.getKeyFrame(transformAnimation.estimatedTime);
            transformAnimation.estimatedTime += deltaTime;
            finished = false;
        }
        switch (type) {
            case POSITION:
                transform.x = key.x;
                transform.y = key.y;
                break;
            case SIZE:
                transform.width = key.x;
                transform.height = key.y;
                break;
            case SCALE:
                transform.scaleX = key.x;
                transform.scaleY = key.y;
                break;
        }
        if (finished) {
            transformAnimation.onFinish.run();
            transformAnimation.reset();
        }
    }
}
