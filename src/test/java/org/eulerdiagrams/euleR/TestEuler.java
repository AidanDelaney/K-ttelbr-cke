package org.eulerdiagrams.euleR;

import edu.uic.ncdm.venn.VennDiagram;
import edu.uic.ncdm.venn.data.VennData;

import org.eulerdiagrams.AbstractDiagram.*;
import org.eulerdiagrams.vennom.apCircles.AreaSpecification;
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
        VennDiagram vd = ed.layout();

        assertThat(vd, is(not(nullValue())));
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

        EulerDrawer ed = new EulerDrawer(areas);
        VennDiagram vd = ed.layout();

        assertThat(vd, is(not(nullValue())));
    }

    @Test
    public void testVenn3() {
        Set<AbstractContour> contours = new HashSet<AbstractContour>();
        AbstractContour a = new AbstractContour("a");
        AbstractContour b = new AbstractContour("b");
        AbstractContour c = new AbstractContour("c");
        contours.add(a);
        contours.add(b);
        contours.add(c);

        WeightedAbstractDiagram d = new WeightedAbstractDiagram(contours);
        d.addZone(100.0, a);
        d.addZone(100.0, b);
        d.addZone(40.0, a, b);
        d.addZone(40.0, a, c);
        d.addZone(40.0, b, c);
        d.addZone(10.0, a, b, c);

        EulerDrawer ed = new EulerDrawer(d);
        VennDiagram vd = ed.layout();

        assertThat(vd, is(not(nullValue())));
    }
}
