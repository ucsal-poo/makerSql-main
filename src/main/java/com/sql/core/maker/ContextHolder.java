package com.sql.core.maker;

import java.util.List;

public abstract class ContextHolder {
   public abstract void addToContext(Class<?>clazz); 
   public abstract List<Class<?>> getContext();
}
