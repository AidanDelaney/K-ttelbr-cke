package org.eulerdiagrams.euleR;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import org.eulerdiagrams.AbstractDiagram.AbstractContour;
import org.eulerdiagrams.AbstractDiagram.AbstractZone;
import org.eulerdiagrams.ConcreteDiagram.ConcreteCircle;
import org.eulerdiagrams.ConcreteDiagram.ConcreteDiagram;
import org.eulerdiagrams.utils.Pair;

import math.geom2d.conic.Circle2D;

public class JSONAreaResponse extends JSONResponse {
    public JSONAreaResponse(JSONAreaRequest jar) {

        // Can't be a map as there may be multiple concrete circles per abstract
        List<Pair<AbstractContour, Circle2D>> cs = new Vector<>();
        for(JSONCircle circle: jar.circles) {
            Circle2D c = new Circle2D(circle.x, circle.y, circle.radius);
            cs.add(new Pair<>(new AbstractContour(circle.label), c));
        }

        List<ConcreteCircle> concreteCircles  = new Vector<>();
        Set<Circle2D> allCircles = cs.stream().map(x -> x.cdr).collect(Collectors.toSet());
        for(Pair<AbstractContour, Circle2D> pair: cs) {
            // remove this circle from all
            allCircles.remove(pair.cdr);
            concreteCircles.add(new ConcreteCircle(pair.car, pair.cdr, allCircles));
            // then add it back
            allCircles.add(pair.cdr);
        }

        ConcreteDiagram d = new ConcreteDiagram(cs.stream().map(x -> x.car).collect(Collectors.toList()), concreteCircles);

        circles = jar.circles;
        areas = d.getZoneAreaMap();
        duration = 0;

        Optional<AbstractZone> key = Optional.empty();
        for (AbstractZone z : areas.keySet()) {
            if (Double.isInfinite(areas.get(z))) {
                key = Optional.of(z);
                break;
            }
        }
        if(key.isPresent()) {
            areas.remove(key.get());
        }
    }
}
