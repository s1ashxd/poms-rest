package ru.compot.pomsrest.utils;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class VectorAnimation {

    private final Vector2 start, distance, end;
    private final Interpolation interpolation;
    private final float speed;
    private float time;

    public VectorAnimation(float speed, float startX, float startY, float destX, float destY, Interpolation interpolation) {
        this.start = new Vector2(startX, startY);
        this.distance = new Vector2(destX - startX, destY - startY);
        this.end = new Vector2(destX, destY);
        this.interpolation = interpolation == null ? Interpolation.linear : interpolation;
        this.speed = speed;
        this.time = (float) (Math.sqrt(distance.x * distance.x + distance.y * distance.y) / speed);
    }

    public Vector2 getKeyFrame(float estimatedTime) {
        if (isAnimationFinished(estimatedTime)) return end;
        float multiplier = interpolation.apply(estimatedTime / time);
        return new Vector2(start.x + distance.x * multiplier, start.y + distance.y * multiplier);
    }

    public boolean isAnimationFinished(float estimatedTime) {
        return estimatedTime >= time;
    }

    public void setEnd(float x, float y, float lastEstimatedTime) {
        start.set(getKeyFrame(lastEstimatedTime));
        end.set(x, y);
        distance.set(x - start.x, y - start.y);
        time = (float) (Math.sqrt(distance.x * distance.x + distance.y * distance.y) / speed);
    }
}
