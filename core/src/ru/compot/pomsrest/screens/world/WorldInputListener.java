package ru.compot.pomsrest.screens.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import ru.compot.pomsrest.ashley.components.PlayerComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.ashley.utils.constants.enums.TransformAnimationType;
import ru.compot.pomsrest.utils.constants.AnimationIDs;
import ru.compot.pomsrest.utils.constants.OtherConstants;

public class WorldInputListener extends InputListener {

    private final Entity player;

    public WorldInputListener(Entity player) {
        this.player = player;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player);
        if (playerData.moveBlocked) return false;
        TransformComponent transform = Mappers.TRANSFORM_MAPPER.get(player);
        TransformAnimationComponent transformAnimation = Mappers.TRANSFORM_ANIMATION_MAPPER.get(player);
        TextureAnimationComponent textureAnimation = Mappers.TEXTURE_ANIMATION_MAPPER.get(player);
        playerData.interact(x, y);
        byte animationId = AnimationIDs.PLAYER_RIGHT_RUNNING;
        if (x < transform.x + transform.originX) animationId = AnimationIDs.PLAYER_LEFT_RUNNING;
        textureAnimation.animate(animationId, true);
        transformAnimation.animate(
                TransformAnimationType.POSITION,
                OtherConstants.PLAYER_SPEED,
                transform.x,
                transform.y,
                x - transform.originX,
                transform.y,
                textureAnimation::reset
        );
        return true;
    }
}
