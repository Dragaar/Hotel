package database.dao;

import com.rosivanyshyn.db.dao.GenericDAO;

import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.ResponseToOrder;

import com.rosivanyshyn.db.dao.implMySQL.ResponseToOrderDAOImpl;
import com.rosivanyshyn.db.manager.DBManager;
import com.rosivanyshyn.db.transaction.TransactionManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResponseToOrderDAOTest extends GenericDAOTest<ResponseToOrder>{
    ResponseToOrder responseToOrder;
    ResponseToOrderDAOImpl rtoDAO = new ResponseToOrderDAOImpl();
    @Override
    protected GenericDAO<ResponseToOrder> setDAO() { return new ResponseToOrderDAOImpl();}
    @Override
    protected ResponseToOrder setEntity() { return responseToOrder; }
    @Override
    protected Long getEntityId(ResponseToOrder entity) { return entity.getId();}

    //--------------Many to Many Apartment temporary data----------------------\\

    ArrayList<Apartment> apartments = new ArrayList<>();
    ArrayList<Apartment> dataBaseApartments = new ArrayList<>();
    ApartmentDAOTest apartmentInitializer = new ApartmentDAOTest();

    @BeforeEach
    void initialiseForeignKeys(){
        //don`t clean BD after calling each method
        apartmentInitializer.cleanDB = false;

        apartmentInitializer.insertTestLogic(apartmentInitializer.insertEntity);

        apartments.add(apartmentInitializer.entity);
    }
    @AfterEach
    void destroyForeignKeys(){
        apartmentInitializer.deleteTestLogic(apartmentInitializer.entity.getId());
    }
    //----------------------------------------------------------------------\\

    protected BuildEntity<ResponseToOrder> insertEntity = ()-> responseToOrder = ResponseToOrder.builder()
            .id(0L)
            .description("Highly recommend you pay attention in this apartments")
            .build();

    protected BuildEntity<ResponseToOrder> updateEntity = ()-> responseToOrder = ResponseToOrder.builder()
            .id(responseToOrder.getId())
            .description("some changes in description")
            .build();

    @Test
    void GeneralTest(){
        try{
        insertTestLogic(insertEntity);
        insertRTOApartments();

        getTestLogic();
        getRTOApartments();
        updateTestLogic(updateEntity);
        } finally {
            deleteRTOApartments();
            deleteTestLogic(responseToOrder.getId());
        }
    }

    // -------------- Many to Many extra queries ---------- \\
    protected void insertRTOApartments(){
        connection = DBManager.getConnection();

        boolean result = (Boolean) TransactionManager.execute(connection,
                ()-> rtoDAO.setApartmentToResponse(connection, responseToOrder, apartments.get(0))
        );

        assertTrue(result);
    }

    /** Logic for get test */
    protected void getRTOApartments() {
        connection = DBManager.getConnection();


        dataBaseApartments = (
                (ArrayList<Apartment>) TransactionManager.execute(connection,
                ()-> rtoDAO.getResponseApartments(connection, responseToOrder.getId()))
        );

        assertThat(apartments, is(dataBaseApartments));
    }

    protected void deleteRTOApartments(){
        connection = DBManager.getConnection();
        boolean result = (Boolean) TransactionManager.execute(connection,
                ()-> rtoDAO.deleteResponseApartments(connection, responseToOrder.getId())
        );
        assertTrue(result);
    }
}

