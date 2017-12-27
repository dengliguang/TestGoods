package cn.itcast.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import cn.itcast.jdbc.JdbcUtils;
/*
 * JDBCUtils:������ȡConnection   
 * 		* �ײ�ʹ���ˣ�c3p0���ݿ����ӳ�
 * 		* ����Ҫmysql����
 */
public class JDBCUtilsTest {
	/*
	 * �ײ�ʹ����c3p0���ӳأ���Ҫ��c3p0�����ļ�
	 */
	@Test
	public void testGetConnection() throws SQLException {
		Connection conn=JdbcUtils.getConnection();
		System.out.println(conn);
		//releaseConnection()  ���������йؾͲ��رա��޹ؾ͹ر�
		JdbcUtils.releaseConnection(conn);
		System.out.println(conn.isClosed());
	}
	
	/*
	 * JDBCUtils���ṩ����������صĹ���
	 */
	@Test
	public void testTransaction(){
		try {
			JdbcUtils.beginTransaction();	//��������
			
			JdbcUtils.commitTransaction();	//�ύ����
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();	//�ع�����
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
}
