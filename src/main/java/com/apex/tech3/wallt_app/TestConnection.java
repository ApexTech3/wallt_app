package com.apex.tech3.wallt_app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://postgres-2.c1mssmkqachz.eu-central-1.rds.amazonaws.com:5432/postgres";
        String username = "postgres";
        String password = "tikvi4ki";

        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch(SQLException e) {
            System.out.println("Connection to the PostgreSQL server failed.");
            e.printStackTrace();
        }
    }
}
