package ru.compot.pomsrest.screens;

import com.badlogic.ashley.core.PooledEngine;
import ru.compot.pomsrest.ashley.systems.animations.TextureAnimationSystem;
import ru.compot.pomsrest.ashley.systems.animations.TransformAnimationSystem;
import ru.compot.pomsrest.ashley.systems.render.DebugRenderSystem;
import ru.compot.pomsrest.ashley.systems.render.RenderSystem;

public abstract class GameScreen extends StageScreen {

    protected final PooledEngine engine = new PooledEngine();

    protected GameScreen() {
        engine.addSystem(new DebugRenderSystem(batch));
        engine.addSystem(new RenderSystem(batch));
        engine.addSystem(new TextureAnimationSystem());
        engine.addSystem(new TransformAnimationSystem());
    }

    @Override
    public void render(float delta) {
        engine.update(delta);
        super.render(delta);
    }

    @Override
    public void hide() {
        engine.clearPools();
        super.hide();
    }
}
