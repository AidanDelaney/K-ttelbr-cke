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
}
