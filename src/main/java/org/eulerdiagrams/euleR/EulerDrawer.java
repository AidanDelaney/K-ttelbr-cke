package org.eulerdiagrams.euleR;

import org.eulerdiagrams.AbstractDiagram.WeightedAbstractDiagram;
import org.eulerdiagrams.ConcreteDiagram.ConcreteCircle;
import org.eulerdiagrams.vennom.apCircles.drawers.GeneralAPForceModel;
import org.eulerdiagrams.vennom.graph.*;
import org.eulerdiagrams.vennom.apCircles.*;

import static org.eulerdiagrams.vennom.apCircles.display.APCircleDisplay.*;
import edu.uic.ncdm.venn.VennDiagram;
import edu.uic.ncdm.venn.data.VennData;
import math.geom2d.conic.Circle2D;

import org.eulerdiagrams.vennom.graph.drawers.GraphDrawer;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

class EulerDrawer {
    static {
        /*
         * The lines until the <<< marker are junk that is necessary to set before you can use the VenNom graph drawer.
         * It's not pretty, but without setting properties of the EdgeTypes you get NullPointerExceptions from the
         * VenNom code.
         */
        FIXED = new EdgeType("fixed");
        FIXED.setLineColor(Color.black);
        FIXED.setTextColor(Color.black);
        FIXED.setSelectedLineColor(Color.gray);
        FIXED.setPriority(1020);

        ATTRACTOR = new EdgeType("attractor");
        ATTRACTOR.setLineColor(Color.green);
        ATTRACTOR.setTextColor(Color.green);
        ATTRACTOR.setSelectedLineColor(Color.gray);
        ATTRACTOR.setPriority(1019);

        REPULSOR = new EdgeType("repulsor");
        REPULSOR.setLineColor(Color.red);
        REPULSOR.setTextColor(Color.red);
        REPULSOR.setSelectedLineColor(Color.gray);
        REPULSOR.setPriority(1018);

        IDEAL = new EdgeType("ideal");
        IDEAL.setLineColor(Color.blue);
        IDEAL.setTextColor(Color.blue);
        IDEAL.setSelectedLineColor(Color.gray);
        IDEAL.setPriority(1016);

        CONTAINMENT = new EdgeType("containment");
        CONTAINMENT.setLineColor(Color.magenta);
        CONTAINMENT.setTextColor(Color.magenta);
        CONTAINMENT.setSelectedLineColor(Color.magenta);
        CONTAINMENT.setPriority(1016);

        Graph.DEFAULT_NODE_TYPE.setHeight(20);
        Graph.DEFAULT_NODE_TYPE.setWidth(20);
        Graph.DEFAULT_NODE_TYPE.setBorderColor(Color.WHITE);
        Graph.DEFAULT_NODE_TYPE.setTextColor(Color.BLUE);
        Graph.DEFAULT_EDGE_TYPE = FIXED;
        // <<<
    }

    private AbstractDiagramProvider adp;

    public EulerDrawer(WeightedAbstractDiagram diagram) {
        adp = new AbstractDiagramProvider(diagram);
    }

    public EulerDrawer(JSONArea diagram) {
        adp = new AbstractDiagramProvider(diagram);
    }

    public org.eulerdiagrams.AbstractDiagram.AbstractDiagram getDiagram() {
        return adp.getDiagram();
    }

    public Graph layout() {
        AreaSpecification as = adp.asAreaSpecification();
        JFrame frame = new JFrame();
        GraphDrawer gd = new GeneralAPForceModel();
        APCirclePanel apc = new APCirclePanel(frame);
        apc.setSpecification(as);
        apc.addGraphDrawer(gd);

        apc.setGraph(as.generateGeneralAugmentedIntersectionGraph());
        gd.layout();

        return gd.getGraph();
    }
}
