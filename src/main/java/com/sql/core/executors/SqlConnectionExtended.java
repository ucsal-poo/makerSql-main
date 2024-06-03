package com.sql.core.executors;

import java.sql.Connection;

import com.sql.core.PoolEmptyException;

public interface SqlConnectionExtended extends SqlConnection{
    void generatePoolConnection(ConnectionConfig config);
    void generatePoolConnection(ConnectionComponents config);
    void generatePoolConnection(String host, int port, String dataBase, String user, String password, String dbTpe);
    void releaseConnection(Connection connection);
    Connection getConnection() throws PoolEmptyException;
}
