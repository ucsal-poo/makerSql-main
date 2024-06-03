package com.sql.data.maker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import com.sql.anotations.OrderBy;
import com.sql.core.maker.ContextHolder;
import com.sql.core.maker.SqlMakerExtended;
import com.sql.core.maker.SqlResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

public class SqlGenerator implements SqlMakerExtended {

    @SuppressWarnings("unused")
    private Collection<Class<?>> context;
    private Map<String, String> cashQuery;

    public SqlGenerator() {
        cashQuery = new HashMap<>();
    }

    public SqlGenerator(ContextHolder context) {
        this.context = context.getContext();
        cashQuery = new HashMap<>();
    }

    @Override
    public SqlResponse generateSelectAllSql(Class<?> clazz) {
        String orderBy = getorderBy(clazz);
        String sqlQuery = selectMaker(clazz) + orderBy + ";";
        cashQuery.put("select_all_" + clazz.getSimpleName(), sqlQuery);
        return new SqlQueryData(sqlQuery, clazz);
    }

    @Override
    public SqlResponse generateSelectSql(Class<?> clazz, String operator, String... Name) {
        if (Name.length <= 0) {
            throw new RuntimeException("Clausula whare não pode ser vazia");
        }
        String orderBy = getorderBy(clazz);
        String sqlQuery = selectMaker(clazz) + " WHERE";
        for (String string : Name) {
            sqlQuery += addWhereSelect(string, clazz) + " " + operator.toUpperCase();
        }
        sqlQuery = sqlQuery.substring(0, (sqlQuery.length() - operator.length())) + orderBy + ";";
        cashQuery.put("select_" + clazz.getSimpleName(), sqlQuery);
        return new SqlQueryData(sqlQuery, clazz);
    }

    @Override
    public SqlResponse generateAgregateSql(Class<?> clazz, String function, String element, String operator,
            String... Name) {
        function = function.toUpperCase();
        String sql = choiceAgregateFunction(clazz, function, element);
        if (Name.length <= 0) {
            throw new RuntimeException("Clausula whare não pode ser vazia");
        }
        sql += " WHERE";
        for (String string : Name) {
            sql += addWhereSelect(string, clazz) + " " + operator.toUpperCase();
        }
        sql = sql.substring(0, sql.length() - operator.length()) + ";";
        return new SqlQueryData(sql, clazz);
    }

    @Override
    public SqlResponse generateInsertSql(Class<?> clazz) {
        Map<String, String> tableMap = new HashMap<>();
        SqlResponse res = InsertQuery(clazz, tableMap);
        res.setInternalQuery(tableMap);
        return res;
    }

    @Override
    public SqlResponse generateDeleteSql(Class<?> clazz) {
        SqlResponse sqlQueryResponse = deleteQuery(clazz);
        return sqlQueryResponse;
    }

    @Override
    public SqlResponse generateUpdateSql(Class<?> clazz) {
        return updateSql(clazz);
    }

    @Override
    public SqlResponse generateAgregateSql(Class<?> clazz, String function, String element) {
        String sql = choiceAgregateFunction(clazz, function, element);
        return new SqlQueryData(sql, clazz);
    }

    @Override
    public Map<String, String> getSqlCash() {
        return cashQuery;
    }

    private SqlResponse updateSql(Class<?> clazz) {
        String tableName = getTableName(clazz);
        Map<String, String> table = new HashMap<>();
        String updateBase = String.format("UPDATE %s SET ", tableName);
        for (Field field : clazz.getDeclaredFields()) {
            Class<?> fieldType = field.getType();
            if (!Collection.class.isAssignableFrom(fieldType)) {
                if (!isIdvar(field)) {
                    if (isDbClass(fieldType)) {
                        String internalQuery = updateSqlInternal(fieldType);
                        table.put(fieldType.getSimpleName(), internalQuery);
                    }
                    String collumName = getCollumName(field);
                    String compasitor = "%s = ?,";
                    updateBase += String.format(compasitor, collumName);
                }
            }
        }
        updateBase = updateBase.substring(0, updateBase.length() - 1);
        updateBase += " WHERE " + getIdForEntity(clazz) + " = ?;";
        table.put(clazz.getSimpleName(), updateBase);
        cashQuery.put("update_" + clazz.getSimpleName(), updateBase);
        return new SqlQueryData(updateBase, clazz, table);
    }

