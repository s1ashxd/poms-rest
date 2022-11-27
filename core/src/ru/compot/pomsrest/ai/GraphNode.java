package ru.compot.pomsrest.ai;

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

    public void connect(GraphNode node) {
        connections.add(new ConnectionImpl(this, node));
    }

    public void removeConnection(GraphNode node) {
        for (int i = 0; i < connections.size; i++) {
            Connection<GraphNode> connection = connections.get(i);
            if (connection.getToNode() == node) connections.removeValue(connection, true);
        }
    }

    @Override
    public String toString() {
        return "GraphNode{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
