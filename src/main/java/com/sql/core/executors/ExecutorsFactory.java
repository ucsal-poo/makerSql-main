package com.sql.core.executors;

import com.sql.core.maker.SqlMaker;
import com.sql.core.maker.SqlMakerExtended;

public abstract class ExecutorsFactory {
    public abstract SqlExecutors createExcutors(SqlMaker sqlmaker, SqlConnection conectionMaker);
    public abstract SqlExecutors createExcutorsExtended(SqlMakerExtended sqlmaker, SqlConnectionExtended conectionMaker);
}
