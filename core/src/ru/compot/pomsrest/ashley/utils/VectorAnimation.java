package ru.compot.pomsrest.ashley.utils;

import com.badlogic.gdx.math.Vector2;

public class VectorAnimation {

    private final Vector2 start, distance, end;
    private final float directDistance;
    private final float speed;

    public VectorAnimation(float speed, float startX, float startY, float destX, float destY) {
        this.speed = speed;
        this.start = new Vector2(startX, startY);
        this.distance = new Vector2(destX - startX, destY - startY);
        this.end = new Vector2(destX, destY);
        float distanceX = destX - startX, distanceY = destY - startY;
        this.directDistance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    public Vector2 getKeyFrame(float estimatedTime) {
        float dist = estimatedTime * speed;
        if (dist >= directDistance) return new Vector2(end);
        float multiplier = dist / directDistance;
        float resultX = start.x + distance.x * multiplier;
        float resultY = start.y + distance.y * multiplier;
        return new Vector2(resultX, resultY);
    }

    public boolean isAnimationFinished(float estimatedTime) {
        float dist = estimatedTime * speed;
        return dist >= directDistance;
    }

    public Vector2 getEnd() {
        return end;
    }
}
