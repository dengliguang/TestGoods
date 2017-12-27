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
		 * ����һ�������32λ���ַ���
		 * 
		 */
		String s=CommonUtils.uuid();
		System.out.println(s);
	}

	/*
	 * ��map����ת��bean���󣺵ײ���beanutils 
	 */
	@Test
	public void toBean(){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name", "����");
		map.put("age", 25);
		map.put("sex", "��");
		//map.put("phone", "187748144");
		
		Person p=CommonUtils.toBean(map, Person.class);
		System.out.println(p);
	}

}
