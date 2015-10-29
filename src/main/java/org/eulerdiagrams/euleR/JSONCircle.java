package org.eulerdiagrams.euleR;

import math.geom2d.conic.Circle2D;

public class JSONCircle {
    public double x, y, radius;
    public String label;

    public JSONCircle(Circle2D circle, String label) {
        this.x = circle.center().x();
        this.y = circle.center().y();
        this.radius = circle.radius();
        this.label = label;
    }
}