package com.sql.core.maker;

public abstract class SqlmakerFactory {
    public abstract SqlMaker createSqlMaker(String dbType);
    public abstract SqlMakerExtended createSqlMakerExtended(String dbType);
}
