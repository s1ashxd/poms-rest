package ru.compot.pomsrest.ashley.utils.comparators;

import com.badlogic.ashley.core.Entity;
import ru.compot.pomsrest.ashley.constants.Mappers;

import java.util.Comparator;

public class CollisionComparator implements Comparator<Entity> {

    public static final CollisionComparator INSTANCE = new CollisionComparator();

    private CollisionComparator() {
    }

    @Override
    public int compare(Entity o1, Entity o2) {
        return Integer.compare(
                Mappers.COLLISION_MAPPER.get(o1).collisionPriority,
                Mappers.COLLISION_MAPPER.get(o2).collisionPriority
        );
    }
}
