package ru.compot.pomsrest.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import ru.compot.pomsrest.ashley.components.BoundsComponent;
import ru.compot.pomsrest.ashley.constants.Mappers;
import ru.compot.pomsrest.ashley.constants.Priorities;

public class DebugRenderSystem extends IteratingSystem {
    private final ShapeRenderer renderer = new ShapeRenderer();

    public DebugRenderSystem() {
        super(Family.all(BoundsComponent.class).get(), Priorities.DEBUG_RENDER_SYSTEM);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent bc = Mappers.BOUNDS_MAPPER.get(entity);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.RED);
        renderer.rect(bc.position.x, bc.position.y, bc.width, bc.height);
        renderer.end();
    }
}
