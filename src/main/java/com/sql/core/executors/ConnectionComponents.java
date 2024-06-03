package com.sql.core.executors;

public abstract class ConnectionComponents {
    public abstract ConnectionComponents host(String host);
    public abstract ConnectionComponents port(int port);
    public abstract ConnectionComponents database(String database);
    public abstract ConnectionComponents user(String user);
    public abstract ConnectionComponents password(String password);
    public abstract ConnectionComponents dbType(String dbType);
    public abstract String getHost();
    public abstract int getPort();
    public abstract String getDatabase();
    public abstract String getUser();
    public abstract String getPassword();
    public abstract String getDbType();
}
