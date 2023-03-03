package com.rosivanyshyn.database.dao;

import com.rosivanyshyn.db.dao.GenericDAO;

import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.Booking;

import com.rosivanyshyn.db.dao.implMySQL.BookingDAOImpl;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.time.LocalDate;

import static com.rosivanyshyn.Constant.*;
import static com.rosivanyshyn.db.dao.constant.Field.*;

/**
 * Booking DAO Integration Test
 */
@Tag("IntegrationTest")
public class BookingDAOIT extends GenericDAOIT<Booking> {
    Booking booking;
    @Override
    protected GenericDAO<Booking> setDAO() { return new BookingDAOImpl();}
    @Override
    protected Booking setEntity() { return booking; }
    @Override
    protected Long getEntityId(Booking entity) { return entity.getId();}
    //--------------Foreign keys temporary data----------------------\\
    AccountDAOIT accountInitializer = new AccountDAOIT();
    ApartmentDAOIT apartmentInitializer= new ApartmentDAOIT();
    Account account;
    Apartment apartment;
    @BeforeEach
    void initialiseForeignKeys(){
        //don`t clean BD after calling each method
        accountInitializer.cleanDB = false;
        apartmentInitializer.cleanDB = false;

        accountInitializer.insertTestLogic(accountInitializer.insertEntity);
        apartmentInitializer.insertTestLogic(apartmentInitializer.insertEntity);

        account = accountInitializer.entity;
        apartment =  apartmentInitializer.entity;
    }
    @AfterEach
    void destroyForeignKeys(){
        accountInitializer.deleteTestLogic(accountInitializer.entity.getId());
        apartmentInitializer.deleteTestLogic(apartmentInitializer.entity.getId());
    }
    //--------------------------------------------------------------\\

    BuildEntity<Booking> insertEntity = ()-> booking = Booking.builder()
            .id(0L)
            .guestsNumber("4")
            .checkInDate(
                    Date.valueOf(DATE_VALUE)
            )
            .checkOutDate(
                    Date.valueOf(DATE_VALUE2)
            )
            .isPaidForReservation(false)
            .account(account)
            .apartment(apartment)
            .build();

    BuildEntity<Booking> updateEntity = ()-> Booking.builder()
            .id(booking.getId())
            .guestsNumber(booking.getGuestsNumber())
            .checkOutDate(
                    booking.getCheckOutDate()
            )
            .checkInDate(booking.getCheckInDate())
            .isPaidForReservation(true)
            .account(booking.getAccount())
            .apartment(booking.getApartment())
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
        deleteTestLogic(booking.getId());
    }
}
