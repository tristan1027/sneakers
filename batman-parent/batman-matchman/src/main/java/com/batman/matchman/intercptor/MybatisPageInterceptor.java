package com.batman.matchman.intercptor;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import com.batman.common.dialect.Dialect;
import com.batman.matchman.dialect.MySqlDialect;
import com.batman.matchman.dialect.OracleDialect;



@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})  
public class MybatisPageInterceptor implements Interceptor{
	 private String databaseType;//数据库类型，不同的数据库有不同的分页方法  
	 private String pageSqlId = ".*query$";//默认重新组装id含有query的查询语句
	 private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	 private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	 private static final Logger logger = Logger.getLogger(MybatisPageInterceptor.class);		
	 
    /***
     * 拦截执行方法
     */

	public Object intercept(Invocation invocation) throws Throwable {
		  // logger.info("MybatisPageInterceptor被执行" +Thread.currentThread());
		   StatementHandler delegate = (StatementHandler) invocation.getTarget();   
           MetaObject metaStatementHandler = MetaObject.forObject(delegate, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
           MappedStatement mappedStatement = (MappedStatement)
                   metaStatementHandler.getValue("delegate.mappedStatement");
           // 只重写需要分页的sql语句。通过MappedStatement的ID匹配，默认重写以query结尾的MappedStatement的sql  
             String  mapId =  mappedStatement.getId();
           if (mapId.matches(pageSqlId)) {
        	   //RoutingStatementHandler实现的所有StatementHandler接口方法里面都是调用的delegate对应的方法。  
    	       BoundSql boundSql = delegate.getBoundSql();  
                 //Object parameterObject = boundSql.getParameterObject();
    	          //我们采用ThreadLocal模式来解决问题
            	  //获取业务sql语句。即配置文件中传入的sql  
                   String sql = boundSql.getSql(); 
                   String pageSql = sql;
                   Dialect.Type  dialectType =    Dialect.Type.valueOf(databaseType.toUpperCase());
                   switch (dialectType) {
						    case ORACLE:
							 pageSql = new  OracleDialect().getPageSql(sql);
							 break;
							 
						    case MYSQL:
						     pageSql = new  MySqlDialect().getPageSql(sql);
						    break;
		
						 default:
							break;
				    }
                   //改变mybatis的要执行的sql为我们重新组合的sql语句。
                  // logger.info("MybatisPageInterceptor被执行,最终sql语句====》:" +pageSql);
                   metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
                   // 采用物理分页，就不需要mybatis的内存分页了，重置下面的两个参数
                   metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
                   metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT); 
                  
                   // 重设分页参数里的总页数等  
                   Connection connection = (Connection) invocation.getArgs()[0];  
                   setPageParameter(sql, connection, mappedStatement, boundSql);  
           }
           //执行下一个
	       return invocation.proceed();
	}
     
	/***
	 * 是否拦截。 StatementHandler类型的才拦截
	 */

	public Object plugin(Object target) {
		// 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
	}
	/**
	 * 一些mybatis.xml中的配置信息
	 * 数据库方言和要拦截的方法的ID正则匹配表达式
	 */

	public void setProperties(Properties properties) {
		 this.databaseType = properties.getProperty("databaseType"); 
		 this.pageSqlId = properties.getProperty("pageSqlId");
	}  
	
	 /** 
     * 获取总记录数 
     * @param sql 
     * @param connection 
     * @param mappedStatement 
     * @param boundSql 
     * @param page 
     */  
    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement,  
                                  BoundSql boundSql) {  
        // 记录总记录数  
        String countSql = "select count(0) from (" + sql + ")  as tb";  
        PreparedStatement countStmt = null;  
        ResultSet rs = null;  
        try {  
        	logger.info("MybatisPageInterceptor被执行,最终select count(0) 语句====》:" +countSql);
            countStmt = connection.prepareStatement(countSql);  
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,  
                    boundSql.getParameterMappings(), boundSql.getParameterObject());  
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());  
            rs = countStmt.executeQuery();  
            int totalCount = 0;  
            if (rs.next()) {  
                totalCount = rs.getInt(1);  
            } 
            logger.info("MybatisPageInterceptor-->总条数"+totalCount);  
            int totalPage = totalCount / PageThreadLocal.getPageSize() + ((totalCount % PageThreadLocal.getPageSize() == 0) ? 0 : 1);  
            //设置总页数和总条数
            PageThreadLocal.setTotalPage(totalPage);
            PageThreadLocal.setTotal(totalCount);
        } catch (SQLException e) {  
            logger.error("Ignore this exception", e);  
        } finally {  
            try {  
            	if(rs != null){
            		 rs.close();  
            	} 
            } catch (SQLException e) {  
                logger.error("Ignore this exception", e);  
            }  
            try {
            	if(countStmt != null){
            	  countStmt.close();  
            	}
               
            } catch (SQLException e) {  
                logger.error("Ignore this exception", e);  
            }  
        }  
    }  
    
    /** 
     * 代入参数值 
     * @param ps 
     * @param mappedStatement 
     * @param boundSql 
     * @param parameterObject 
     * @throws SQLException 
     */  
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,  
                               Object parameterObject) throws SQLException {  
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);  
        parameterHandler.setParameters(ps);  
    }  
}

