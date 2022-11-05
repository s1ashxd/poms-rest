package ru.compot.pomsrest.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import ru.compot.pomsrest.ashley.components.InteractComponent;
import ru.compot.pomsrest.ashley.components.PlayerComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.comparators.InteractComparator;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.ashley.utils.constants.Priorities;

public class InteractSystem extends SortedIteratingSystem {

    private final Entity player;

    public InteractSystem(Entity player) {
        super(Family.all(TransformComponent.class, InteractComponent.class).get(), InteractComparator.INSTANCE, Priorities.INTERACT_SYSTEM);
        this.player = player;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player);
        if (playerData.interactRequested)
            playerData.interactRequested = false;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent areaTransform = Mappers.TRANSFORM_MAPPER.get(entity);
        InteractComponent areaCollision = Mappers.INTERACT_MAPPER.get(entity);
        PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player);
        Rectangle areaRectangle = new Rectangle(areaTransform.x, areaTransform.y, areaTransform.width, areaTransform.height);
        if (playerData.interactRequested && areaRectangle.contains(playerData.interactPoint)) {
            areaCollision.interactType.acceptAction(player, entity);
            playerData.interactRequested = false;
        }
    }
}
