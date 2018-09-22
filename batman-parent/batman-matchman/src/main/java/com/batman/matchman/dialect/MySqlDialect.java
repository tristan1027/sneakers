package com.batman.matchman.dialect;

import com.batman.common.dialect.Dialect;
import com.batman.matchman.intercptor.PageThreadLocal;

public class MySqlDialect extends Dialect {

    @Override
    public String getPageSql(String sql, int offset, int limit) {

        StringBuffer sb = new StringBuffer();
        sb.append(sql.trim());
        sb.append(" limit " + offset);
        sb.append(", " + limit);
        return sb.toString();
    }

	public String getPageSql(String sql) {
		    int curPage = PageThreadLocal.getCurPage();
		    int pageSize = PageThreadLocal.getPageSize();
		    int startIndex = (curPage-1)*pageSize;//起始位置
		    StringBuffer sb = new StringBuffer();
	        sb.append(sql.trim());
	        sb.append(" limit " + startIndex);
	        sb.append(", " + pageSize);
	        return sb.toString();
	}

}
