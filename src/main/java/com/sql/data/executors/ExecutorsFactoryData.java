package com.sql.data.executors;

import com.sql.core.executors.ExecutorsFactory;
import com.sql.core.executors.SqlConnection;
import com.sql.core.executors.SqlConnectionExtended;
import com.sql.core.executors.SqlExecutors;
import com.sql.core.maker.SqlMaker;
import com.sql.core.maker.SqlMakerExtended;

public class ExecutorsFactoryData extends ExecutorsFactory{

    @Override
    public SqlExecutors createExcutors(SqlMaker sqlmaker, SqlConnection conectionMaker) {
        return new ExecutorsData();
    }

    @Override
    public SqlExecutors createExcutorsExtended(SqlMakerExtended sqlmaker, SqlConnectionExtended conectionMaker) {
        return new ExecutorsData();
    }
    
}
