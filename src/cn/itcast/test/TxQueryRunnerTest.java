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
 * TxQueryRunner:他是queryRunner的子类！(commons-dbutils.jar)
 * 		用起来与queryRunner相似
 * 		可以使用事物，底层用JDBCUtils来获取链接
 * 
 * QueryRunner的三个方法：
	 * 		*  update() ---> insert、update、delete
	 * 		*  query()	---> select
	 * 		*  batch() 	---> 批量处理
 */
public class TxQueryRunnerTest {
	/*
	 * 测试：update()方法的：insert、update、delete语句
	 * 
	 */
	@Test
	public void testUpdateInsert() throws SQLException {
		String sql="insert into emp_tbl(emp_name,gender,phone,d_id) values(?,?,?,?)";
		Object[] params={"灵羽yu","男","18774814558",3};   
		
		QueryRunner qr=new TxQueryRunner();		//内部会提供JDBCUtils连接数据库
		int b=qr.update(sql,params);
		System.out.println(b);
	}
	
	/*
	 * 添加事务
	 */
	@Test
	public void testUpdate() throws Exception {
		try {
			JdbcUtils.beginTransaction();	//开启事物
			
			String sql="insert into emp_tbl(emp_name,gender,phone,d_id) values(?,?,?,?)";
			QueryRunner qr=new TxQueryRunner();		//内部会提供JDBCUtils连接数据库
			
			Object[] params={"灵羽yu","男","18774814558",3};  
			int b=qr.update(sql,params);
			System.out.println(b);
			
			if(true){
				throw new Exception();
			}
			
			params=new Object[]{"灵羽yu","男","18774814558",3};
			int i=qr.update(sql,params);
			System.out.println(i);
			
			JdbcUtils.commitTransaction();	//提交事物
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();	//回滚事物
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw e;
		}
	}
	
	/*
	 * 测试查询：
	 * 		jdbc查询的结果是：ResultSet
	 * 		QueryRunner查询的结果是通过ResultSet映射后的数据
	 * 			* 第一步:通过QueryRunner执行select，得到ResultSet
	 * 			* 把ResultSet转换成其它类型
	 * 		转换结果：
	 * 			* 1、javaBean: 把结果集封装到JavaBean中
	 * 			* 2、Map: 把结果集封装到Map中
	 * 			* 3、把结果集封装到Object中(结果集是单行单列)
	 */
	
	/*
	 * 1、javaBean: 把结果集封装到JavaBean中
	 */
	@Test
	public void testQuery1() throws SQLException{
		String sql="select * from emp_tbl where emp_id=?";
		QueryRunner qr=new TxQueryRunner();
		/*
		 * 第二个参数类型为ResultSetHandler,他是一个接口，表示映射的结果类型
		 * 
		 * BeanHandler:是ResultSetHandler的实现类，他的作用是把结果集封装到person对象中
		 * 注意：查询结果集必须与bean对象的属性名相同,没有默认支持驼峰命名规则
		 * 
		 */
		Emp emp=qr.query(sql, new BeanHandler<Emp>(Emp.class),3530);
		System.out.println(emp);
		
	}
	/*
	 *使用BeanListHandler
	 *		把多行结果集映射到List<bean>,即多个javaBean对象
	 *		一行结果集对应一个javaBean对象,多行对应List<bean> 
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
	 * 2、Map: 把结果集封装到Map中
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
	 * 使用ScalarHandler()，把单行单列结果集封装到Object中
	 * 
	 */
	@Test
	public void testQuery5() throws SQLException{
		String sql="select count(*) from emp_tbl";   //结果集是单行单列
		QueryRunner qr=new TxQueryRunner();
		/*
		 * select count(*) 结果集是整数
		 * 	>Integer
		 * 	>Long
		 * 	>BigInteger
		 * 
		 * 不同驱动，结果不同
		 * 无论是哪种类型，都是Number类型，强转成Number一定不会出错	
		 */
		Object obj=qr.query(sql, new ScalarHandler());
		Number num=(Number)obj;
		Integer it=num.intValue();
		System.out.println(it);
	}
	
	/*
	 * 一行结果集中包含了两张表
	 * 		用MapHandler()来处理
	 * 		1、把结果集封装到map中
	 * 		2、使用map生成Emp对象
	 * 		3、使用map生成Dept对象
	 * 		4、把两个实体对象建立关系
	 */
	@Test
	public void testQuery6() throws SQLException{
		String sql="select * from emp_tbl e,dept_tbl d where e.d_id=d.dept_id and e.emp_id=?";
		QueryRunner qr=new TxQueryRunner();
		//1、获取map
		Map map=qr.query(sql, new MapHandler(),4);
		//2、生成Emp
		Emp emp=CommonUtils.toBean(map, Emp.class);
		//3、生成Dept
		Dept dept=CommonUtils.toBean(map, Dept.class);
		//4、两个实体对象建立关系
		emp.setDept(dept);
		System.out.println(emp);
	}
	
	
	
	
	
	
}
