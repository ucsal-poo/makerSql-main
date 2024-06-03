package com.sql.core.pa;

import java.lang.reflect.Method;

public interface RepositoryControll {
    Object run(Object proxy, Method method, Object[] arg);
}
