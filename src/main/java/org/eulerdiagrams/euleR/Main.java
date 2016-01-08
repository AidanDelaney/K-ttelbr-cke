package org.eulerdiagrams.euleR;

import static spark.Spark.*;

import java.sql.*;
import java.util.Optional;

import com.google.gson.Gson;

public class Main {
    private static final String IP_ADDRESS = System.getenv("OPENSHIFT_DIY_IP") != null ? System.getenv("OPENSHIFT_DIY_IP") : "0.0.0.0";
    private static final int PORT = System.getenv("OPENSHIFT_DIY_PORT") != null ? Integer.parseInt(System.getenv("OPENSHIFT_DIY_PORT")) : 8080;

    public static Optional<Connection> openDB() {
        Optional<Connection> cnx = Optional.empty();
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            cnx = Optional.of(c);
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return cnx;
    }

    public static void main(String[] args) {
        ipAddress(IP_ADDRESS);
        port(PORT);
        staticFileLocation("/public");
        Gson gson = new Gson();

        final Connection c = openDB().get();
        try {
            Class.forName("org.sqlite.JDBC");

            Statement s = c.createStatement();
            // We don't have rich datatypes with SQLite (https://www.sqlite.org/datatype3.html)
            String cTable = "CREATE TABLE IF NOT EXISTS AreaRequestResponse"
                    + "("
                    + "TSTAMP    INTEGER NOT NULL," // time since epoch
                    + "INPUT     TEXT    NOT NULL,"
                    + "OUTPUT    TEXT    NOT NULL,"
                    + "VALID     INTEGER NOT NULL" // 0 false, 1 true
                    + ")";
            s.execute(cTable);
            s.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");

        post("/layout", (req, res) -> {
            JSONArea pojo = gson.fromJson(req.body(), JSONArea.class);
            KöttelbrückeService kbs = new KöttelbrückeService(pojo);

            return gson.toJson(new JSONLayoutResponse(kbs));
        });

        post("/areas", (req, res) -> {
            JSONAreaRequest pojo = gson.fromJson(req.body(), JSONAreaRequest.class);

            JSONAreaResponse jar = new JSONAreaResponse(pojo);
            String rval = gson.toJson(jar);

            try {
                PreparedStatement insert = c.prepareStatement("INSERT INTO AreaRequestResponse VALUES (?, ?, ?, ?)");
                insert.setLong(1, System.currentTimeMillis());
                insert.setString(2, req.body());
                insert.setString(3, rval);
                insert.setBoolean(4, jar.areas.values().stream().allMatch(x -> x >= 0.0));
                insert.executeUpdate();
                insert.close();
            } catch (Exception e) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            }
            return rval;
        });
    }
}