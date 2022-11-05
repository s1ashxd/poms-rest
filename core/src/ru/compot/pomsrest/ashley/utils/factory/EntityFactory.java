package ru.compot.pomsrest.ashley.utils.factory;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.compot.pomsrest.GameCore;
import ru.compot.pomsrest.ashley.components.InteractComponent;
import ru.compot.pomsrest.ashley.components.PlayerComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.utils.constants.enums.InteractType;
import ru.compot.pomsrest.screens.restaurant.RestaurantScreen;
import ru.compot.pomsrest.screens.world.WorldScreen;
import ru.compot.pomsrest.utils.constants.AnimationIDs;
import ru.compot.pomsrest.utils.constants.Assets;

public class EntityFactory {

    private EntityFactory() {
    }

    public static Entity createWorldGround(Engine engine) {
        return EntityBuilder.create(engine)
                .setZIndex(0)
                .setSize(WorldScreen.WORLD_WIDTH, WorldScreen.GROUND_HEIGHT)
                .setTexture(GameCore.INSTANCE.getAsset(Assets.WORLD_BACKGROUND))
                .build();
    }

    public static Entity createRestaurantBackground(Engine engine) {
        return EntityBuilder.create(engine)
                .setZIndex(0)
                .setSize(RestaurantScreen.RESTAURANT_WIDTH, RestaurantScreen.RESTAURANT_HEIGHT)
                .setTexture(GameCore.INSTANCE.getAsset(Assets.RESTAURANT_BACKGROUND))
                .build();
    }

    public static Entity createPlayerEntity(Engine engine, float x, float y) {
        TextureAtlas atlas = GameCore.INSTANCE.getAsset(Assets.PLAYER_LLAMA);
        return EntityBuilder.create(engine)
                .setPosition(x, y)
                .setZIndex(10)
                .setSize(70f, 70f)
                .setTextureRegion(atlas.findRegion(Assets.RIGHT_IDLE_REGION))
                .setTextureSize(70f, 70f)
                .addComponent(TextureAnimationComponent.class, tac -> {
                    tac.add(
                            AnimationIDs.PLAYER_RIGHT_RUNNING,
                            new Animation<>(Assets.RIGHT_FRAME_DURATION, atlas.findRegions(Assets.RIGHT_RUNNING_REGIONS)),
                            atlas.findRegion(Assets.RIGHT_IDLE_REGION)
                    );
                    tac.add(
                            AnimationIDs.PLAYER_LEFT_RUNNING,
                            new Animation<>(Assets.LEFT_FRAME_DURATION, atlas.findRegions(Assets.LEFT_RUNNING_REGIONS)),
                            atlas.findRegion(Assets.LEFT_IDLE_REGION)
                    );
                    tac.add(
                            AnimationIDs.PLAYER_ENTERING,
                            new Animation<>(Assets.ENTERING_FRAME_DURATION, atlas.findRegions(Assets.ENTERING_REGIONS)),
                            atlas.findRegion(Assets.ENTERING_IDLE_REGION)
                    );
                })
                .addComponent(TransformAnimationComponent.class)
                .addComponent(PlayerComponent.class)
                .build();
    }

    public static Entity createInteractArea(Engine engine, float x, float y, float width, float height, InteractType type) {
        return EntityBuilder.create(engine)
                .setPosition(x, y)
                .setSize(width, height)
                .addComponent(InteractComponent.class, cc -> cc.interactType = type)
                .build();
    }

}
