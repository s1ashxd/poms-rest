package ru.compot.pomsrest.ashley.systems.animations;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ru.compot.pomsrest.ashley.components.texture.TextureAnimationComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureComponent;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.ashley.utils.constants.Priorities;

public class TextureAnimationSystem extends IteratingSystem {
    public TextureAnimationSystem() {
        super(Family.all(TextureComponent.class, TextureAnimationComponent.class).get(), Priorities.TEXTURE_ANIMATION_SYSTEM);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TextureAnimationComponent textureAnimation = Mappers.TEXTURE_ANIMATION_MAPPER.get(entity);
        TextureComponent texture = Mappers.TEXTURE_MAPPER.get(entity);
        if (textureAnimation.currentAnimation == null) {
            if (textureAnimation.idleTexture != null) {
                texture.region = textureAnimation.idleTexture;
                textureAnimation.idleTexture = null;
            }
            return;
        }
        if (textureAnimation.currentAnimation.isAnimationFinished(textureAnimation.estimatedTime)) {
            if (textureAnimation.looping) textureAnimation.estimatedTime = 0f;
            else {
                texture.region = textureAnimation.idleTexture;
                textureAnimation.idleTexture = null;
                textureAnimation.reset();
                return;
            }
        }
        texture.region = textureAnimation.currentAnimation.getKeyFrame(textureAnimation.estimatedTime);
        textureAnimation.estimatedTime += deltaTime;
    }
}
