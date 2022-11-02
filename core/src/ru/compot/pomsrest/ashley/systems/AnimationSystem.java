package ru.compot.pomsrest.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ru.compot.pomsrest.ashley.components.AnimationComponent;
import ru.compot.pomsrest.ashley.components.TextureComponent;
import ru.compot.pomsrest.ashley.constants.Mappers;
import ru.compot.pomsrest.ashley.constants.Priorities;

public class AnimationSystem extends IteratingSystem {
    public AnimationSystem() {
        super(Family.all(TextureComponent.class, AnimationComponent.class).get(), Priorities.ANIMATION_SYSTEM);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent ac = Mappers.ANIMATION_MAPPER.get(entity);
        TextureComponent tc = Mappers.TEXTURE_MAPPER.get(entity);
        if (ac.currentAnimationData == null) {
            if (ac.lastAnimationData != null)
                tc.region = ac.lastAnimationData.idleTexture;
            return;
        }
        if (ac.currentAnimationData.animation.isAnimationFinished(ac.estimatedTime)) {
            if (ac.looping) ac.estimatedTime = 0f;
            else {
                tc.region = ac.lastAnimationData.idleTexture;
                ac.lastAnimationData = null;
                ac.reset();
                return;
            }
        }
        tc.region = ac.currentAnimationData.animation.getKeyFrame(ac.estimatedTime);
        ac.estimatedTime += deltaTime;
    }
}
