package database.dao;

import com.rosivanyshyn.db.dao.GenericDAO;
import com.rosivanyshyn.db.dao.constant.Field;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.implMySQL.ApartmentDAOImpl;
import org.junit.jupiter.api.Test;

public class ApartmentDAOTest extends GenericDAOTest<Apartment>{
    Apartment apartment;
    @Override
    protected GenericDAO<Apartment> setDAO() { return new ApartmentDAOImpl();}
    @Override
    protected Apartment setEntity() { return apartment; }
    @Override
    protected Long getEntityId(Apartment entity) { return entity.getId();}

    protected BuildEntity<Apartment> insertEntity = ()-> apartment = Apartment.builder()
            .id(0L)
            .maxGuestsNumber("4")
            .roomsNumber("2")
            .apartmentClass("B")
            .price(2000L)
            .state(true)
            .build();

    protected BuildEntity<Apartment> updateEntity = ()-> Apartment.builder()
            .id(apartment.getId())
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

            getFewTestLogic();
        } finally {
            deleteTestLogic(firstEntityID);
            deleteTestLogic(secondEntityID);
            deleteTestLogic(ThirdEntityID);
        }
    }
    @Test
    void getByFieldTest(){
        insertTestLogic(insertEntity);
        getByFieldTestLogic(Field.APARTMENT_PRICE, 2000L);
    }
    @Test
    void deleteTest(){
        insertTestLogic(insertEntity);
        deleteTestLogic(apartment.getId());
    }
}
