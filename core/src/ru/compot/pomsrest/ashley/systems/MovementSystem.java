package ru.compot.pomsrest.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ru.compot.pomsrest.ashley.components.BoundsComponent;
import ru.compot.pomsrest.ashley.components.StateComponent;
import ru.compot.pomsrest.ashley.constants.Mappers;
import ru.compot.pomsrest.ashley.constants.Priorities;

public class MovementSystem extends IteratingSystem {

    public MovementSystem() {
        super(Family.all(BoundsComponent.class, StateComponent.class).get(), Priorities.MOVEMENT_SYSTEM);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent bc = Mappers.BOUNDS_MAPPER.get(entity);
        StateComponent sc = Mappers.STATE_MAPPER.get(entity);
        if (sc.movePositionX == bc.position.x) return;
        float direction = Math.signum(sc.movePositionX - bc.position.x);
        bc.position.x += deltaTime * direction * sc.speed;
        if ((direction > 0 && bc.position.x > sc.movePositionX)
                || (direction < 0 && bc.position.x < sc.movePositionX))
            bc.position.x = sc.movePositionX;
    }
}
