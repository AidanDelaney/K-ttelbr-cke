package org.eulerdiagrams.euleR;

import java.awt.*;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.eulerdiagrams.AbstractDiagram.AbstractDiagram;
import org.eulerdiagrams.vennom.apCircles.VennomLayout;
import org.eulerdiagrams.vennom.graph.EdgeType;
import org.eulerdiagrams.vennom.graph.Graph;
import org.eulerdiagrams.vennom.graph.Node;

//import static org.eulerdiagrams.vennom.apCircles.display.APCircleDisplay.*;

import com.google.common.base.Stopwatch;

import math.geom2d.conic.Circle2D;

public class KöttelbrückeService {
   
    private List<JSONCircle> circles;
    private Graph graph;
    private Stopwatch timer;
    private AbstractDiagramProvider adp;

    public KöttelbrückeService(JSONArea areaSpec) {
        adp = new AbstractDiagramProvider(areaSpec);
        VennomLayout vl = new VennomLayout(VennomLayout.FORCE_LAYOUT, adp.asAreaSpecification());
        timer = Stopwatch.createStarted();
        graph = vl.layout();
        timer.stop();
        circles = graphToConcreteCircles(graph);
    }

    public List<JSONCircle> getCircles() {
        return circles;
    }

    public AbstractDiagram getDiagram() {
        return adp.getDiagram();
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
            Point.Double centre = n.getPreciseCentre();
            circles.add(new JSONCircle(new Circle2D(centre.getX(), centre.getY(), n.getPreciseRadius()), n.getContour()));
        }
        return circles;
    }
}