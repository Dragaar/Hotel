package com.rosivanyshyn.database.dao;

import com.rosivanyshyn.db.dao.GenericDAO;
import com.rosivanyshyn.db.dao.constant.Field;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.entity.Order;
import com.rosivanyshyn.db.dao.implMySQL.OrderDAOImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static com.rosivanyshyn.Constant.DATE_VALUE;
import static com.rosivanyshyn.Constant.DATE_VALUE2;
import static com.rosivanyshyn.db.dao.constant.Field.ENTITY_ID;

/**
 * Order DAO Integration Test
 */
@Tag("IntegrationTest")
public class OrderDAOIT extends GenericDAOIT<Order> {
    Order order;
    @Override
    protected GenericDAO<Order> setDAO() { return new OrderDAOImpl();}
    @Override
    protected Order setEntity() { return order; }
    @Override
    protected Long getEntityId(Order entity) { return entity.getId();}

    //--------------Foreign keys temporary data----------------------\\
    AccountDAOIT accountInitializer = new AccountDAOIT();
    Account account;

    @BeforeEach
    void initialiseForeignKeys(){
        //don`t clean BD after calling each method
        accountInitializer.cleanDB = false;

        accountInitializer.insertTestLogic(accountInitializer.insertEntity);

        account = accountInitializer.entity;
    }
    @AfterEach
    void destroyForeignKeys(){
        accountInitializer.deleteTestLogic(accountInitializer.entity.getId());
    }
    //--------------------------------------------------------------\\

    protected BuildEntity<Order> insertEntity = ()-> order = Order.builder()
            .id(0L)
            .guestsNumber(4)
            .roomsNumber("2")
            .apartmentClass("B")
            .price(250L)
            .description("best apartment")
            .checkInDate(
                    Date.valueOf(DATE_VALUE)
            )
            .checkOutDate(
                    Date.valueOf(DATE_VALUE2)
            )
            .account(account)
            .build();

    protected BuildEntity<Order> updateEntity = ()-> Order.builder()
            .id(order.getId())
            .guestsNumber(6)
            .roomsNumber("3")
            .apartmentClass("C")
            .price(500L)
            .description("best apartment 2")
            .checkInDate(order.getCheckInDate())
            .checkOutDate(order.getCheckOutDate())
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
            insertTestLogic(insertEntity);
            secondEntityID = entity.getId();
            insertTestLogic(insertEntity);
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
        getByFieldTestLogic(ENTITY_ID, entity.getId());
    }
    @Test
    void deleteTest(){
        insertTestLogic(insertEntity);
        deleteTestLogic(order.getId());
    }
}
