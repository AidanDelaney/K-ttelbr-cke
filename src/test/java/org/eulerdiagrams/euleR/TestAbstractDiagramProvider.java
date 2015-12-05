package org.eulerdiagrams.euleR;

import edu.uic.ncdm.venn.data.VennData;

import org.eulerdiagrams.vennom.apCircles.AreaSpecification;
import org.junit.*;
import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;

import java.util.*;

public class TestAbstractDiagramProvider {
    @Test
    public void testCreation() {
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
        AbstractDiagramProvider adp  = new AbstractDiagramProvider(areas);
        AreaSpecification as = adp.asAreaSpecification();

        assertThat(as.getZoneArea("A"), is(closeTo(20.0, 1.0)));
    }

    @Test
    public void testNonLexiographcCreation() {
        String[][] zones = new String[3][2];
        zones[0][0] = "A";
        zones[1][0] = "B";
        zones[2][0] = "B&A&C";

        double [] weights = new double[3];
        weights[0] = 20.0;
        weights[1] = 20.0;
        weights[2] = 10.0;

        VennData data = new VennData(zones, weights, true);
        //AbstractDiagramProvider adp  = new AbstractDiagramProvider(data);
        //AreaSpecification as = adp.asAreaSpecification();

        //assertThat(as.getZoneArea("abc"), is(closeTo(10.0, 1.0)));
    }
}