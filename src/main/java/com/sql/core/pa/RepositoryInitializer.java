package com.sql.core.pa;

import java.lang.reflect.InvocationHandler;

import com.sql.core.executors.ConnectionComponents;
import com.sql.core.executors.SqlConnection;
import com.sql.core.executors.SqlExecutors;
import com.sql.core.maker.SqlMakerExtended;

public interface RepositoryInitializer extends InvocationHandler{
    <E extends RepositoryBase<?, ?>> E initRepository(SqlConnection SqlConnection, SqlMakerExtended sqlMaker, SqlExecutors executor, ConnectionComponents datasource);
}
