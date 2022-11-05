package ru.compot.pomsrest.pathfinder;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

public class Pathfinder {

    private static final Heuristic<GraphNode> HEURISTIC =
            (node, endNode) -> Vector2.dst(node.getX(), node.getY(), endNode.getX(), endNode.getY());

    private Pathfinder() {
    }

    public static Iterator<GraphNode> findPath(float x1, float y1, float x2, float y2, GraphImpl graph) {
        graph.setStart(x1, y1);
        graph.setEnd(x2, y2);
        IndexedAStarPathFinder<GraphNode> pathfinder = new IndexedAStarPathFinder<>(graph);
        GraphPathImpl path = new GraphPathImpl();
        if (pathfinder.searchNodePath(graph.getStart(), graph.getEnd(), HEURISTIC, path))
            return path.iterator();
        return null;
    }
}
