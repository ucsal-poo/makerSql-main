package com.sql.data.executors;

import com.sql.core.executors.SqlConnection;
import com.sql.core.executors.SqlConnectionExtended;
import com.sql.core.executors.SqlConnectionFactory;

public class ConnFactory extends SqlConnectionFactory{

    @Override
    public SqlConnection createSqlConnection() {
        return new SqlConnectionDataSingle();
    }

    @Override
    public SqlConnectionExtended createSqlConnectionExtended() {
        return new SqlConnectionData();
    }
    
}
