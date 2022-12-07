package database.dao;

import com.rosivanyshyn.db.dao.GenericDAO;

import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.Booking;

import com.rosivanyshyn.db.dao.implMySQL.BookingDAOImpl;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.time.LocalDate;

import static com.rosivanyshyn.db.dao.constant.Field.*;

public class BookingDAOTest extends GenericDAOTest<Booking>{
    Booking booking;
    @Override
    protected GenericDAO<Booking> setDAO() { return new BookingDAOImpl();}
    @Override
    protected Booking setEntity() { return booking; }
    @Override
    protected Long getEntityId(Booking entity) { return entity.getId();}
    //--------------Foreign keys temporary data----------------------\\
    AccountDAOTest accountInitializer = new AccountDAOTest();
    ApartmentDAOTest apartmentInitializer= new ApartmentDAOTest();
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
        accountInitializer.deleteTestLogic();
        apartmentInitializer.deleteTestLogic();
    }
    //--------------------------------------------------------------\\

    BuildEntity<Booking> insertEntity = ()-> booking = Booking.builder()
            .id(0L)
            .guestsNumber("4")
            .checkInDate(
                    Date.valueOf(LocalDate.of(2023, 01, 10))
            )
            .checkOutDate(
                    Date.valueOf(LocalDate.of(2023, 01, 20))
            )
            .isPaidForReservation(false)
            .account(account)
            .apartment(apartment)
            .build();

    BuildEntity<Booking> updateEntity = ()-> Booking.builder()
            .id(booking.getId())
            .guestsNumber(booking.getGuestsNumber())
            .checkOutDate(
                    Date.valueOf(LocalDate.of(2023, 01, 5))
            )
            .checkInDate(booking.getCheckOutDate())
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
    void getByFieldTest(){
        insertTestLogic(insertEntity);
        getByFieldTestLogic(BOOKING_CHECK_IN_DATE, Date.valueOf(LocalDate.of(2023, 01, 10)));
    }
    @Test
    void deleteTest(){
        insertTestLogic(insertEntity);
        deleteTestLogic();
    }
}
