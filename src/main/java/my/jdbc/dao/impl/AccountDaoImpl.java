package my.jdbc.dao.impl;

import my.jdbc.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Tomcat on 2017/9/19.
 */
@Component
public class AccountDaoImpl implements AccountDao{
    @Autowired
    NamedParameterJdbcTemplate template;

    @Override
    public int updateAccount(String number, double money) {
        return template.getJdbcOperations().update("UPDATE back_account SET money=money+? WHERE number=?",money,number);
    }
}
