package my.jdbc.service;


import my.jdbc.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Tomcat on 2017/9/14.
 */
@Component("accountservice")
@Transactional
public class AccountService {
    @Autowired
    AccountDao accountDao;

    public void transactionAccount(){
        accountDao.updateAccount("0000000001",-1000d);

        accountDao.updateAccount("0000000002",1000d);
    }

}
