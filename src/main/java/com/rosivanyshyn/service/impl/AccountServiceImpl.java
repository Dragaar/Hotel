package com.rosivanyshyn.service.impl;

import com.rosivanyshyn.db.dao.AccountDAO;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.manager.DBManager;
import com.rosivanyshyn.db.transaction.TransactionManager;
import com.rosivanyshyn.service.AccountService;

import java.sql.Connection;

import static com.rosivanyshyn.db.dao.constant.Field.*;

public class AccountServiceImpl implements AccountService {
    AccountDAO accountDAO;
    DBManager dbManager;

    public AccountServiceImpl(AccountDAO accountDAO,   DBManager dbManager) {
        this.accountDAO = accountDAO;
        this.dbManager = dbManager;
    }

    @Override
    public Boolean createAccount(Account account) {
        Connection connection = dbManager.getConnection();

        return  (Boolean) TransactionManager.execute(connection,
                ()-> accountDAO.insert(connection, account)
        );
    }

    @Override
    public Boolean isAccountExist(Account account) {
        Connection connection = dbManager.getConnection();

        Account dbAccount = (Account) TransactionManager.execute(connection,
                ()-> accountDAO.getByField(connection, ENTITY_ID, account.getId())
        );
        return dbAccount.getId().equals(account.getId());
    }

    @Override
    public Account findAccountByField(String field, Object value) {
        Connection connection = dbManager.getConnection();

        return  (Account) TransactionManager.execute(connection,
                ()-> accountDAO.getByField(connection, field, value)
        );
    }

    @Override
    public Boolean updateAccount(Account account) {
        Connection connection = dbManager.getConnection();

        return  (Boolean) TransactionManager.execute(connection,
                ()-> accountDAO.update(connection, account));
    }

    @Override
    public Boolean deleteAccount(Account account) {
        Connection connection = dbManager.getConnection();
        return (Boolean) TransactionManager.execute(connection,
                ()-> accountDAO.delete(connection, account.getId())
        );
    }
}
