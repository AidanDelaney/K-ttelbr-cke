package org.eulerdiagrams.euleR;

import math.geom2d.conic.Circle2D;

public class JSONCircle {
    public Circle2D circle;
    public String label;

    public JSONCircle(Circle2D circle, String label) {
        this.circle = circle;
        this.label = label;
    }
}