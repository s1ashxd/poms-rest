package ru.compot.pomsrest.ashley.utils;

import ru.compot.pomsrest.ashley.utils.constants.enums.TransformAnimationType;

public class TransformAnimationData {

    public final VectorAnimation animation;
    public final TransformAnimationType type;
    public final Runnable onFinish;
    public float estimatedTime;

    public TransformAnimationData(VectorAnimation animation, TransformAnimationType type, Runnable onFinish) {
        this.animation = animation;
        this.type = type;
        this.onFinish = onFinish;
    }
}
