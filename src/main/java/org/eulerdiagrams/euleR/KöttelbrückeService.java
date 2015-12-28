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
    static {
        /*
         * The lines until the <<< marker are junk that is necessary to set before you can use the VenNom graph drawer.
         * It's not pretty, but without setting properties of the EdgeTypes you get NullPointerExceptions from the
         * VenNom code.
         */
        EdgeType FIXED = new EdgeType("fixed");
        FIXED.setLineColor(Color.black);
        FIXED.setTextColor(Color.black);
        FIXED.setSelectedLineColor(Color.gray);
        FIXED.setPriority(1020);

        EdgeType ATTRACTOR = new EdgeType("attractor");
        ATTRACTOR.setLineColor(Color.green);
        ATTRACTOR.setTextColor(Color.green);
        ATTRACTOR.setSelectedLineColor(Color.gray);
        ATTRACTOR.setPriority(1019);

        EdgeType REPULSOR = new EdgeType("repulsor");
        REPULSOR.setLineColor(Color.red);
        REPULSOR.setTextColor(Color.red);
        REPULSOR.setSelectedLineColor(Color.gray);
        REPULSOR.setPriority(1018);

        EdgeType SEPARATOR = new EdgeType("separator");
        SEPARATOR.setLineColor(Color.cyan);
        SEPARATOR.setTextColor(Color.cyan);
        SEPARATOR.setSelectedLineColor(Color.gray);
        SEPARATOR.setPriority(1017);

        EdgeType IDEAL = new EdgeType("ideal");
        IDEAL.setLineColor(Color.blue);
        IDEAL.setTextColor(Color.blue);
        IDEAL.setSelectedLineColor(Color.gray);
        IDEAL.setPriority(1016);

        EdgeType CONTAINMENT = new EdgeType("containment");
        CONTAINMENT.setLineColor(Color.magenta);
        CONTAINMENT.setTextColor(Color.magenta);
        CONTAINMENT.setSelectedLineColor(Color.magenta);
        CONTAINMENT.setPriority(1016);

        Graph.DEFAULT_NODE_TYPE.setHeight(20);
        Graph.DEFAULT_NODE_TYPE.setWidth(20);
        Graph.DEFAULT_NODE_TYPE.setBorderColor(Color.WHITE);
        Graph.DEFAULT_NODE_TYPE.setTextColor(Color.BLUE);
        Graph.DEFAULT_EDGE_TYPE = IDEAL;
        // <<<
    }
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