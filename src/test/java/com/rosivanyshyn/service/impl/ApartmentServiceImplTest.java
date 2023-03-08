package com.rosivanyshyn.service.impl;

import com.rosivanyshyn.db.dao.ApartmentDAO;

import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.manager.DBManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static com.rosivanyshyn.db.dao.constant.Field.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static com.rosivanyshyn.Constant.*;
class ApartmentServiceImplTest {
    ApartmentDAO apartmentDAO = mock(ApartmentDAO.class);
    private final DBManager dbManager = mock(DBManager.class);
    private final Connection connection = mock(Connection.class);

    ApartmentServiceImpl apartmentService = new ApartmentServiceImpl(apartmentDAO, dbManager);

    ArrayList<Apartment> apartments = new ArrayList<>();

    { apartments.add(getTestApartment());}

    private Apartment getTestApartment(){
        return Apartment.builder()
                .id(ID_VALUE_LONG)
                .title(TITLE_VALUE)
                .description(DESCRIPTION_VALUE)
                .imageURL(IMAGE_URL_VALUE)
                .address(ADDRESS_VALUE)
                .maxGuestsNumber(MAX_GUESTS_NUMBER_VALUE)
                .roomsNumber(ROOMS_NUMBER_VALUE)
                .apartmentClass(APARTMENT_CLASS_VALUE)
                .price(PRICE_VALUE_LONG)
                .build();
    }

    @BeforeEach
    void getConnection(){
        when( dbManager.getConnection()).thenReturn(connection);
    }

    @Test
    void createApartment() {
        apartmentService.createApartment(getTestApartment());
    }

    @Test
    void findApartmentByField() {
        when(apartmentDAO.getByField(isA(Connection.class), anyString(), isA(Object.class))).thenReturn(getTestApartment());
        assertEquals(getTestApartment(),
                apartmentService.findApartmentByField(APARTMENT_DESCRIPTION, DESCRIPTION_VALUE)
        );
    }

    @Test
    void findFewApartment() {
        when(apartmentDAO.getFew(isA(Connection.class), isA(int.class), isA(int.class))).thenReturn(apartments);
        when(apartmentDAO.countRowsInLastQuery(isA(Connection.class))).thenReturn(1);

        assertEquals(apartments,
                apartmentService.findFewApartment(1, 1)
        );
        assertEquals(1, apartmentService.getRowsNumber());

    }

    @Test
    void findFewApartmentsAndSort() {
        when(apartmentDAO.getWithDynamicQuery(isA(Connection.class), isA(String.class))).thenReturn(apartments);
        when(apartmentDAO.countRowsInLastQuery(isA(Connection.class))).thenReturn(1);

        assertEquals(apartments,
                apartmentService.findFewApartmentsAndSort("query")
        );
        assertEquals(1, apartmentService.getRowsNumber());
    }

    @Test
    void findFewApartmentsWhichAreBooked() {
        when(apartmentDAO.getUniqueApartmentsWhichAreBookedWithDynamicQuery(isA(Connection.class), isA(String.class))).thenReturn(apartments);
        when(apartmentDAO.countRowsInLastQuery(isA(Connection.class))).thenReturn(1);

        assertEquals(apartments,
                apartmentService.findFewApartmentsWhichAreBooked("query")
        );
        assertEquals(1, apartmentService.getRowsNumber());
    }

    @Test
    void searchApartment(){
        when(apartmentDAO.searchApartments(isA(Connection.class), isA(String.class), isA(int.class), isA(int.class))).thenReturn(apartments);
        when(apartmentDAO.countRowsInLastQuery(isA(Connection.class))).thenReturn(1);

        assertEquals(apartments,
                apartmentService.searchApartment("query", 1, 1)
        );

        assertEquals(1, apartmentService.getRowsNumber());
    }

    @Test
    void updateApartment() {
        apartmentService.updateApartment(getTestApartment());
    }

    @Test
    void deleteApartment() {
        when(apartmentDAO.delete(isA(Connection.class), isA(Long.class))).thenReturn(true);
        assertTrue(apartmentService.deleteApartment(getTestApartment()));
    }
}