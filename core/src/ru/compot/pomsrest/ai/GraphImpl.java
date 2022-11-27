package ru.compot.pomsrest.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class GraphImpl implements IndexedGraph<GraphNode> {

    private final GraphNode[] nodes;
    private final Rectangle[] obstacles;
    private GraphNode start, end;

    public GraphImpl(float corner, float playerWidth, Rectangle[] obstacles) {
        GraphNode[] nodes = new GraphNode[obstacles.length * 4];
        for (int i = 0; i < obstacles.length; i++) {
            Rectangle rect = obstacles[i];
            rect.x -= playerWidth;
            rect.width += playerWidth;
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

    private static GraphNode checkConnections(GraphNode node, GraphNode[] nodes, Rectangle[] obstacles) {
        label:
        for (GraphNode n2 : nodes) {
            for (Rectangle rect : obstacles) {
                if (Intersector.intersectSegmentRectangle(node.getX(), node.getY(), n2.getX(), n2.getY(), rect))
                    continue label;
            }
            node.connect(n2);
            n2.connect(node);
        }
        return node;
    }

    @Override
    public int getIndex(GraphNode node) {
        if (node == start) return 0;
        if (node == end) return nodes.length + 1;
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == node) return i + 1;
        }
        throw new IllegalArgumentException("node not found in this graph");
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

    public GraphNode getEnd() {
        return end;
    }

    public void setPathNodes(float startX, float startY, float endX, float endY) {
        this.start = checkConnections(new GraphNode(startX, startY), nodes, obstacles);
        this.end = checkConnections(new GraphNode(endX, endY), nodes, obstacles);
        checkDirectConnection(obstacles, start, end);
    }

    public void removePathNodes() {
        if (start != null && end != null) {
            for (GraphNode node : nodes) {
                node.removeConnection(start);
                node.removeConnection(end);
            }
        }
    }

    private static void checkDirectConnection(Rectangle[] obstacles, GraphNode start, GraphNode end) {
        for (Rectangle rect : obstacles) {
            if (Intersector.intersectSegmentRectangle(start.getX(), start.getY(), end.getX(), end.getY(), rect))
                return;
        }
        start.connect(end);
        end.connect(start);
    }
}