    private String updateSqlInternal(Class<?> clazz) {
        return updateSql(clazz).getSqlQuery();
    }

    private SqlResponse InsertQuery(Class<?> clazz, Map<String, String> tableMap) {
        Field[] vars = clazz.getDeclaredFields();
        isInvalidvalidInsert(vars, clazz);
        String tableName = getTableName(clazz);
        String insertBase = "INSERT INTO %s (";
        String endQuery = "VALUES (";
        SqlResponse data = new SqlQueryData();

        for (Field field : vars) {
            Class<?> fieldType = field.getType();
            if (!Collection.class.isAssignableFrom(fieldType)) {
                if (isIdvar(field)) {
                    insertBase += inserElement(field, data, tableMap);
                    endQuery += "?,";
                } else {
                    insertBase += inserElement(field, data, tableMap);
                    endQuery += "?,";
                }
            }
        }

        insertBase = insertBase.substring(0, insertBase.length() - 1);
        insertBase += ")";
        endQuery = endQuery.substring(0, endQuery.length() - 1);
        endQuery += ")";
        insertBase += endQuery;
        String insert = String.format(insertBase, tableName) + ";";

        data.setSqlQuery(insert);
        tableMap.put(clazz.getSimpleName(), insert);
        cashQuery.put("insert_" + clazz.getSimpleName(), insert);
        data.setSqlClass(clazz);
        return data;
    }

    private String inserElement(Field field, SqlResponse data, Map<String, String> tableMap) {
        String juncao = getCollumName(field) + ",";
        if (isDbClass(field.getType())) {
            InsertQuery(field.getType(), tableMap);
        }

        return juncao;
    }

    private SqlResponse deleteQuery(Class<?> clazz) {
        String tableName = getTableName(clazz);
        String idName = getIdForEntity(clazz);
        Map<String, String> table = new HashMap<>();
        String baseQury = "DELETE FROM %s WHERE %s = ?;";
        String sqlQuery = String.format(baseQury, tableName, idName);

        for (Field field : clazz.getDeclaredFields()) {
            Class<?> fieldType = field.getType();
            if (isDbClass(fieldType)) {
                String query = deleteQuery(fieldType).getSqlQuery();
                table.put(fieldType.getSimpleName(), query);
            }
        }

        cashQuery.put("delete_" + clazz.getSimpleName(), sqlQuery);
        return new SqlQueryData(sqlQuery, clazz, table);
    }

    private void isInvalidvalidInsert(Field[] vars, Class<?> clazz) {
        if (vars.length == 0) {
            throw new RuntimeException(
                    "não posso fazer inserção de uma classe que não tem atributos: " + clazz.getName());
        } else if (vars.length == 1) {
            Field field = vars[0];
            if (isIdvar(field)) {
                throw new RuntimeException("não posso fazer inserção de uma classe que so tem id auto_increment: "
                        + field.getName() + " : " + clazz.getName());
            }
        }
    }

    private String selectMaker(Class<?> clazz) {
        String tableName = getTableName(clazz);
        String baseQury = "SELECT * FROM %s %s";
        String joins = addJoins(clazz);
        String sqlQuery = String.format(baseQury, tableName, joins);

        return sqlQuery;
    }

