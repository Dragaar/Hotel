package com.rosivanyshyn.service;

import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.Booking;
import com.rosivanyshyn.db.dao.entity.Order;

import java.util.ArrayList;

public interface OrderService {
    Boolean                  createOrder(Order order);

    Order                    findOrderByField(String field, Object value);
    ArrayList<Order>         findAllOrders();

    ArrayList<Order>         findFewOrders(int start, int total);
    ArrayList<Order>         findFewOrdersAndSort(String secondQueryPart, Object... fields);
    Boolean                  updateOrder(Order order);
    Boolean                  deleteOrder(Order order);
}
