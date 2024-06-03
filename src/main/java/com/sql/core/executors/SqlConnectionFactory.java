package com.sql.core.executors;

public abstract class SqlConnectionFactory {
    public abstract SqlConnection createSqlConnection();
    public abstract SqlConnectionExtended createSqlConnectionExtended();
}
