package com.rosivanyshyn.service.impl;

import com.rosivanyshyn.db.dao.OrderDAO;
import com.rosivanyshyn.db.dao.ResponseToOrderDAO;
import com.rosivanyshyn.db.dao.entity.Account;
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
import static com.rosivanyshyn.Constant.ID_VALUE_LONG;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {

    OrderDAO orderDAO = mock(OrderDAO.class);

    ResponseToOrderDAO responseToOrderDAO = mock(ResponseToOrderDAO.class);
    private final DBManager dbManager = mock(DBManager.class);
    private final Connection connection = mock(Connection.class);

    OrderServiceImpl orderService = new OrderServiceImpl(orderDAO, responseToOrderDAO, dbManager);

    Account account = Account.builder().id(ID_VALUE_LONG).build();

    ArrayList<Order> orders = new ArrayList<>();

    { orders.add(getTestOrder());}

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

    @BeforeEach
    void getConnection(){
        when( dbManager.getConnection()).thenReturn(connection);
    }
    @Test
    void createOrder() {
        when(orderDAO.insert(isA(Connection.class), isA(Order.class))).thenReturn(true);
        assertTrue( orderService.createOrder(getTestOrder()) );
    }

    @Test
    void findOrderByField() {
        when(orderDAO.getByField(isA(Connection.class), anyString(), isA(Object.class))).thenReturn(getTestOrder());
        assertEquals(getTestOrder(),
                orderService.findOrderByField(ORDER_DESCRIPTION, DESCRIPTION_VALUE)
        );
    }

    @Test
    void findFewOrders() {
        when(orderDAO.getFew(isA(Connection.class), isA(int.class), isA(int.class))).thenReturn(orders);
        when(orderDAO.countRowsInLastQuery(isA(Connection.class))).thenReturn(1);

        assertEquals(orders,
                orderService.findFewOrders(1, 1)
        );
        assertEquals(1, orderService.getRowsNumber());
    }

    @Test
    void findFewOrdersAndSort() {
        when(orderDAO.getWithDynamicQuery(isA(Connection.class), isA(String.class))).thenReturn(orders);
        when(orderDAO.countRowsInLastQuery(isA(Connection.class))).thenReturn(1);

        assertEquals(orders,
                orderService.findFewOrdersAndSort("query")
        );
        assertEquals(1, orderService.getRowsNumber());
    }

    @Test
    void updateOrder() {
        orderService.updateOrder(getTestOrder());
    }

    @Test
    void deleteOrder() {
        when(orderDAO.delete(isA(Connection.class), isA(Long.class))).thenReturn(true);
        assertTrue( orderService.deleteOrder(getTestOrder()) );
    }
    @Test
    void deleteOrderWithResponse() {
        Order order = getTestOrder();
        ResponseToOrder responseToOrder = ResponseToOrder.builder().id(ID_VALUE_LONG).build();
        order.setResponseToOrder(responseToOrder);

        when(responseToOrderDAO.deleteResponseApartments(isA(Connection.class), isA(Long.class))).thenReturn(true);
        when(responseToOrderDAO.delete(isA(Connection.class), isA(Long.class))).thenReturn(true);
        when(orderDAO.delete(isA(Connection.class), isA(Long.class))).thenReturn(true);

        assertTrue( orderService.deleteOrder(order) );
    }
}