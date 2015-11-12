package org.eulerdiagrams.euleR;

import java.awt.Point;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.eulerdiagrams.AbstractDiagram.AbstractDiagram;
import org.eulerdiagrams.vennom.graph.Graph;
import org.eulerdiagrams.vennom.graph.Node;

import com.google.common.base.Stopwatch;

import math.geom2d.conic.Circle2D;

public class KöttelbrückeService {
    private List<JSONCircle> circles;
    private EulerDrawer ed;
    private Graph graph;
    private Stopwatch timer;

    public KöttelbrückeService(JSONArea areaSpec) {
        ed = new EulerDrawer(areaSpec);
        timer = Stopwatch.createStarted();
        graph = ed.layout();
        timer.stop();
        circles = graphToConcreteCircles(graph);
    }

    public List<JSONCircle> getCircles() {
        return circles;
    }

    public AbstractDiagram getDiagram() {
        return ed.getDiagram();
    }

    public long getDuration() {
        return timer.elapsed(TimeUnit.MILLISECONDS);
    }

    public Graph getGraph() {
        return graph;
    }

    private List<JSONCircle> graphToConcreteCircles(Graph graph) {
        List<JSONCircle> circles = new Vector<>();

        for(Node n: graph.getNodes()) {
            Point centre = n.getCentre();
            circles.add(new JSONCircle(new Circle2D(centre.getX(), centre.getY(), n.getPreciseRadius()), n.getContour()));
        }
        return circles;
    }
}