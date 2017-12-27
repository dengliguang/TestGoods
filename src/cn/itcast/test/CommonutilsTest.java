package cn.itcast.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import bean.Person;
import cn.itcast.commons.CommonUtils;

public class CommonutilsTest {

	@Test
	public void JunTest() {
		/*
		 * 返回一个随机的32位的字符串
		 * 
		 */
		String s=CommonUtils.uuid();
		System.out.println(s);
	}

	/*
	 * 把map集合转成bean对象：底层是beanutils 
	 */
	@Test
	public void toBean(){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name", "张三");
		map.put("age", 25);
		map.put("sex", "男");
		//map.put("phone", "187748144");
		
		Person p=CommonUtils.toBean(map, Person.class);
		System.out.println(p);
	}

}
