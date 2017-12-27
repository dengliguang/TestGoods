package cn.itcast.test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import bean.Dept;
import bean.Emp;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.JdbcUtils;
import cn.itcast.jdbc.TxQueryRunner;
/*
 * TxQueryRunner:����queryRunner�����࣡(commons-dbutils.jar)
 * 		��������queryRunner����
 * 		����ʹ������ײ���JDBCUtils����ȡ����
 * 
 * QueryRunner������������
	 * 		*  update() ---> insert��update��delete
	 * 		*  query()	---> select
	 * 		*  batch() 	---> ��������
 */
public class TxQueryRunnerTest {
	/*
	 * ���ԣ�update()�����ģ�insert��update��delete���
	 * 
	 */
	@Test
	public void testUpdateInsert() throws SQLException {
		String sql="insert into emp_tbl(emp_name,gender,phone,d_id) values(?,?,?,?)";
		Object[] params={"����yu","��","18774814558",3};   
		
		QueryRunner qr=new TxQueryRunner();		//�ڲ����ṩJDBCUtils�������ݿ�
		int b=qr.update(sql,params);
		System.out.println(b);
	}
	
	/*
	 * �������
	 */
	@Test
	public void testUpdate() throws Exception {
		try {
			JdbcUtils.beginTransaction();	//��������
			
			String sql="insert into emp_tbl(emp_name,gender,phone,d_id) values(?,?,?,?)";
			QueryRunner qr=new TxQueryRunner();		//�ڲ����ṩJDBCUtils�������ݿ�
			
			Object[] params={"����yu","��","18774814558",3};  
			int b=qr.update(sql,params);
			System.out.println(b);
			
			if(true){
				throw new Exception();
			}
			
			params=new Object[]{"����yu","��","18774814558",3};
			int i=qr.update(sql,params);
			System.out.println(i);
			
			JdbcUtils.commitTransaction();	//�ύ����
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();	//�ع�����
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw e;
		}
	}
	
	/*
	 * ���Բ�ѯ��
	 * 		jdbc��ѯ�Ľ���ǣ�ResultSet
	 * 		QueryRunner��ѯ�Ľ����ͨ��ResultSetӳ��������
	 * 			* ��һ��:ͨ��QueryRunnerִ��select���õ�ResultSet
	 * 			* ��ResultSetת������������
	 * 		ת�������
	 * 			* 1��javaBean: �ѽ������װ��JavaBean��
	 * 			* 2��Map: �ѽ������װ��Map��
	 * 			* 3���ѽ������װ��Object��(������ǵ��е���)
	 */
	
	/*
	 * 1��javaBean: �ѽ������װ��JavaBean��
	 */
	@Test
	public void testQuery1() throws SQLException{
		String sql="select * from emp_tbl where emp_id=?";
		QueryRunner qr=new TxQueryRunner();
		/*
		 * �ڶ�����������ΪResultSetHandler,����һ���ӿڣ���ʾӳ��Ľ������
		 * 
		 * BeanHandler:��ResultSetHandler��ʵ���࣬���������ǰѽ������װ��person������
		 * ע�⣺��ѯ�����������bean�������������ͬ,û��Ĭ��֧���շ���������
		 * 
		 */
		Emp emp=qr.query(sql, new BeanHandler<Emp>(Emp.class),3530);
		System.out.println(emp);
		
	}
	/*
	 *ʹ��BeanListHandler
	 *		�Ѷ��н����ӳ�䵽List<bean>,�����javaBean����
	 *		һ�н������Ӧһ��javaBean����,���ж�ӦList<bean> 
	 *		
	 */
	@Test
	public void testQuery2() throws SQLException{
		String sql="select * from emp_tbl";
		QueryRunner qr=new TxQueryRunner();
		
		List<Emp> emp=qr.query(sql, new BeanListHandler<Emp>(Emp.class));
		System.out.println(emp);
		
	}
	
	/*
	 * 2��Map: �ѽ������װ��Map��
	 * 			MapHandler()
	 */
	@Test
	public void testQuery3() throws SQLException{
		String sql="select * from emp_tbl where emp_id=?";
		QueryRunner qr=new TxQueryRunner();
		
		Map map=qr.query(sql, new MapHandler(),3530);
		System.out.println(map);
		
	}
	
	/*
	 * MapListHandler()
	 */
	@Test
	public void testQuery4() throws SQLException{
		String sql="select * from emp_tbl";
		QueryRunner qr=new TxQueryRunner();
		
		List<Map<String,Object>> map=qr.query(sql, new MapListHandler());
		System.out.println(map);
		
	}
	
	/*
	 * ʹ��ScalarHandler()���ѵ��е��н������װ��Object��
	 * 
	 */
	@Test
	public void testQuery5() throws SQLException{
		String sql="select count(*) from emp_tbl";   //������ǵ��е���
		QueryRunner qr=new TxQueryRunner();
		/*
		 * select count(*) �����������
		 * 	>Integer
		 * 	>Long
		 * 	>BigInteger
		 * 
		 * ��ͬ�����������ͬ
		 * �������������ͣ�����Number���ͣ�ǿת��Numberһ���������	
		 */
		Object obj=qr.query(sql, new ScalarHandler());
		Number num=(Number)obj;
		Integer it=num.intValue();
		System.out.println(it);
	}
	
	/*
	 * һ�н�����а��������ű�
	 * 		��MapHandler()������
	 * 		1���ѽ������װ��map��
	 * 		2��ʹ��map����Emp����
	 * 		3��ʹ��map����Dept����
	 * 		4��������ʵ���������ϵ
	 */
	@Test
	public void testQuery6() throws SQLException{
		String sql="select * from emp_tbl e,dept_tbl d where e.d_id=d.dept_id and e.emp_id=?";
		QueryRunner qr=new TxQueryRunner();
		//1����ȡmap
		Map map=qr.query(sql, new MapHandler(),4);
		//2������Emp
		Emp emp=CommonUtils.toBean(map, Emp.class);
		//3������Dept
		Dept dept=CommonUtils.toBean(map, Dept.class);
		//4������ʵ���������ϵ
		emp.setDept(dept);
		System.out.println(emp);
	}
	
	
	
	
	
	
}
