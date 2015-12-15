package org.eulerdiagrams.euleR;

import org.eulerdiagrams.AbstractDiagram.*;
import org.eulerdiagrams.vennom.graph.Graph;
import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.*;

public class TestEuler {
    @Test
    public void simpleTest() {
        Set<AbstractContour> contours = new HashSet<AbstractContour>();
        AbstractContour a = new AbstractContour("a");
        AbstractContour b = new AbstractContour("b");
        contours.add(a);
        contours.add(b);

        WeightedAbstractDiagram d = new WeightedAbstractDiagram(contours);
        d.addZone(100.0, a);
        d.addZone(100.0, b);
        d.addZone(50.0, a, b);

        EulerDrawer ed = new EulerDrawer(d);
        Graph g = ed.layout();

        assertThat(g, is(not(nullValue())));
    }

    @Test
    public void testVenn2() {
        JSONArea areas = new JSONArea();

        Map<String, Double> a = new HashMap<>();
        a.put("A", 20.0);
        Map<String, Double> b = new HashMap<>();
        b.put("B", 20.0);
        Map<String, Double> ab = new HashMap<>();
        ab.put("A&B", 10.0);

        areas.area_specifications = new Vector<>();
        areas.area_specifications.add(a);
        areas.area_specifications.add(b);
        areas.area_specifications.add(ab);

        KöttelbrückeService ed = new KöttelbrückeService(areas);
        Graph g = ed.getGraph();

        assertThat(g, is(not(nullValue())));
    }
}
