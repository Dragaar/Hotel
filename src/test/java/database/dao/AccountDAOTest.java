package database.dao;

import com.rosivanyshyn.db.dao.GenericDAO;
import com.rosivanyshyn.db.dao.constant.Field;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.implMySQL.AccountDAOImpl;


import org.junit.jupiter.api.*;


public class AccountDAOTest extends GenericDAOTest<Account> {

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
    void getByFieldTest(){
        insertTestLogic(insertEntity);
        getByFieldTestLogic(Field.ACCOUNT_FIRST_NAME, "Oleg");
    }
    @Test
    void deleteTest(){
        insertTestLogic(insertEntity);
        deleteTestLogic();
    }
}
