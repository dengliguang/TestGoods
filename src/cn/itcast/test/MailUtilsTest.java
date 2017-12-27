package cn.itcast.test;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Session;

import org.junit.Test;

import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
/*
 * 测试MailUtils：作用发邮件
 * 		底层依赖javamail :依赖 mail.jar、activation.jar
 * 
 */
public class MailUtilsTest {
	
	/*
	 * 1、登录邮件服务器
	 * 		MailUtils.createSession
	 * 2、创建邮件对象
	 * 		发件人
	 * 		收件人
	 * 		主题
	 * 		正文
	 * 3、发送
	 * 		需要第一步的session、第二步的邮件对象
	 * 
	 * 注意发送的是：163、162的邮箱,qq邮箱略有不同
	 */
	@Test
	public void test() throws MessagingException, IOException {
		Session session=MailUtils.createSession("smtp.163.com", "itcast_cxf", "sdds312");
		Mail mail=new Mail("itcast_cxf@163.com","itcast_cxf@163.com","测试邮件一封","<a href='http://www.baidu.com'>百度</a>");	 	
		MailUtils.send(session, mail);	
	}

}
