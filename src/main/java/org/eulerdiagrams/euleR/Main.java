package org.eulerdiagrams.euleR;

import static spark.Spark.*;

import com.google.gson.Gson;

import math.geom2d.conic.Circle2D;

public class Main {
    private static final String IP_ADDRESS = System.getenv("OPENSHIFT_DIY_IP") != null ? System.getenv("OPENSHIFT_DIY_IP") : "0.0.0.0";
    private static final int PORT = System.getenv("OPENSHIFT_DIY_PORT") != null ? Integer.parseInt(System.getenv("OPENSHIFT_DIY_PORT")) : 8080;

    public static void main(String[] args) {
        ipAddress(IP_ADDRESS);
        port(PORT);
        staticFileLocation("/public");
        Gson gson = new Gson();
        post("/", (req, res) -> {
            System.err.println(req.body());
            JSONArea pojo = gson.fromJson(req.body(), JSONArea.class);
            KöttelbrückeService kbs = new KöttelbrückeService(pojo);
            return gson.toJson(kbs.getDiagram());
        });
    }
}