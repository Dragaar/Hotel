package com.rosivanyshyn.service;

import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.Booking;
import com.rosivanyshyn.db.dao.entity.Order;

import java.util.ArrayList;

/**
 * Order Service interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
public interface OrderService {

    /**
     * Create new order
     * @param order the order to be added
     * @return operation result
     */
    Boolean                  createOrder(Order order);

    /**
     * Find order by field
     * @param field field name
     * @param value unique field value
     * @return found order
     */
    Order                    findOrderByField(String field, Object value);

    /**
     * Find few orders by total count and from set start id
     * @param start countdown start id
     * @param total total rows count
     * @return found orders
     */
    ArrayList<Order>         findFewOrders(int start, int total);

    /**
     * Find few orders using second query part conditions and by fields for them
     * @param secondQueryPart QueryBuilder result string
     * @param fields fields to insert in second query part statement
     * @return found orders
     */
    ArrayList<Order>         findFewOrdersAndSort(String secondQueryPart, Object... fields);

    /**
     * Update order
     * @param order order to update
     * @return operation result
     */
    Boolean                  updateOrder(Order order);
    /**
     * Delete order
     * @param order order to delete
     * @return operation result
     */
    Boolean                  deleteOrder(Order order);

    /** Get last statement rows count
     * @return last statement rows count
     */
    int                      getRowsNumber();
}
