package ru.compot.pomsrest.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.Vector2;

public class ConnectionImpl implements Connection<GraphNode> {

    private final GraphNode start, end;
    private final float cost;

    public ConnectionImpl(GraphNode start, GraphNode end) {
        this.start = start;
        this.end = end;
        this.cost = Vector2.dst(start.getX(), start.getY(), end.getX(), end.getY());
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public GraphNode getFromNode() {
        return start;
    }

    @Override
    public GraphNode getToNode() {
        return end;
    }
}
