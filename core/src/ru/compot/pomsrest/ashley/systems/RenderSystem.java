package ru.compot.pomsrest.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import ru.compot.pomsrest.ashley.components.BoundsComponent;
import ru.compot.pomsrest.ashley.components.TextureComponent;
import ru.compot.pomsrest.ashley.constants.Mappers;
import ru.compot.pomsrest.ashley.constants.Priorities;
import ru.compot.pomsrest.ashley.utils.LayerComparator;

public class RenderSystem extends SortedIteratingSystem {

    private final SpriteBatch batch = new SpriteBatch();

    public RenderSystem() {
        super(Family.all(BoundsComponent.class, TextureComponent.class).get(), LayerComparator.INSTANCE, Priorities.RENDER_SYSTEM);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent bc = Mappers.BOUNDS_MAPPER.get(entity);
        TextureComponent tc = Mappers.TEXTURE_MAPPER.get(entity);
        float width = tc.width > 0 ? tc.width : tc.region.getRegionWidth();
        float height = tc.height > 0 ? tc.height : tc.region.getRegionHeight();
        batch.begin();
        batch.draw(tc.region, bc.position.x, bc.position.y, width, height);
        batch.end();
    }
}
