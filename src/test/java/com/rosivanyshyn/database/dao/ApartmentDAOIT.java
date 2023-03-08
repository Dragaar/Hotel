package com.rosivanyshyn.database.dao;

import com.rosivanyshyn.db.dao.ApartmentDAO;
import com.rosivanyshyn.db.dao.GenericDAO;
import com.rosivanyshyn.db.dao.constant.Field;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.implMySQL.ApartmentDAOImpl;
import com.rosivanyshyn.db.manager.MySQLDBManagerImpl;
import com.rosivanyshyn.db.transaction.TransactionManager;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Apartment DAO Integration Test
 */
@Tag("IntegrationTest")
public class ApartmentDAOIT extends GenericDAOIT<Apartment> {
    Apartment apartment;
    @Override
    protected GenericDAO<Apartment> setDAO() { return new ApartmentDAOImpl();}
    @Override
    protected Apartment setEntity() { return apartment; }
    @Override
    protected Long getEntityId(Apartment entity) { return entity.getId();}

    protected BuildEntity<Apartment> insertEntity = ()-> apartment = Apartment.builder()
            .id(0L)
            .title("Тестовий хостел")
            .description("some description")
            .imageURL("new image")
            .address("вулиця Прорізна, 14, Київ, 01034")
            .maxGuestsNumber("4")
            .roomsNumber("2")
            .apartmentClass("B")
            .price(2000L)
            .state(true)
            .build();

    protected BuildEntity<Apartment> updateEntity = ()-> Apartment.builder()
            .id(apartment.getId())
            .title(apartment.getTitle())
            .description("new description")
            .imageURL("new image")
            .address(apartment.getAddress())
            .maxGuestsNumber(apartment.getMaxGuestsNumber())
            .roomsNumber(apartment.getRoomsNumber())
            .apartmentClass(apartment.getApartmentClass())
            .price(3100L)
            .state(true)
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
        getByFieldTestLogic(Field.ENTITY_ID, entity.getId());
    }
    @Test
    void deleteTest(){
        insertTestLogic(insertEntity);
        deleteTestLogic(apartment.getId());
    }

    // -------------- Unique queries ---------- \\

    @Test
    void searchApartmentsTest(){
        ApartmentDAO apartmentDAO = new ApartmentDAOImpl();

            insertTestLogic(insertEntity);

            String searchValue = "Тестовий";
            connection = MySQLDBManagerImpl.getInstance().getConnection();
            @SuppressWarnings("unchecked")
            ArrayList<Apartment> result = (ArrayList<Apartment>) TransactionManager.execute(connection,
                    ()-> apartmentDAO.searchApartments(connection, searchValue, 0,1)
            );

            deleteTestLogic(apartment.getId());

        assertEquals(getEntityId(entity), getEntityId(result.get(0)));
    }
}
