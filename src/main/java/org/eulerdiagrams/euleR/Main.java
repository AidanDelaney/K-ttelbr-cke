package org.eulerdiagrams.euleR;

import static spark.Spark.*;

import com.google.gson.Gson;

public class Main {
    private static final String IP_ADDRESS = System.getenv("OPENSHIFT_DIY_IP") != null ? System.getenv("OPENSHIFT_DIY_IP") : "0.0.0.0";
    private static final int PORT = System.getenv("OPENSHIFT_DIY_PORT") != null ? Integer.parseInt(System.getenv("OPENSHIFT_DIY_PORT")) : 8080;

    public static void main(String[] args) {
        ipAddress(IP_ADDRESS);
        port(PORT);
        staticFileLocation("/public");
        Gson gson = new Gson();
        post("/layout", (req, res) -> {
            JSONArea pojo = gson.fromJson(req.body(), JSONArea.class);
            KöttelbrückeService kbs = new KöttelbrückeService(pojo);

            return gson.toJson(new JSONLayoutResponse(kbs));
        });

        post("/areas", (req, res) -> {
            JSONAreaRequest pojo = gson.fromJson(req.body(), JSONAreaRequest.class);

            return gson.toJson(new JSONAreaResponse(pojo));
        });
    }
}