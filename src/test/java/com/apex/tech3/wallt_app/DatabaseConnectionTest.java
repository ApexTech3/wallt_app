package com.apex.tech3.wallt_app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
public class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testConnection() {
        try {
            dataSource.getConnection();
            System.out.println("Database connection is successful");
        } catch(SQLException e) {
            throw new RuntimeException("Cannot connect to the database", e);
        }
    }
}