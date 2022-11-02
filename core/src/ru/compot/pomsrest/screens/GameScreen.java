package ru.compot.pomsrest.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import ru.compot.pomsrest.GameCore;
import ru.compot.pomsrest.ashley.components.BoundsComponent;
import ru.compot.pomsrest.ashley.components.StateComponent;
import ru.compot.pomsrest.ashley.constants.EntityState;
import ru.compot.pomsrest.ashley.constants.Mappers;
import ru.compot.pomsrest.ashley.constants.MoveDirection;
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
        EntityBuilder.create(engine)
                .setSize(WORLD_WIDTH, GROUND_HEIGHT)
                .setTexture(GameCore.getAsset(AssetConstants.BACKGROUND))
                .build();
        Entity player = EntityBuilder.create(engine)
                .setPosition(10f, GROUND_HEIGHT)
                .setSize(70f, 70f)
                .setTextureRegion(GameCore.<TextureAtlas>getAsset(AssetConstants.PLAYER_LLAMA).findRegion("right-running"))
                .setTextureSize(70f, 70f)
                .addComponent(StateComponent.class, sc -> {
                    sc.movePositionX = 10f;
                    sc.speed = 200f;
                })
                .build();
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                StateComponent sc = Mappers.STATE_MAPPER.get(player);
                if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
                    sc.movePositionX = 0;
                    return true;
                } else if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
                    sc.movePositionX = 3332;
                    return true;
                }
                return false;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                StateComponent sc = Mappers.STATE_MAPPER.get(player);
                BoundsComponent bc = Mappers.BOUNDS_MAPPER.get(player);
                sc.movePositionX = bc.position.x;
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
