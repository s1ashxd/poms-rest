package ru.compot.pomsrest.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import ru.compot.pomsrest.GameCore;
import ru.compot.pomsrest.ashley.components.AnimationComponent;
import ru.compot.pomsrest.ashley.components.BoundsComponent;
import ru.compot.pomsrest.ashley.components.StateComponent;
import ru.compot.pomsrest.ashley.constants.Mappers;
import ru.compot.pomsrest.ashley.systems.AnimationSystem;
import ru.compot.pomsrest.ashley.systems.DebugRenderSystem;
import ru.compot.pomsrest.ashley.systems.MovementSystem;
import ru.compot.pomsrest.ashley.systems.RenderSystem;
import ru.compot.pomsrest.ashley.utils.EntityBuilder;
import ru.compot.pomsrest.assets.AssetConstants;

public class GameScreen extends StageScreen {

    private static final float WORLD_WIDTH = 3332f, GROUND_HEIGHT = 62f;

    private final PooledEngine engine = new PooledEngine();

    public GameScreen() {
        engine.addSystem(new DebugRenderSystem());
        engine.addSystem(new RenderSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new AnimationSystem());
        EntityBuilder.create(engine)
                .setSize(WORLD_WIDTH, GROUND_HEIGHT)
                .setTexture(GameCore.getAsset(AssetConstants.BACKGROUND))
                .build();
        TextureAtlas playerAtlas = GameCore.getAsset(AssetConstants.PLAYER_LLAMA);
        Entity player = EntityBuilder.create(engine)
                .setPosition(10f, GROUND_HEIGHT)
                .setSize(70f, 70f)
                .setTextureRegion(playerAtlas.findRegion(AssetConstants.RIGHT_IDLE_REGION))
                .setTextureSize(70f, 70f)
                .addComponent(StateComponent.class, sc -> {
                    sc.movePositionX = 10f;
                    sc.speed = 200f;
                })
                .addComponent(AnimationComponent.class, ac -> {
                    ac.animations.put(
                            "RIGHT_RUNNING",
                            new AnimationComponent.AnimationData(
                                    new Animation<>(
                                            .3f,
                                            playerAtlas.findRegions(AssetConstants.RIGHT_RUNNING_REGIONS)
                                    ),
                                    playerAtlas.findRegion(AssetConstants.RIGHT_IDLE_REGION)
                            )
                    );
                    ac.animations.put(
                            "LEFT_RUNNING",
                            new AnimationComponent.AnimationData(
                                    new Animation<>(
                                            .3f,
                                            playerAtlas.findRegions(AssetConstants.LEFT_RUNNING_REGIONS)
                                    ),
                                    playerAtlas.findRegion(AssetConstants.LEFT_IDLE_REGION)
                            )
                    );
                })
                .build();
        stage.addListener(new InputListener() {
            private int keyPressed;

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                StateComponent sc = Mappers.STATE_MAPPER.get(player);
                AnimationComponent ac = Mappers.ANIMATION_MAPPER.get(player);
                if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
                    sc.movePositionX = 0;
                    keyPressed = keycode;
                    ac.start("LEFT_RUNNING", true);
                    return true;
                } else if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
                    sc.movePositionX = 3332;
                    keyPressed = keycode;
                    ac.start("RIGHT_RUNNING", true);
                    return true;
                }
                return false;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == keyPressed) {
                    StateComponent sc = Mappers.STATE_MAPPER.get(player);
                    BoundsComponent bc = Mappers.BOUNDS_MAPPER.get(player);
                    AnimationComponent ac = Mappers.ANIMATION_MAPPER.get(player);
                    sc.movePositionX = bc.position.x;
                    ac.reset();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        engine.update(delta);
        super.render(delta);
    }
}
