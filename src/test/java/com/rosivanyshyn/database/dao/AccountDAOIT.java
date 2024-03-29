package com.rosivanyshyn.database.dao;

import com.rosivanyshyn.db.dao.GenericDAO;
import com.rosivanyshyn.db.dao.constant.Field;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.implMySQL.AccountDAOImpl;


import org.junit.jupiter.api.*;

/**
 * Account DAO Integration Test
 */
@Tag("IntegrationTest")
public class AccountDAOIT extends GenericDAOIT<Account> {

    Account account;
    @Override
    protected GenericDAO<Account> setDAO() { return new AccountDAOImpl();}
    @Override
    protected Account setEntity() { return account; }
    @Override
    protected Long getEntityId(Account entity) { return entity.getId();}

    protected BuildEntity<Account> insertEntity = ()-> account = Account.builder()
            .id(0L)
            .role("user")
            .firstName("Oleg")
            .lastName("Shevchuk")
            .email("oleg_shevchuk@gmail.com")
            .password("fjtn285ty2")
            .state(true)
            .build();

    protected BuildEntity<Account> insertEntity2 = ()-> {
        account.setEmail("oleg_shevchuk2@gmail.com");
        return account;
    };
    protected BuildEntity<Account> insertEntity3 = ()-> {
        account.setEmail("oleg_shevchuk3@gmail.com");
        return account;
    };

    protected BuildEntity<Account> updateEntity = ()-> Account.builder()
            .id(account.getId())
            .role(account.getRole())
            .firstName(account.getFirstName())
            .lastName(account.getLastName())
            .email("shevchuk_oleg5223@gmail.com")
            .password(account.getPassword())
            .state(account.getState())
            .build();

    @Test
    void insertTest(){
        insertTestLogic(insertEntity);
    }
    @Test
    void getTest(){
        insertTestLogic(insertEntity);
        getTestLogic();
    }

    @Test
    void updateTest(){
        insertTestLogic(insertEntity);
        updateTestLogic(updateEntity);
    }

    @Test
    void getAllTest(){
        insertTestLogic(insertEntity);
        getAllTestLogic();
    }
    @Test
    void getFewTest(){
        Long firstEntityID = 0L, secondEntityID = 0L, ThirdEntityID = 0L;

        try {
            insertTestLogic(insertEntity);
            firstEntityID = entity.getId();
            insertTestLogic(insertEntity2);
            secondEntityID = entity.getId();
            insertTestLogic(insertEntity3);
            ThirdEntityID = entity.getId();

            getFewTestLogic(1, 2);
        } finally {
            deleteTestLogic(firstEntityID);
            deleteTestLogic(secondEntityID);
            deleteTestLogic(ThirdEntityID);
        }
    }
    @Test
    void getByFieldTest(){
        insertTestLogic(insertEntity);
        getByFieldTestLogic(Field.ENTITY_ID, entity.getId());
    }
    @Test
    void deleteTest(){
        insertTestLogic(insertEntity);
        deleteTestLogic(account.getId());
    }
}
