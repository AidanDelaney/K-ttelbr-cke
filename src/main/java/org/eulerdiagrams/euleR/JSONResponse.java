package org.eulerdiagrams.euleR;

import java.util.List;
import java.util.Map;

import org.eulerdiagrams.AbstractDiagram.AbstractZone;
import org.eulerdiagrams.AbstractDiagram.VennSetIterator;
import org.eulerdiagrams.ConcreteDiagram.ConcreteDiagram;

import math.geom2d.conic.Circle2D;

public class JSONResponse {
    public List<Circle2D> circles;
    public Map<AbstractZone, Double> areas;

    public JSONResponse(KöttelbrückeService kbs) {
        this.circles = kbs.getCircles();

        VennSetIterator vsi = new VennSetIterator(kbs.getDiagram().getContours());
        ConcreteDiagram cd = new ConcreteDiagram(kbs.getGraph(), kbs.getDiagram());
        areas = cd.getZoneAreaMap();

        for(AbstractZone z: vsi) {
            if(!areas.containsKey(z)) {
                areas.put(z, 0.0);
            } else if(Double.isInfinite(areas.get(z))) {
                areas.remove(z);
            }
        }
    }
}
