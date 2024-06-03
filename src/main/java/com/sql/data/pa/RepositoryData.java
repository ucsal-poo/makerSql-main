package com.sql.data.pa;


import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

import com.sql.core.executors.ConnectionComponents;
import com.sql.core.executors.SqlConnection;
import com.sql.core.executors.SqlExecutors;
import com.sql.core.maker.SqlMakerExtended;
import com.sql.core.pa.RepositoryBase;
import com.sql.core.pa.RepositoryControll;
import com.sql.core.pa.RepositoryInitializer;

@SuppressWarnings("unchecked")
public class RepositoryData implements RepositoryInitializer {

   private Class<?> repositoryInterface;
   private Class<?> idClass;
   private Class<?> repositoryClass;
   private RepositoryControll controll;
   private SqlConnection SqlConnection;
   private SqlMakerExtended sqlMaker;
   private SqlExecutors executor;

   public RepositoryData(Class<?> repository){
      this.repositoryInterface = repository;
      findTypeRepository();
   }

   @Override
   public Object invoke(Object proxy, Method method, Object[] arg) throws Throwable {
      return controll.run(proxy, method, arg);
   }
    
   @Override
   public <E extends RepositoryBase<?, ?>> E initRepository(SqlConnection SqlConnection, SqlMakerExtended sqlMaker, SqlExecutors executor, ConnectionComponents datasource){
      this.SqlConnection = SqlConnection;
      this.sqlMaker = sqlMaker;
      this.executor = executor;
      initControll();
      return (E) Proxy.newProxyInstance(repositoryInterface.getClassLoader(), new Class[]{repositoryInterface}, this);
   }

   private void findTypeRepository(){
      Type type = repositoryInterface.getGenericInterfaces()[0];
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            if (typeArguments.length > 0) {
               repositoryClass = (Class<?>) typeArguments[0];
               idClass = (Class<?>) typeArguments[1]; 
            }
        }
   }

   public static <E extends RepositoryBase<?, ?>> E createProxy(Class<E> repositoryInterface) {
      RepositoryData data = new RepositoryData(repositoryInterface);
      return (E) Proxy.newProxyInstance(
         repositoryInterface.getClassLoader(),
         new Class[]{repositoryInterface},
         data
      );
   }

   private void initControll(){
      if(controll == null){
         this.controll = new ProxyControll(repositoryInterface, idClass, repositoryClass, SqlConnection, sqlMaker, executor);
      }
   }
}
