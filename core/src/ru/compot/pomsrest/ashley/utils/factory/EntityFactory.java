package ru.compot.pomsrest.ashley.utils.factory;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.compot.pomsrest.GameCore;
import ru.compot.pomsrest.ashley.components.InteractComponent;
import ru.compot.pomsrest.ashley.components.PlayerComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.utils.constants.enums.InteractType;
import ru.compot.pomsrest.utils.Animated2DCamera;
import ru.compot.pomsrest.utils.AtlasUtils;
import ru.compot.pomsrest.utils.constants.AnimationIDs;
import ru.compot.pomsrest.utils.constants.Assets;

public class EntityFactory {

    private EntityFactory() {
    }

    public static Entity buildPlayerEntity(Engine engine, float x, float y, Screen screen, Animated2DCamera camera) {
        TextureAtlas atlas = Assets.getAsset(Assets.PLAYER_LLAMA);
        return EntityBuilder.create(engine)
                .setPosition(x, y)
                .setZIndex(10)
                .setSize(70f, 70f)
                .setTextureRegion(atlas.findRegion(Assets.RIGHT_IDLE_REGION))
                .setTextureSize(70f, 70f)
                .addComponent(TextureAnimationComponent.class, tac -> {
                    tac.add(
                            AnimationIDs.PLAYER_RIGHT_RUNNING,
                            new Animation<>(Assets.PLAYER_FRAME_DURATION, AtlasUtils.findRegionFolder(atlas, Assets.RIGHT_RUNNING_FOLDER)),
                            atlas.findRegion(Assets.RIGHT_IDLE_REGION)
                    );
                    tac.add(
                            AnimationIDs.PLAYER_LEFT_RUNNING,
                            new Animation<>(Assets.PLAYER_FRAME_DURATION, AtlasUtils.findRegionFolder(atlas, Assets.LEFT_RUNNING_FOLDER)),
                            atlas.findRegion(Assets.LEFT_IDLE_REGION)
                    );
                    tac.add(
                            AnimationIDs.PLAYER_ENTERING,
                            new Animation<>(Assets.PLAYER_FRAME_DURATION, AtlasUtils.findRegionFolder(atlas, Assets.FORWARD_RUNNING)),
                            atlas.findRegion(Assets.FORWARD_IDLE_REGION)
                    );
                })
                .addComponent(TransformAnimationComponent.class)
                .addComponent(PlayerComponent.class, pc -> {
                    pc.screen = screen;
                    pc.camera = camera;
                })
                .build();
    }

    public static void createInteractArea(Engine engine, float x, float y, float width, float height, InteractType type) {
        EntityBuilder.create(engine)
                .setPosition(x, y)
                .setSize(width, height)
                .addComponent(InteractComponent.class, cc -> cc.interactType = type)
                .build();
    }

}
