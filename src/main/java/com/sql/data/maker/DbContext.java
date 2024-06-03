package com.sql.data.maker;

import java.util.ArrayList;
import java.util.List;

import com.sql.core.maker.ContextHolder;

public class DbContext extends ContextHolder{

    @Override
    public void addToContext(Class<?> clazz) {
        
    }

    @Override
    public List<Class<?>> getContext() {
        return new ArrayList<>();
    }
    
}
