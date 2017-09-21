package my.jdbc.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Tomcat on 2017/9/19.
 */
public class TransactionTest {
    AccountService accountService;
    @Before
    public void setUp(){
        ApplicationContext context=new ClassPathXmlApplicationContext("AppCon.xml");
        accountService= (AccountService) context.getBean("accountservice");
    }
    @Test
    public void test(){
        accountService.transactionAccount();
    }
}
