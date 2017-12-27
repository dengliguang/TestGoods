package cn.itcast.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import cn.itcast.jdbc.JdbcUtils;
/*
 * JDBCUtils:用来获取Connection   
 * 		* 底层使用了：c3p0数据库连接池
 * 		* 还需要mysql驱动
 */
public class JDBCUtilsTest {
	/*
	 * 底层使用了c3p0连接池，需要有c3p0配置文件
	 */
	@Test
	public void testGetConnection() throws SQLException {
		Connection conn=JdbcUtils.getConnection();
		System.out.println(conn);
		//releaseConnection()  当和事物有关就不关闭、无关就关闭
		JdbcUtils.releaseConnection(conn);
		System.out.println(conn.isClosed());
	}
	
	/*
	 * JDBCUtils还提供了与事物相关的功能
	 */
	@Test
	public void testTransaction(){
		try {
			JdbcUtils.beginTransaction();	//开启事物
			
			JdbcUtils.commitTransaction();	//提交事物
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();	//回滚事物
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
}
