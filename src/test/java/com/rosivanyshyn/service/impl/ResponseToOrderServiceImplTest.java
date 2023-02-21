package com.rosivanyshyn.service.impl;

import com.rosivanyshyn.db.dao.OrderDAO;
import com.rosivanyshyn.db.dao.ResponseToOrderDAO;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.Order;
import com.rosivanyshyn.db.dao.entity.ResponseToOrder;
import com.rosivanyshyn.db.manager.DBManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import static com.rosivanyshyn.Constant.*;
import static com.rosivanyshyn.db.dao.constant.Field.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResponseToOrderServiceImplTest {
    ResponseToOrderDAO responseToOrderDAO = mock(ResponseToOrderDAO.class);
    OrderDAO orderDAO = mock(OrderDAO.class);
    private final DBManager dbManager = mock(DBManager.class);
    private final Connection connection = mock(Connection.class);
    ResponseToOrderServiceImpl responseToOrderService = new ResponseToOrderServiceImpl(responseToOrderDAO, orderDAO, dbManager);
    //----------------------- Data ---------------------\\
    Account account = Account.builder().id(ID_VALUE_LONG).build();
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
    private Order getTestOrder(){
        return Order.builder()
                .id(ID_VALUE_LONG)
                .guestsNumber(GUESTS_NUMBER_VALUE_INT)
                .roomsNumber(ROOMS_NUMBER_VALUE)
                .apartmentClass(APARTMENT_CLASS_VALUE)
                .price(PRICE_VALUE_LONG)
                .description(DESCRIPTION_VALUE)
                .checkInDate(Date.valueOf(DATE_VALUE))
                .checkOutDate(Date.valueOf(DATE_VALUE2))
                .account(account)
                .build();
    }

    private ResponseToOrder getTestResponseToOrder(){
        return ResponseToOrder.builder()
                .id(ID_VALUE_LONG)
                .description(DESCRIPTION_VALUE)
                .build();
    }
    //-------------------------------------------------\\

    @BeforeEach
    void getConnection(){
        when( dbManager.getConnection()).thenReturn(connection);
    }

    @Test
    void createResponseToOrder() {
        Order order = getTestOrder();

        when(responseToOrderDAO.insert(isA(Connection.class), isA(ResponseToOrder.class))).thenReturn(true);
        when(orderDAO.update(isA(Connection.class), isA(Order.class))).thenReturn(true);
        when(responseToOrderDAO.setApartmentToResponse(
                isA(Connection.class), isA(ResponseToOrder.class), isA(Apartment.class))
        ).thenReturn(true);

        assertTrue( responseToOrderService.createResponseToOrder(order, getTestResponseToOrder(), apartments) );

        assertNotNull(order.getResponseToOrder());
    }

    @Test
    void findResponseToOrderByField() {
        when(responseToOrderDAO.getByField(isA(Connection.class), anyString(), isA(Object.class))).thenReturn(getTestResponseToOrder());
        assertEquals(getTestResponseToOrder(),
                responseToOrderService.findResponseToOrderByField(ID_FIELD, ID_VALUE)
        );
    }

    @Test
    void updateResponseToOrder() {
        responseToOrderService.updateResponseToOrder(getTestResponseToOrder());
    }

    @Test
    void findAllResponseApartments() {
        when(responseToOrderDAO.getResponseApartments(isA(Connection.class), isA(Long.class))).thenReturn(apartments);

        assertEquals(apartments,
                responseToOrderService.findAllResponseApartments(getTestResponseToOrder())
        );
    }
}