package org.eulerdiagrams.euleR;

import static spark.Spark.*;

public class Main {
    private static final String IP_ADDRESS = System.getenv("OPENSHIFT_DIY_IP") != null ? System.getenv("OPENSHIFT_DIY_IP") : "localhost";
    private static final int PORT = System.getenv("OPENSHIFT_DIY_PORT") != null ? Integer.parseInt(System.getenv("OPENSHIFT_DIY_PORT")) : 8080;

    public static void main(String[] args) {
        ipAddress(IP_ADDRESS);
        port(PORT);
        staticFileLocation("/public");
        get("/hello", (req, res) -> "Hello World");
    }
}