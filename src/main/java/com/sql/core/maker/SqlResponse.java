package com.sql.core.maker;

import java.util.Map;

public abstract class SqlResponse {
    public abstract String getSqlQuery();
    public abstract void setSqlQuery(String sqlQuery);
    public abstract Map<String, String> getInternalQuery();
    public abstract void setInternalQuery(Map<String, String> queryTable);
    public abstract Class<?> getSqlClass();
    public abstract void setSqlClass(Class<?> clazz);
    public abstract int size();
}
