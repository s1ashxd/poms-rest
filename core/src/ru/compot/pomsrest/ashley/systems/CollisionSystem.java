package ru.compot.pomsrest.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import ru.compot.pomsrest.ashley.components.CollisionComponent;
import ru.compot.pomsrest.ashley.components.PlayerComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.ashley.utils.constants.Priorities;
import ru.compot.pomsrest.ashley.utils.comparators.CollisionComparator;

public class CollisionSystem extends SortedIteratingSystem {

    private final Entity player;

    public CollisionSystem(Entity player) {
        super(Family.all(TransformComponent.class, CollisionComponent.class).get(), CollisionComparator.INSTANCE, Priorities.COLLISION_SYSTEM);
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
        CollisionComponent areaCollision = Mappers.COLLISION_MAPPER.get(entity);
        TransformComponent playerTransform = Mappers.TRANSFORM_MAPPER.get(player);
        PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player);
        Rectangle playerRectangle = new Rectangle(playerTransform.x, playerTransform.y, playerTransform.width, playerTransform.height);
        Rectangle areaRectangle = new Rectangle(areaTransform.x, areaTransform.y, areaTransform.width, areaTransform.height);
        if (playerRectangle.overlaps(areaRectangle) && playerData.interactRequested) {
            Gdx.app.log("DEBUG", "Requested interaction with entity: " + areaCollision.collisionType);
            areaCollision.collisionType.acceptAction(player, entity);
            playerData.interactRequested = false;
        }
    }
}
