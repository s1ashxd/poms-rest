package ru.compot.pomsrest.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import ru.compot.pomsrest.GameCore;
import ru.compot.pomsrest.ashley.components.PlayerComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.utils.constants.AnimationIDs;
import ru.compot.pomsrest.utils.constants.Assets;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.utils.constants.OtherConstants;
import ru.compot.pomsrest.ashley.utils.constants.enums.TransformAnimationType;
import ru.compot.pomsrest.ashley.systems.CollisionSystem;
import ru.compot.pomsrest.ashley.systems.animations.TextureAnimationSystem;
import ru.compot.pomsrest.ashley.systems.animations.TransformAnimationSystem;
import ru.compot.pomsrest.ashley.systems.render.DebugRenderSystem;
import ru.compot.pomsrest.ashley.systems.render.RenderSystem;
import ru.compot.pomsrest.ashley.utils.factory.EntityFactory;

public class GameScreen extends StageScreen {

    private static final float WORLD_WIDTH = 3332f, GROUND_HEIGHT = 62f;

    private final PooledEngine engine = new PooledEngine();

    public GameScreen() {
        EntityFactory.createGroundEntity(engine, WORLD_WIDTH, GROUND_HEIGHT);
        TextureAtlas playerAtlas = GameCore.getAsset(Assets.PLAYER_LLAMA);
        Entity player = EntityFactory.createPlayerEntity(
                engine,
                10f, GROUND_HEIGHT,
                playerAtlas.findRegion(Assets.RIGHT_IDLE_REGION),
                playerAtlas.findRegion(Assets.LEFT_IDLE_REGION),
                playerAtlas.findRegion(Assets.ENTERING_IDLE_REGION),
                playerAtlas.findRegions(Assets.RIGHT_RUNNING_REGIONS),
                playerAtlas.findRegions(Assets.LEFT_RUNNING_REGIONS),
                playerAtlas.findRegions(Assets.ENTERING_REGIONS)
        );
        EntityFactory.createInteractArea(
                engine,
                200f, GROUND_HEIGHT,
                160f, 200f,
                () -> Gdx.app.log("DEBUG", "Interact with Lobster")
        );
        engine.addSystem(new DebugRenderSystem());
        engine.addSystem(new RenderSystem(batch));
        engine.addSystem(new TextureAnimationSystem());
        engine.addSystem(new TransformAnimationSystem());
        engine.addSystem(new CollisionSystem(player));
        stage.addListener(new InputListener() {
            private int keyPressed;

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keyPressed != keycode) {
                    keyPressed = 0;
                    return false;
                }
                Mappers.TRANSFORM_ANIMATION_MAPPER.get(player).reset();
                Mappers.TEXTURE_ANIMATION_MAPPER.get(player).reset();
                return true;
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player);
                if (keycode == Input.Keys.SPACE) {
                    playerData.interactRequested = true;
                    return true;
                }
                if (playerData.moveBlocked || (keycode != Input.Keys.LEFT && keycode != Input.Keys.RIGHT)) return false;
                TransformComponent transform = Mappers.TRANSFORM_MAPPER.get(player);
                TransformAnimationComponent transformAnimation = Mappers.TRANSFORM_ANIMATION_MAPPER.get(player);
                TextureAnimationComponent textureAnimation = Mappers.TEXTURE_ANIMATION_MAPPER.get(player);
                float destX = 0;
                int animationId = AnimationIDs.PLAYER_LEFT_RUNNING;
                if (keycode == Input.Keys.RIGHT) {
                    destX = WORLD_WIDTH;
                    animationId = AnimationIDs.PLAYER_RIGHT_RUNNING;
                }
                keyPressed = keycode;
                transformAnimation.animate(TransformAnimationType.POSITION, OtherConstants.PLAYER_SPEED, transform, destX, transform.y);
                textureAnimation.animate(animationId, true);
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        engine.update(delta);
        super.render(delta);
    }
}
