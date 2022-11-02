package ru.compot.pomsrest.ashley.constants;

public enum MoveDirection {

    LEFT(-1), RIGHT(1);

    private final float multiplier;

    MoveDirection(float multiplier) {
        this.multiplier = multiplier;
    }

    public float getMultiplier() {
        return multiplier;
    }
}
