package com.sql.data.executors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sql.core.PoolEmptyException;
import com.sql.core.executors.ConnectionComponents;
import com.sql.core.executors.ConnectionConfig;
import com.sql.core.executors.SqlConnectionExtended;

public class SqlConnectionData implements SqlConnectionExtended{
    private static final int POOL_SIZE = 5;
    private static List<Connection> pool;

    public SqlConnectionData(){
        pool = new ArrayList<>(POOL_SIZE);
    }

    @Override
    public Connection generateConnection(ConnectionConfig config) {
        try {
            return getConnection();
        } catch (PoolEmptyException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Connection generateConnection(ConnectionComponents config) {
        try {
            return getConnection();
        } catch (PoolEmptyException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Connection generateConnection(String host, int port, String dataBase, String user, String password, String dbTpe) {
        try {
            return getConnection();
        } catch (PoolEmptyException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public synchronized void generatePoolConnection(ConnectionConfig config) {
        ConnectionComponents builder = new ConnectionInfo();
        config.config(builder);

        for (int i = 0; i < POOL_SIZE; i++) {
            Connection con = genConn(builder.getHost(), builder.getPort(), builder.getDatabase(), builder.getUser(), builder.getPassword(), builder.getDbType());
            pool.add(con);
        } 
        System.out.println("Pool de conexão bem Estabelecida ");  
    }

    @Override
    public synchronized void generatePoolConnection(ConnectionComponents config) {
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection con = genConn(config.getHost(), config.getPort(), config.getDatabase(), config.getUser(), config.getPassword(), config.getDbType());
            pool.add(con);
        }
        System.out.println("Pool de conexão bem Estabelecida ");  
    }

    @Override
    public synchronized void generatePoolConnection(String host, int port, String dataBase, String user, String password, String dbTpe) {
        for (int i = 0; i < POOL_SIZE; i++){
            Connection con = genConn(host, port, dataBase, user, password, dbTpe);
            pool.add(con);
        }
        System.out.println("Pool de conexão bem Estabelecida ");  
    }

    @Override
    public synchronized Connection getConnection() throws PoolEmptyException{
        if (pool.isEmpty()) {
            throw new PoolEmptyException("Pool de conexões esgotado");
        }
        return pool.remove(0);
    }

    @Override
    public void releaseConnection(Connection connection) {
        pool.add(connection);
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
