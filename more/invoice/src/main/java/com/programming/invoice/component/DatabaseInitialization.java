package com.programming.invoice.component;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitialization {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseInitialization(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void runAfterSchemaCreation() {
        // Execute your SQL query or any other initialization logic here
        String sqlQuery = """
                CREATE SCHEMA IF NOT EXISTS tst;
                SET NAMES 'UTF8MB4';
                SET TIME_ZONE = 'US/Eastern';
                SET TIME_ZONE = '-4:00';
                USE tst;
                """;
        String query = """
                CREATE TABLE Roles (
                id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(50) NOT NULL,
                permission VARCHAR(50) NOT NULL,
                CONSTRAINT UQ_Roles_Name UNIQUE(name)
                )
                """;
        jdbcTemplate.execute(query);
    }
}