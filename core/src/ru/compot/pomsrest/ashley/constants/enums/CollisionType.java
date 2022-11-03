package ru.compot.pomsrest.ashley.constants.enums;

import com.badlogic.ashley.core.Entity;
import ru.compot.pomsrest.ashley.components.PlayerComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.constants.AnimationIDs;
import ru.compot.pomsrest.ashley.constants.Mappers;
import ru.compot.pomsrest.ashley.constants.OtherConstants;

import java.util.function.BiConsumer;

public enum CollisionType {
    INTERACT_AREA((player, collider) -> {
        PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player);
        TransformComponent playerTransform = Mappers.TRANSFORM_MAPPER.get(player);
        TransformAnimationComponent playerTransformAnimation = Mappers.TRANSFORM_ANIMATION_MAPPER.get(player);
        TextureAnimationComponent playerTextureAnimation = Mappers.TEXTURE_ANIMATION_MAPPER.get(player);
        TransformComponent areaTransform = Mappers.TRANSFORM_MAPPER.get(collider);
        playerData.moveBlocked = true;
        float destX = areaTransform.x + (areaTransform.width - playerTransform.width) / 2f;
        playerTransformAnimation.animate(
                TransformAnimationType.POSITION,
                OtherConstants.PLAYER_SPEED,
                playerTransform,
                destX, playerTransform.y
        );
        playerTransformAnimation.onFinish = () -> {
            playerTextureAnimation.reset();
            playerData.moveBlocked = false;
            playerTransformAnimation.onFinish = null;
        };
        playerTextureAnimation.animate(
                playerTransform.x < destX ? AnimationIDs.PLAYER_RIGHT_RUNNING : AnimationIDs.PLAYER_LEFT_RUNNING,
                true
        );
    });

    private final BiConsumer<Entity, Entity> action;

    CollisionType(BiConsumer<Entity, Entity> action) {
        this.action = action;
    }

    public void acceptAction(Entity player, Entity collider) {
        action.accept(player, collider);
    }
}
