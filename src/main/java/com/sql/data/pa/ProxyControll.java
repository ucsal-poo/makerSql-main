package com.sql.data.pa;

import java.lang.reflect.Method;

import com.sql.core.executors.SqlConnection;
import com.sql.core.executors.SqlExecutors;
import com.sql.core.maker.SqlMakerExtended;
import com.sql.core.maker.SqlResponse;
import com.sql.core.pa.RepositoryControll;

@SuppressWarnings("unused")
class ProxyControll implements RepositoryControll{
    
    private Class<?> repositoryInterface;
    private Class<?> idClass;
    private Class<?> repositoryClass; 
    private SqlConnection SqlConnection;
    private SqlMakerExtended sqlMaker;
    private SqlExecutors executor;


    public ProxyControll(Class<?> repositoryInterface, Class<?> idClass, Class<?> repositoryClass,
        SqlConnection sqlConnection, SqlMakerExtended sqlMaker, SqlExecutors executor) {
        this.repositoryInterface = repositoryInterface;
        this.idClass = idClass;
        this.repositoryClass = repositoryClass;
        SqlConnection = sqlConnection;
        this.sqlMaker = sqlMaker;
        this.executor = executor;
    }

    @Override
    public Object run(Object proxy, Method method, Object[] arg){
        switch (method.getName()) {
            case "save":
                save(arg[0]);
                break;
            case "delete":
                delete(arg[0]);
                break;
            case "findAll":
                findAll();
                break;
            case "findById":
                findById(arg[0]);
                break;
            default:
                break;
        }
        return null;
    }

    private void save(Object entity){
        System.out.println(entity);
    }

    private void delete(Object entity){
        System.out.println(entity);
    }

    private void findAll(){
        SqlResponse sqlCommand = sqlMaker.generateSelectAllSql(repositoryClass);
        System.out.println(sqlCommand.getSqlQuery());
    }

    private void findById(Object id){
        System.out.println(id);
    }

}
