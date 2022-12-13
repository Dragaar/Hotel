package com.rosivanyshyn.service;

import com.rosivanyshyn.db.dao.entity.Account;

import java.sql.Connection;

public interface AccountService {

    Boolean         createAccount(Account account);

    Boolean         isAccountExist(Account account);
    Account         findAccountByField(String field, Object value);


    Boolean         updateAccount(Account account);
    Boolean         deleteAccount(Account account);
}
