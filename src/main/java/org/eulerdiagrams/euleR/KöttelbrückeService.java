package org.eulerdiagrams.euleR;

import java.util.List;

import edu.uic.ncdm.venn.VennDiagram;

import math.geom2d.conic.Circle2D;

public class KöttelbrückeService {
    private List<Circle2D> d;

    public KöttelbrückeService(JSONArea areaSpec) {
        EulerDrawer ed = new EulerDrawer(areaSpec);
        d = ed.layout();
    }

    public List<Circle2D> getDiagram() {
        return d;
    }
}