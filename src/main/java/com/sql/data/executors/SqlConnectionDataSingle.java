package com.sql.data.executors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sql.core.executors.ConnectionComponents;
import com.sql.core.executors.ConnectionConfig;
import com.sql.core.executors.SqlConnection;

public class SqlConnectionDataSingle implements SqlConnection{

    @Override
    public Connection generateConnection(ConnectionConfig config) {
        ConnectionComponents builder = new ConnectionInfo();
        config.config(builder);
        return genConn(builder.getHost(), builder.getPort(), builder.getDatabase(), builder.getUser(), builder.getPassword(), builder.getDbType());
    }

    @Override
    public Connection generateConnection(ConnectionComponents config) {
        return genConn(config.getHost(), config.getPort(), config.getDatabase(), config.getUser(), config.getPassword(), config.getDbType());
    }

    @Override
    public Connection generateConnection(String host, int port, String dataBase, String user, String password, String dbTpe) {
        return genConn(host, port, dataBase, user, password, dbTpe);
    }
    
    private Connection genConn(String host, int port, String dataBase, String user, String password, String dbTpe){
        Connection connection = null;
        port = addDefaultPortFilter(port, dbTpe);
        try {
            String driverName = obterDriverString(dbTpe);
            String url = sqlStringGenerator(host, port, dataBase, user, password, dbTpe);
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, user, password);
        }catch(ClassNotFoundException e){
            throw new RuntimeException("Driver Error: "+ e.getMessage());
        }catch (SQLException  e) {
            throw new RuntimeException("SqlConnection Error: "+ e.getMessage());
        }catch (Exception e) {
            throw new RuntimeException("Connection Error: "+ e.getMessage());
        }
        return connection;
    }

    private String obterDriverString(String dbType) {
        switch (dbType) {
            case "postgres":
                return "org.postgresql.Driver";
            case "mysql":
                return "com.mysql.cj.jdbc.Driver";
            case "oracle":
                return "oracle.jdbc.driver.OracleDriver";
            case "sqlserver":
                return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            default:
                throw new RuntimeException("Erro ao obter o driver: " + dbType + " não reconhecido");
        }
    }
    
    private String sqlStringGenerator(String host, int port, String dataBase, String user, String password, String dbTpe){
        String url = "jdbc:?://" + host + ":" + port + "/" + dataBase;
        return convertString(url, dbTpe);
    }

    private String convertString(String url, String DbType){
        switch (DbType) {
            case "postgres":
                return url.replace("?", "postgresql");
            case "mysql":
                return url.replace("?", "mysql");
            case "oracle":
                return url.replace("?", "oracle");
            case "sqlserver":
                return url.replace("?", "sqlserver");
            default:
                throw new RuntimeException("Erro ao obter o driver: " + DbType + " não reconhecido");
        }
    }

    private int addDefaultPortFilter(int port, String DbType){
        if(port == 0){
            switch (DbType) {
                case "postgres":
                    return 5432;
                case "mysql":
                    return 3306;
                case "oracle":
                    return 1521;
                case "sqlserver":
                    return 1433;
                default:
                    throw new RuntimeException("Erro ao obter o driver: " + DbType + " não reconhecido");
            }
        }else{
            return port;
        }
    }
}
