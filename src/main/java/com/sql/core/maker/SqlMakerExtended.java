package com.sql.core.maker;

public interface SqlMakerExtended extends SqlMaker{
    SqlResponse generateAgregateSql(Class<?> clazz, String function, String element);
    SqlResponse generateAgregateSql(Class<?> clazz, String function, String element, String operator, String... name);
}
