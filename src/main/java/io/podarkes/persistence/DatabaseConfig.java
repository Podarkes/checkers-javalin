package io.podarkes.persistence;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class DatabaseConfig {
    public static DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(System.getenv("url"));
        dataSource.setUser(System.getenv("user"));
        dataSource.setPassword(System.getenv("password"));
        return dataSource;
    }
}
