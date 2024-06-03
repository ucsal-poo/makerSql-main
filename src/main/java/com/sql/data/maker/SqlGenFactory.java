package com.sql.data.maker;

import com.sql.core.maker.SqlMaker;
import com.sql.core.maker.SqlMakerExtended;
import com.sql.core.maker.SqlmakerFactory;

public class SqlGenFactory extends SqlmakerFactory{

    @Override
    public SqlMaker createSqlMaker(String dbType) {
        if(dbType == null){dbType = "";}
        switch (dbType) {
            case "postgres":
                return new SqlGenerator();
            default:
                return new SqlGenerator();
        }
    }

    @Override
    public SqlMakerExtended createSqlMakerExtended(String dbType) {
        if(dbType == null){dbType = "";}
        switch (dbType) {
            case "postgres":
                return new SqlGenerator();
            default:
                return new SqlGenerator();
        }
    }
    
}
