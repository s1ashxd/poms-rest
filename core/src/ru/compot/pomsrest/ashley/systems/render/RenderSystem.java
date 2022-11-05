package ru.compot.pomsrest.ashley.systems.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.compot.pomsrest.ashley.components.texture.TextureComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.comparators.LayerComparator;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.ashley.utils.constants.Priorities;

public class RenderSystem extends SortedIteratingSystem {

    private final SpriteBatch batch;

    public RenderSystem(SpriteBatch batch) {
        super(Family.all(TransformComponent.class, TextureComponent.class).get(), LayerComparator.INSTANCE, Priorities.RENDER_SYSTEM);
        this.batch = batch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = Mappers.TRANSFORM_MAPPER.get(entity);
        TextureComponent texture = Mappers.TEXTURE_MAPPER.get(entity);
        float width = texture.width > 0 ? texture.width : texture.region.getRegionWidth();
        float height = texture.height > 0 ? texture.height : texture.region.getRegionHeight();
        batch.begin();
        batch.draw(
                texture.region,
                transform.x, transform.y,
                transform.originX, transform.originY,
                width, height,
                transform.scaleX, transform.scaleY,
                0);
        batch.end();
    }
}
