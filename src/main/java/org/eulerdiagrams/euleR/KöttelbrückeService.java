package org.eulerdiagrams.euleR;

import java.awt.Point;
import java.util.List;
import java.util.Vector;

import org.eulerdiagrams.AbstractDiagram.AbstractDiagram;
import org.eulerdiagrams.vennom.graph.Graph;
import org.eulerdiagrams.vennom.graph.Node;

import edu.uic.ncdm.venn.VennDiagram;

import math.geom2d.conic.Circle2D;

public class KöttelbrückeService {
    private List<Circle2D> circles;
    private EulerDrawer ed;
    private Graph graph;

    public KöttelbrückeService(JSONArea areaSpec) {
        ed = new EulerDrawer(areaSpec);
        graph = ed.layout();
        circles = graphToConcreteCircles(graph);
    }

    public List<Circle2D> getCircles() {
        return circles;
    }

    public AbstractDiagram getDiagram() {
        return ed.getDiagram();
    }

    public Graph getGraph() {
        return graph;
    }

    private List<Circle2D> graphToConcreteCircles(Graph graph) {
        List<Circle2D> circles = new Vector<>();

        for(Node n: graph.getNodes()) {
            Point centre = n.getCentre();
            circles.add(new Circle2D(centre.getX(), centre.getY(), n.getPreciseRadius()));
        }
        return circles;
    }
}