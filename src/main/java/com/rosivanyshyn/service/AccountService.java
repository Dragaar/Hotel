package com.rosivanyshyn.service;

import com.rosivanyshyn.db.dao.entity.Account;

import java.sql.Connection;

/**
 * Account Service interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
public interface AccountService {

    /**
     * Create new account
     * @param account the account to be added
     * @return operation result
     */
    Boolean         createAccount(Account account);

    /**
     * Check if account exist
     * @param account the account to be checked
     * @return operation result
     */
    Boolean         isAccountExist(Account account);

    /**
     * Find account by field
     * @param field field name
     * @param value unique field value
     * @return found account
     */
    Account         findAccountByField(String field, Object value);

    /**
     * Update account
     * @param account account to update
     * @return operation result
     */
    Boolean         updateAccount(Account account);
    /**
     * Delete account
     * @param account account to delete
     * @return operation result
     */
    Boolean         deleteAccount(Account account);
}
