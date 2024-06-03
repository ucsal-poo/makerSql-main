package com.sql.data.executors;

import java.sql.Connection;
import java.sql.ResultSet;
import com.sql.core.executors.SqlExecutors;

public class ExecutorsData implements SqlExecutors{

    @Override
    public ResultSet executeExcalar(Connection connection, String query, Object... bindParams) {
        throw new UnsupportedOperationException("Unimplemented method 'executeExcalar'");
    }

    @Override
    public int executeNonExcalar(Connection connection, String query, Object... bindParams) {
        throw new UnsupportedOperationException("Unimplemented method 'executeNonExcalar'");
    }
}
