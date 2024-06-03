package com.sql.core.executors;

import java.sql.Connection;
import java.sql.ResultSet;

public interface SqlExecutors {
    ResultSet executeExcalar(Connection connection, String query, Object...bindParams);
    int executeNonExcalar(Connection connection, String query, Object...bindParams);
}
