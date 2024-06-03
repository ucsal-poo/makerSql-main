package com.sql.core.executors;

@FunctionalInterface
public interface ConnectionConfig {
    void config(ConnectionComponents components);
}
