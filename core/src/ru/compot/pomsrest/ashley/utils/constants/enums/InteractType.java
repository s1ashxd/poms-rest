package ru.compot.pomsrest.ashley.utils.constants.enums;

import com.badlogic.ashley.core.Entity;
import ru.compot.pomsrest.GameCore;
import ru.compot.pomsrest.ashley.components.PlayerComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.screens.restaurant.RestaurantScreen;
import ru.compot.pomsrest.screens.world.WorldScreen;
import ru.compot.pomsrest.utils.constants.AnimationIDs;
import ru.compot.pomsrest.utils.constants.OtherConstants;

public enum InteractType {
    WORLD_ENTER_AREA((player, collider) -> {
        PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player);
        TransformComponent playerTransform = Mappers.TRANSFORM_MAPPER.get(player);
        TransformAnimationComponent playerTransformAnimation = Mappers.TRANSFORM_ANIMATION_MAPPER.get(player);
        TextureAnimationComponent playerTextureAnimation = Mappers.TEXTURE_ANIMATION_MAPPER.get(player);
        TransformComponent areaTransform = Mappers.TRANSFORM_MAPPER.get(collider);
        playerData.moveBlocked = true;
        float destX = areaTransform.x + (areaTransform.width - playerTransform.width) / 2f;
        playerTextureAnimation.animate(
                playerTransform.x < destX ? AnimationIDs.PLAYER_RIGHT_RUNNING : AnimationIDs.PLAYER_LEFT_RUNNING,
                true
        );
        playerTransformAnimation.animate(
                TransformAnimationType.POSITION,
                OtherConstants.PLAYER_SPEED,
                playerTransform.x,
                playerTransform.y,
                destX,
                playerTransform.y,
                () -> {
                    playerTextureAnimation.reset();
                    playerTextureAnimation.animate(AnimationIDs.PLAYER_ENTERING, true);
                    playerTransformAnimation.animate(
                            TransformAnimationType.SCALE,
                            0.4f,
                            playerTransform.scaleX,
                            playerTransform.scaleY,
                            0.7f,
                            0.7f,
                            null
                    );
                    GameCore.INSTANCE.playerConfig.lastWorldPosition = playerTransform.x;
                    playerTransformAnimation.animate(
                            TransformAnimationType.POSITION,
                            50f,
                            playerTransform.x,
                            playerTransform.y,
                            playerTransform.x,
                            playerTransform.y + 25f,
                            () -> {
                                playerTextureAnimation.reset();
                                GameCore.INSTANCE.setCurrentScreen(new RestaurantScreen());
                            }
                    );
                }
        );
        playerData.camera.animate(destX + playerTransform.originX, playerData.camera.position.y);
    }),
    RESTAURANT_EXIT_AREA((player, collider) -> {
        PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player);
        TransformComponent playerTransform = Mappers.TRANSFORM_MAPPER.get(player);
        TransformAnimationComponent playerTransformAnimation = Mappers.TRANSFORM_ANIMATION_MAPPER.get(player);
        TextureAnimationComponent playerTextureAnimation = Mappers.TEXTURE_ANIMATION_MAPPER.get(player);
        playerData.moveBlocked = true;
        playerTextureAnimation.animate(AnimationIDs.PLAYER_LEFT_RUNNING, true);
        playerTransformAnimation.animate(
                TransformAnimationType.POSITION,
                OtherConstants.PLAYER_SPEED,
                playerTransform.x,
                playerTransform.y,
                0f,
                WorldScreen.GROUND_HEIGHT,
                () -> {
                    playerTextureAnimation.reset();
                    GameCore.INSTANCE.setCurrentScreen(new WorldScreen(GameCore.INSTANCE.playerConfig.lastWorldPosition));
                }
        );
    });

    private final InteractApplier action;

    InteractType(InteractApplier action) {
        this.action = action;
    }

    public void acceptAction(Entity player, Entity collider) {
        action.apply(player, collider);
    }

    @FunctionalInterface
    private interface InteractApplier {
        void apply(Entity player, Entity collider);
    }
}
