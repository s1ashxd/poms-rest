package ru.compot.pomsrest.ashley.constants;

public enum EntityState {

    WALK_BLOCKED(1);

    private final int id;

    EntityState(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
