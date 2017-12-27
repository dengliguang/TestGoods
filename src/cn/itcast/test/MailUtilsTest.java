package cn.itcast.test;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Session;

import org.junit.Test;

import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
/*
 * ����MailUtils�����÷��ʼ�
 * 		�ײ�����javamail :���� mail.jar��activation.jar
 * 
 */
public class MailUtilsTest {
	
	/*
	 * 1����¼�ʼ�������
	 * 		MailUtils.createSession
	 * 2�������ʼ�����
	 * 		������
	 * 		�ռ���
	 * 		����
	 * 		����
	 * 3������
	 * 		��Ҫ��һ����session���ڶ������ʼ�����
	 * 
	 * ע�ⷢ�͵��ǣ�163��162������,qq�������в�ͬ
	 */
	@Test
	public void test() throws MessagingException, IOException {
		Session session=MailUtils.createSession("smtp.163.com", "itcast_cxf", "sdds312");
		Mail mail=new Mail("itcast_cxf@163.com","itcast_cxf@163.com","�����ʼ�һ��","<a href='http://www.baidu.com'>�ٶ�</a>");	 	
		MailUtils.send(session, mail);	
	}

}