    private String addWhereSelect(String elementFind, Class<?> clazz) {
        if (elementFind.isEmpty()) {
            throw new RuntimeException("Atributo não Pode ser vazio");
        }
        String tablename = getTableName(clazz);
        try {
            Field element = clazz.getDeclaredField(elementFind);
            String collumName = getCollumName(element);
            String where = String.format(" %s.%s = ?", tablename, collumName);
            return where;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Atributo: " + elementFind + " não encontrado");
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return "";
    }

    private String addJoins(Class<?> clazz) {
        String joinString = "";
        Field[] vars = clazz.getDeclaredFields();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<String>> futures = new ArrayList<>();

        for (Field field : vars) {
            if (isValidjoin(field)) {
                Future<String> future = executor.submit(() -> {
                    String join = prepareJoin(clazz, field.getType());
                    String subJoin = addJoins(field.getType());
                    return join + subJoin;
                });
                futures.add(future);
            }
        }

        for (Future<String> future : futures) {
            try {
                joinString += future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        return joinString;
    }

    private String prepareJoin(Class<?> clazzBase, Class<?> clazzToJoin) {
        String jonclassName = getTableName(clazzToJoin);
        String baseClass = getTableName(clazzBase);
        String idBaseClass = getIdForEntity(clazzBase);
        String idJoinClass = getIdForEntity(clazzToJoin);
        String join = String.format("LEFT JOIN %s ON %s.%s = %s.%s ", jonclassName, jonclassName, idBaseClass,
                baseClass, idJoinClass);

        return join;
    }

    private boolean isValidjoin(Field field) {
        return field.isAnnotationPresent(JoinColumn.class);
    }

    private boolean isDbClass(Class<?> clazz) {
        return clazz.isAnnotationPresent(Entity.class);
    }

    private String getTableName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            Table t = clazz.getAnnotation(Table.class);
            if (t.name().isEmpty()) {
                return clazz.getSimpleName();
            }
            return t.name();
        } else {
            return clazz.getSimpleName();
        }
    }

    private boolean isIdvar(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private String getIdForEntity(Class<?> clazz) {
        AtomicReference<String> idName = new AtomicReference<>(null);
        Field[] vars = clazz.getDeclaredFields();

        ForkJoinPool.commonPool().submit(() -> {
            Arrays.stream(vars).parallel().forEach(v -> {
                if (v.isAnnotationPresent(Id.class)) {
                    idName.set(idNameAdquire(v));
                }
            });
        }).join();

        if (idName.get() == null || idName.get().isEmpty()) {
            idName.set("element");
        }

        return idName.get();
    }

    private String idNameAdquire(Field field) {
        String nome = field.getName();
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            nome = column.name();
        }
        return nome;
    }

    private String getCollumName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column collunm = field.getAnnotation(Column.class);
            if (collunm.name().isEmpty()) {
                return field.getName();
            }
            return collunm.name();
        }
        return field.getName();
    }

    private String getorderBy(Class<?> clazz) {
        String order = "ORDER BY %s";
        AtomicReference<String> name = new AtomicReference<>();
        List<Thread> threadList = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            Thread threadorder = new Thread(() -> {
                if (field.isAnnotationPresent(OrderBy.class)) {
                    if (name.get() == null || name.get().isEmpty()) {
                        name.set(getCollumName(field));
                    } else {
                        throw new RuntimeException("Entidade: " + clazz.getName() + " não pode ter 2 @OrderBy");
                    }
                }
            });

            threadorder.start();
            threadList.add(threadorder);
        }

        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        if (name.get() == null || name.get().isEmpty()) {
            return "";
        }

        return String.format(order, name.get());
    }

    private String choiceAgregateFunction(Class<?> clazz, String function, String element) {
        switch (function) {
            case "SUM":
                return sum(clazz, element);
            case "AVG":
                return avg(clazz, element);
            default:
                throw new RuntimeException("Entidade: " + clazz.getName() + " não pode ter 2 @OrderBy");
        }
    }

    private String sum(Class<?> clazz, String element) {
        if (element.isEmpty()) {
            throw new RuntimeException("Atributo não Pode ser vazio");
        }
        String tablename = getTableName(clazz);
        String collumName;
        try {
            if (element.equals("*")) {
                collumName = "*";
            } else {
                Field elementField = clazz.getDeclaredField(element);
                collumName = getCollumName(elementField);
            }

            String sql = "SELECT SUM(%s) FROM %s";

            return String.format(sql, collumName, tablename);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Atributo: " + element + " não encontrado");
        } catch (SecurityException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String avg(Class<?> clazz, String element) {
        if (element.isEmpty()) {
            throw new RuntimeException("Atributo não Pode ser vazio");
        }
        String tablename = getTableName(clazz);
        String collumName;
        try {
            if (element.equals("*")) {
                collumName = "*";
            } else {
                Field elementField = clazz.getDeclaredField(element);
                collumName = getCollumName(elementField);
            }

            String sql = "SELECT AVG(%s) FROM %s";

            return String.format(sql, collumName, tablename);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Atributo: " + element + " não encontrado");
        } catch (SecurityException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
