package ru.compot.pomsrest.ashley.utils.factory;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import ru.compot.pomsrest.GameCore;
import ru.compot.pomsrest.ashley.components.CollisionComponent;
import ru.compot.pomsrest.ashley.components.PlayerComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.constants.AnimationIDs;
import ru.compot.pomsrest.ashley.constants.Assets;

public class EntityFactory {

    private EntityFactory() {
    }

    public static Entity createGroundEntity(Engine engine, float groundWidth, float groundHeight) {
        return EntityBuilder.create(engine)
                .setSize(groundWidth, groundHeight)
                .setTexture(GameCore.getAsset(Assets.BACKGROUND))
                .build();
    }

    public static Entity createPlayerEntity(
            Engine engine,
            float x, float y,
            TextureRegion rightIdle,
            TextureRegion leftIdle,
            TextureRegion enteringIdle,
            Array<? extends TextureRegion> rightRunning,
            Array<? extends TextureRegion> leftRunning,
            Array<? extends TextureRegion> entering
    ) {
        return EntityBuilder.create(engine)
                .setPosition(x, y)
                .setSize(70f, 70f)
                .setTextureRegion(rightIdle)
                .setTextureSize(70f, 70f)
                .addComponent(TextureAnimationComponent.class, tac -> {
                    tac.add(AnimationIDs.PLAYER_RIGHT_RUNNING, new Animation<>(.3f, rightRunning), rightIdle);
                    tac.add(AnimationIDs.PLAYER_LEFT_RUNNING, new Animation<>(.3f, leftRunning), leftIdle);
                    tac.add(AnimationIDs.PLAYER_ENTERING, new Animation<>(.3f, entering), enteringIdle);
                })
                .addComponent(TransformAnimationComponent.class)
                .addComponent(PlayerComponent.class)
                .build();
    }

    public static Entity createInteractArea(Engine engine, float x, float y, float width, float height, Runnable onInteract) {
        return EntityBuilder.create(engine)
                .setPosition(x, y)
                .setSize(width, height)
                .addComponent(CollisionComponent.class, cc -> cc.onInteract = onInteract)
                .build();
    }

}
