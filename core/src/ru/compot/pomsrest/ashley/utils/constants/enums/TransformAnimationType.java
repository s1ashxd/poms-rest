package ru.compot.pomsrest.ashley.utils.constants.enums;

import com.badlogic.gdx.math.Vector2;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;

import java.util.function.BiConsumer;

public enum TransformAnimationType {

    POSITION((transform, state) -> {
        transform.x = state.x;
        transform.y = state.y;
    }),
    SIZE((transform, state) -> {
        transform.width = state.x;
        transform.height = state.y;
    }),
    SCALE((transform, state) -> {
        transform.scaleX = state.x;
        transform.scaleY = state.y;
    });

    private final BiConsumer<TransformComponent, Vector2> action;

    TransformAnimationType(BiConsumer<TransformComponent, Vector2> action) {
        this.action = action;
    }

    public void acceptAction(TransformComponent transform, Vector2 state) {
        action.accept(transform, state);
    }
}
