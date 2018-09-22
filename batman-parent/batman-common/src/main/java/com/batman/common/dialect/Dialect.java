package com.batman.common.dialect;



public abstract class Dialect {    
    public static enum Type {    
        MYSQL,    
        ORACLE    
    }    
        
    public abstract String getPageSql(String sql, int offset, int limit);
    public abstract String getPageSql(String sql);
}