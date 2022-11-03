package ru.compot.pomsrest.ashley.constants;

import com.badlogic.ashley.core.ComponentMapper;
import ru.compot.pomsrest.ashley.components.CollisionComponent;
import ru.compot.pomsrest.ashley.components.PlayerComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureAnimationComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;

public class Mappers {

    public static final ComponentMapper<TransformComponent> TRANSFORM_MAPPER = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<TextureComponent> TEXTURE_MAPPER = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper<TextureAnimationComponent> TEXTURE_ANIMATION_MAPPER = ComponentMapper.getFor(TextureAnimationComponent.class);
    public static final ComponentMapper<TransformAnimationComponent> TRANSFORM_ANIMATION_MAPPER = ComponentMapper.getFor(TransformAnimationComponent.class);
    public static final ComponentMapper<CollisionComponent> COLLISION_MAPPER = ComponentMapper.getFor(CollisionComponent.class);
    public static final ComponentMapper<PlayerComponent> PLAYER_MAPPER = ComponentMapper.getFor(PlayerComponent.class);

    private Mappers() {
    }
}
