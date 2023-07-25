package com.luchkovskiy.util;

import com.luchkovskiy.configuration.PropertiesManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class ConnectionManager {

    private final PropertiesManager properties;

    public void loadDriver() {
        try {
            Class.forName(properties.getDriverName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load driver");
        }
    }

    public Connection open() {
        try {
            return DriverManager.getConnection(properties.getUrl(), properties.getLogin(), properties.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to open connection");
        }
    }

}
