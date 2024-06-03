package com.sql.core.maker;

import java.util.Map;

public interface SqlMaker {
    Map<String, String> getSqlCash();
    SqlResponse generateSelectAllSql(Class<?> clazz);
    SqlResponse generateSelectSql(Class<?> clazz, String operator, String... name);
    SqlResponse generateInsertSql(Class<?> clazz);
    SqlResponse generateDeleteSql(Class<?> clazz);
    SqlResponse generateUpdateSql(Class<?> clazz);
}
