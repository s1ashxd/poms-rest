package ru.compot.pomsrest.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import ru.compot.pomsrest.ashley.constants.enums.CollisionType;

public class CollisionComponent implements Component, Pool.Poolable {

    public CollisionType collisionType = CollisionType.INTERACT_AREA;
    public Runnable onInteract;
    public int collisionPriority;

    @Override
    public void reset() {
    }
}
