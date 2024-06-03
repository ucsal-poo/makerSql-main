package com.sql.data.maker;

import java.util.HashMap;
import java.util.Map;

import com.sql.core.maker.DdlSql;
import com.sql.core.maker.SqlMaker;
import com.sql.core.maker.SqlMakerExtended;

public class DdlSqlData implements DdlSql {

    private SqlMaker sqlMaker;
    private Map<String, String> adquireSqlAll;
    private Map<String, Map<String, String>> adquireSql;

    public DdlSqlData(SqlMakerExtended extended){
        sqlMaker = extended;
        adquireSqlAll = new HashMap<>();
        adquireSql = new HashMap<>();
    }

    @Override
    public void add(Class<?> clazz){
        Map<String, String> aux = new HashMap<>();
        String delete = sqlMaker.generateDeleteSql(clazz).getSqlQuery();
        String sellectAll = sqlMaker.generateSelectAllSql(clazz).getSqlQuery();
        String update = sqlMaker.generateUpdateSql(clazz).getSqlQuery();

        adquireSqlAll.put("Delete", delete);
        adquireSqlAll.put("SellectAll", sellectAll);
        adquireSqlAll.put("Update", update);

        adquireSql.put(clazz.getSimpleName(), adquireSqlAll);
    } 

    @Override
    public Map<String, String> adquireSql() {
        return adquireSqlAll;
    }

    @Override
    public Map<String, String> adquireSql(String type) {
    
        if(adquireSql.containsKey(type)){
            return adquireSql.get(type); 
        }

        return new HashMap<>();
    }


}
