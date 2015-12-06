package org.eulerdiagrams.euleR;

import org.eulerdiagrams.ConcreteDiagram.ConcreteDiagram;

public class JSONLayoutResponse extends JSONResponse {
    public JSONLayoutResponse(KöttelbrückeService kbs) {
        circles = kbs.getCircles();
        duration = kbs.getDuration();

        ConcreteDiagram cd = new ConcreteDiagram(kbs.getGraph(),
                kbs.getDiagram());
        areas = cd.getZoneAreaMap();
    }
}