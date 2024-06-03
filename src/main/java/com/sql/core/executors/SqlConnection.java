package com.sql.core.executors;

import java.sql.Connection;

public interface SqlConnection {
    Connection generateConnection(ConnectionConfig config);
    Connection generateConnection(ConnectionComponents config);
    Connection generateConnection(String host, int port, String dataBase, String user, String password, String dbTpe);
}
