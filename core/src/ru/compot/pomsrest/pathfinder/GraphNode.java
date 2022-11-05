package ru.compot.pomsrest.pathfinder;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

public class GraphNode {

    private final float x, y;
    private final Array<Connection<GraphNode>> connections = new Array<>();

    public GraphNode(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Array<Connection<GraphNode>> getConnections() {
        return connections;
    }

    public void connect(GraphNode vertex) {
        connections.add(new ConnectionImpl(this, vertex));
    }

    @Override
    public String toString() {
        return "x= " + x + "; y=" + y + "\n";
    }
}
