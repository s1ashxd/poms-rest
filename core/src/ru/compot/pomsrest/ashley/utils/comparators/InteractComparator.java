package ru.compot.pomsrest.ashley.utils.comparators;

import com.badlogic.ashley.core.Entity;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;

import java.util.Comparator;

public class InteractComparator implements Comparator<Entity> {

    public static final InteractComparator INSTANCE = new InteractComparator();

    private InteractComparator() {
    }

    @Override
    public int compare(Entity o1, Entity o2) {
        return Integer.compare(
                Mappers.INTERACT_MAPPER.get(o1).collisionPriority,
                Mappers.INTERACT_MAPPER.get(o2).collisionPriority
        );
    }
}
