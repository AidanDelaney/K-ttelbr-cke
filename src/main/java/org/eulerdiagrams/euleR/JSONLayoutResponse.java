package org.eulerdiagrams.euleR;

import org.eulerdiagrams.AbstractDiagram.AbstractZone;
import org.eulerdiagrams.AbstractDiagram.VennSetIterator;
import org.eulerdiagrams.ConcreteDiagram.ConcreteDiagram;

public class JSONLayoutResponse extends JSONResponse {
    public JSONLayoutResponse(KöttelbrückeService kbs) {
        circles = kbs.getCircles();
        duration = kbs.getDuration();

        VennSetIterator vsi = new VennSetIterator(
                kbs.getDiagram().getContours());
        ConcreteDiagram cd = new ConcreteDiagram(kbs.getGraph(),
                kbs.getDiagram());
        areas = cd.getZoneAreaMap();

        for (AbstractZone z : vsi) {
            if (Double.isInfinite(areas.get(z))) {
                areas.remove(z);
            }
        }
    }
}