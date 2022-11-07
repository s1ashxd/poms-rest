package ru.compot.pomsrest.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import ru.compot.pomsrest.ashley.utils.constants.enums.InteractType;

public class InteractComponent implements Component, Pool.Poolable {

    public InteractType interactType = InteractType.WORLD_ENTER_AREA;
    public int interactPriority;

    @Override
    public void reset() {
    }
}
