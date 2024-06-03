package com.sql.core;

public class PoolEmptyException extends Exception{
    public PoolEmptyException(String msg){
        super(msg);
    }
}
