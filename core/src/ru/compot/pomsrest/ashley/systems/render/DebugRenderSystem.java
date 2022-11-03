package ru.compot.pomsrest.ashley.systems.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.ashley.utils.constants.Priorities;

public class DebugRenderSystem extends IteratingSystem {
    private final ShapeRenderer renderer = new ShapeRenderer();

    public DebugRenderSystem() {
        super(Family.all(TransformComponent.class).get(), Priorities.DEBUG_RENDER_SYSTEM);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = Mappers.TRANSFORM_MAPPER.get(entity);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.RED);
        renderer.rect(transform.x, transform.y, transform.width, transform.height);
        renderer.end();
    }
}
