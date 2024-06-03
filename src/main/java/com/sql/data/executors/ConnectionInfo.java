package com.sql.data.executors;

import com.sql.core.executors.ConnectionComponents;

public class ConnectionInfo extends ConnectionComponents{
    private String host;
    private int port;
    private String database;
    private String user;
    private String password;
    private String dbType;

    @Override
    public ConnectionComponents host(String host) {
        this.host = host;
        return this;
    }
    @Override
    public ConnectionComponents port(int port) {
        this.port = port;
        return this;
    }
    @Override
    public ConnectionComponents database(String database) {
        this.database = database;
        return this;
    }
    @Override
    public ConnectionComponents user(String user) {
        this.user = user;
        return this;
    }
    @Override
    public ConnectionComponents password(String password) {
        this.password = password;
        return this;
    }
    @Override
    public ConnectionComponents dbType(String dbType) {
        this.dbType = dbType;
        return this;
    }

    @Override
    public String getHost() {
        return host;
    }
    @Override
    public int getPort() {
        return port;
    }
    @Override
    public String getDatabase() {
        return database;
    }
    @Override
    public String getUser() {
        return user;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getDbType() {
        return dbType;
    }

    
    
}
