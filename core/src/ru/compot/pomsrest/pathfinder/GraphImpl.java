package ru.compot.pomsrest.pathfinder;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class GraphImpl implements IndexedGraph<GraphNode> {

    private final GraphNode[] nodes;
    private final Rectangle[] obstacles;
    private GraphNode start, end;

    public GraphImpl(float corner, Rectangle[] obstacles) {
        GraphNode[] nodes = new GraphNode[obstacles.length * 4];
        for (int i = 0; i < obstacles.length; i++) {
            Rectangle rect = obstacles[i];
            nodes[4 * i] = new GraphNode(rect.x - corner, rect.y - corner);
            nodes[4 * i + 1] = new GraphNode(rect.x - corner, rect.y + rect.height + corner);
            nodes[4 * i + 2] = new GraphNode(rect.x + rect.width + corner, rect.y + rect.height + corner);
            nodes[4 * i + 3] = new GraphNode(rect.x + rect.width + corner, rect.y - corner);
        }
        for (GraphNode node : nodes)
            checkConnections(node, nodes, obstacles);
        this.nodes = nodes;
        this.obstacles = obstacles;
    }

    private static void checkConnections(GraphNode node, GraphNode[] nodes, Rectangle[] obstacles) {
        label:
        for (GraphNode n2 : nodes) {
            for (Rectangle rect : obstacles) {
                if (Intersector.intersectSegmentRectangle(node.getX(), node.getY(), n2.getX(), n2.getY(), rect))
                    continue label;
            }
            node.connect(n2);
            n2.connect(node);
        }
    }

    @Override
    public int getIndex(GraphNode node) {
        if (node == start) return 0;
        if (node == end) return nodes.length + 1;
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == node) return i + 1;
        }
        return -1;
    }

    @Override
    public int getNodeCount() {
        return nodes.length + 2;
    }

    @Override
    public Array<Connection<GraphNode>> getConnections(GraphNode fromNode) {
        return fromNode.getConnections();
    }

    public GraphNode getStart() {
        return start;
    }

    public void setStart(float x, float y) {
        GraphNode start = new GraphNode(x, y);
        checkConnections(start, nodes, obstacles);
        this.start = start;
    }

    public GraphNode getEnd() {
        return end;
    }

    public void setEnd(float x, float y) {
        GraphNode end = new GraphNode(x, y);
        checkConnections(end, nodes, obstacles);
        this.end = end;
    }
}
