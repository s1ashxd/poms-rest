package ru.compot.pomsrest.ashley.constants;

import com.badlogic.ashley.core.ComponentMapper;
import ru.compot.pomsrest.ashley.components.AnimationComponent;
import ru.compot.pomsrest.ashley.components.BoundsComponent;
import ru.compot.pomsrest.ashley.components.StateComponent;
import ru.compot.pomsrest.ashley.components.TextureComponent;

public class Mappers {

    public static final ComponentMapper<BoundsComponent> BOUNDS_MAPPER = ComponentMapper.getFor(BoundsComponent.class);
    public static final ComponentMapper<TextureComponent> TEXTURE_MAPPER = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper<StateComponent> STATE_MAPPER = ComponentMapper.getFor(StateComponent.class);
    public static final ComponentMapper<AnimationComponent> ANIMATION_MAPPER = ComponentMapper.getFor(AnimationComponent.class);

    private Mappers() {
    }
}
