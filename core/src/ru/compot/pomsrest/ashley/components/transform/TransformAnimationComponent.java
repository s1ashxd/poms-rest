package ru.compot.pomsrest.ashley.components.transform;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import ru.compot.pomsrest.ashley.utils.TransformAnimationData;
import ru.compot.pomsrest.ashley.utils.constants.enums.TransformAnimationType;
import ru.compot.pomsrest.utils.Vector2Animation;

import java.util.concurrent.CopyOnWriteArrayList;

public class TransformAnimationComponent implements Component, Pool.Poolable {

    public final CopyOnWriteArrayList<TransformAnimationData> animations = new CopyOnWriteArrayList<>();

    @Override
    public void reset() {
        animations.clear();
    }

    public void animate(TransformAnimationType type, float speed, float startX, float startY, float destX, float destY, Runnable onFinish) {
        stopAnimations(type);
        animations.add(new TransformAnimationData(new Vector2Animation(speed, startX, startY, destX, destY, null), type, onFinish));
    }

    @SuppressWarnings("all")
    public void stopAnimations(TransformAnimationType type) {
        for (TransformAnimationData tad : animations) {
            if (tad.type == type) animations.remove(tad);
        }
    }
}
