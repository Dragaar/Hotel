package com.rosivanyshyn.service.impl;

import com.rosivanyshyn.db.dao.AccountDAO;
import com.rosivanyshyn.db.dao.constant.AccountRole;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.manager.DBManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static com.rosivanyshyn.Constant.*;
import static com.rosivanyshyn.db.dao.constant.Field.*;
import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceImplTest {
    private final AccountDAO accountDAO = mock(AccountDAO.class);
    private final DBManager dbManager = mock(DBManager.class);
    private final Connection connection = mock(Connection.class);

    private final AccountServiceImpl accountService = new AccountServiceImpl(accountDAO, dbManager);

    @BeforeEach
    void getConnection(){
        when( dbManager.getConnection()).thenReturn(connection);
    }

    private Account getTestAccount(){
        return Account.builder()
                .id(ID_VALUE_LONG)
                .email(EMAIL_VALUE)
                .password(PASSWORD_VALUE)
                .role(AccountRole.USER.getRole())
                .firstName(FIRST_NAME_VALUE)
                .lastName(LAST_NAME_VALUE)
                .build();
    }

    @Test
    void createAccount() {
        when(accountDAO.insert(isA(Connection.class), isA(Account.class))).thenReturn(true);
        assertTrue( accountService.createAccount(getTestAccount()) );
    }

    @Test
    void isAccountExist() {
        Account account = getTestAccount();
        when(accountDAO.getByField(isA(Connection.class), anyString(), isA(Long.class))).thenReturn(account);
        assertTrue( accountService.isAccountExist(getTestAccount()) );

        account.setId(account.getId()+1L);
        assertFalse( accountService.isAccountExist(getTestAccount()) );
    }

    @Test
    void findAccountByField() {
        when(accountDAO.getByField(isA(Connection.class), anyString(), isA(Object.class))).thenReturn(getTestAccount());
        assertEquals(getTestAccount(),
                accountService.findAccountByField(ACCOUNT_EMAIL, EMAIL_VALUE)
        );
    }

    @Test
    void updateAccount() {
        when(accountDAO.update(isA(Connection.class), isA(Account.class))).thenReturn(true);
        assertTrue( accountService.updateAccount(getTestAccount()) );
    }

    @Test
    void deleteAccount() {
        when(accountDAO.delete(isA(Connection.class), isA(Long.class))).thenReturn(true);
        assertTrue( accountService.deleteAccount(getTestAccount()) );
    }
}