package com.sql.core.maker;

import java.util.Map;

public interface DdlSql {
    void add(Class<?> clazz);
    Map<String, String> adquireSql();
    Map<String, String> adquireSql(String type);
}
