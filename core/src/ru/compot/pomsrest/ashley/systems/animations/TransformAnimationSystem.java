package ru.compot.pomsrest.ashley.systems.animations;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.TransformAnimationData;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.ashley.utils.constants.Priorities;

public class TransformAnimationSystem extends IteratingSystem {

    public TransformAnimationSystem() {
        super(Family.all(TransformComponent.class, TransformAnimationComponent.class).get(), Priorities.TRANSFORM_ANIMATION_SYSTEM);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformAnimationComponent transformAnimation = Mappers.TRANSFORM_ANIMATION_MAPPER.get(entity);
        TransformComponent transform = Mappers.TRANSFORM_MAPPER.get(entity);
        for (TransformAnimationData tad : transformAnimation.animations) {
            Vector2 state = getAnimationState(tad, deltaTime);
            tad.type.acceptAction(transform, state);
            runFinishActions(transformAnimation, tad);
        }
    }

    private Vector2 getAnimationState(TransformAnimationData tad, float deltaTime) {
        Vector2 result = tad.animation.getKeyFrame(tad.estimatedTime);
        tad.estimatedTime += deltaTime;
        return result;
    }

    private void runFinishActions(TransformAnimationComponent component, TransformAnimationData data) {
        if (!data.animation.isAnimationFinished(data.estimatedTime)) return;
        Runnable onFinish = data.onFinish;
        if (onFinish != null) onFinish.run();
        component.animations.remove(data);
    }
}
