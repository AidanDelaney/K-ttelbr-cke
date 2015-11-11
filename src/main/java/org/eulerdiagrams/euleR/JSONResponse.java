package org.eulerdiagrams.euleR;

import java.util.List;
import java.util.Map;

import org.eulerdiagrams.AbstractDiagram.AbstractZone;
import org.eulerdiagrams.AbstractDiagram.VennSetIterator;
import org.eulerdiagrams.ConcreteDiagram.ConcreteDiagram;

public class JSONResponse {
    public List<JSONCircle> circles;
    public Map<AbstractZone, Double> areas;
    public long duration;

    public JSONResponse(KöttelbrückeService kbs) {
        this.circles = kbs.getCircles();
        this.duration = kbs.getDuration();

        VennSetIterator vsi = new VennSetIterator(kbs.getDiagram().getContours());
        ConcreteDiagram cd = new ConcreteDiagram(kbs.getGraph(), kbs.getDiagram());
        areas = cd.getZoneAreaMap();

        for(AbstractZone z: vsi) {
            if(Double.isInfinite(areas.get(z))) {
                areas.remove(z);
            }
        }
    }
}
