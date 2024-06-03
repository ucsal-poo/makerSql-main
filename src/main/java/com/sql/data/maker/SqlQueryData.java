package com.sql.data.maker;

import java.util.HashMap;
import java.util.Map;

import com.sql.core.maker.SqlResponse;

public class SqlQueryData extends SqlResponse {

    private String sqlQuery;
    private Map<String, String> queryTable;
    private Class<?> sqlClass;

    public SqlQueryData() {
    }

    public SqlQueryData(String sql) {
        sqlQuery = sql;
        queryTable = new HashMap<>();
    }

    public SqlQueryData(String sql, Class<?> sqlClass) {
        sqlQuery = sql;
        this.sqlClass = sqlClass;
        queryTable = new HashMap<>();
    }

    public SqlQueryData(String sql, Class<?> sqlClass, Map<String, String> queryTable) {
        sqlQuery = sql;
        this.sqlClass = sqlClass;
        this.queryTable = queryTable;
    }

    @Override
    public String getSqlQuery() {
        return sqlQuery;
    }

    @Override
    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    @Override
    public void setSqlClass(Class<?> clazz) {
        this.sqlClass = clazz;
    }

    @Override
    public Class<?> getSqlClass() {
        return sqlClass;
    }

    @Override
    public Map<String, String> getInternalQuery() {
        return queryTable;
    }

    @Override
    public void setInternalQuery(Map<String, String> queryTable) {
        this.queryTable = queryTable;
    }

    @Override
    public int size() {
        return queryTable.size();
    }

    @Override
    public String toString() {
        return "sqlQuery='" + sqlQuery + '\'' +
                '}';
    }
}
