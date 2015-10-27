package org.eulerdiagrams.euleR;

import java.util.List;

import math.geom2d.conic.Circle2D;

public class JSONResponse {
    public List<Circle2D> circles;
    public JSONResponse(List<Circle2D> cs) {
        this.circles = cs;
    }
}
