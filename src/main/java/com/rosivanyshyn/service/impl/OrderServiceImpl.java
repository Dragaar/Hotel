package com.rosivanyshyn.service.impl;

import com.rosivanyshyn.db.dao.OrderDAO;
import com.rosivanyshyn.db.dao.ResponseToOrderDAO;
import com.rosivanyshyn.db.dao.entity.Order;
import com.rosivanyshyn.db.manager.DBManager;
import com.rosivanyshyn.db.transaction.TransactionManager;
import com.rosivanyshyn.service.OrderService;

import java.sql.Connection;
import java.util.ArrayList;

public class OrderServiceImpl implements OrderService {

    OrderDAO orderDAO;
    ResponseToOrderDAO responseToOrderDAO;
    DBManager dbManager;
    private int rowsNumber;

    public OrderServiceImpl(OrderDAO orderDAO, ResponseToOrderDAO responseToOrderDAO,   DBManager dbManager) {
        this.orderDAO = orderDAO;
        this.responseToOrderDAO = responseToOrderDAO;
        this.dbManager = dbManager;
    }
    @Override
    public Boolean createOrder(Order order) {
        Connection connection = dbManager.getConnection();

        return  (Boolean) TransactionManager.execute(connection,
                ()-> orderDAO.insert(connection, order)
        );
    }

    @Override
    public Order findOrderByField(String field, Object value) {
        Connection connection = dbManager.getConnection();

        return  (Order) TransactionManager.execute(connection,
                ()-> orderDAO.getByField(connection, field, value)
        );
    }

    @Override
    public ArrayList<Order> findFewOrders(int start, int total) {
        Connection connection = dbManager.getConnection();

        //sql start indexing from 0
        @SuppressWarnings("unchecked")
        ArrayList<Order> result = (ArrayList<Order>) TransactionManager.execute(connection,
                ()-> {
                ArrayList<Order> r = orderDAO.getFew(connection, start-1, total);
                rowsNumber = orderDAO.countRowsInLastQuery(connection);
                return r;
                }
        );
        return result;
    }

    @Override
    public ArrayList<Order> findFewOrdersAndSort(String secondQueryPart, Object... fields) {
        Connection connection = dbManager.getConnection();

        //sql start indexing from 0
        @SuppressWarnings("unchecked")
        ArrayList<Order> result = (ArrayList<Order>) TransactionManager.execute(connection,
                ()-> {
                ArrayList<Order> r = orderDAO.getWithDynamicQuery(connection, secondQueryPart, fields);
                rowsNumber = orderDAO.countRowsInLastQuery(connection);
                return r;
                }
        );
        return result;
    }

    @Override
    public Boolean updateOrder(Order order) {
        return null;
    }

    /**
     * Delete order, response-to-order and all apartments submitted to response-to-order
     * @param order order to delete
     * @return Boolean result of operation
     */
    @Override
    public Boolean deleteOrder(Order order) {
    Connection connection = dbManager.getConnection();

        return (Boolean) TransactionManager.execute(connection,
            ()-> {
            if(order.getResponseToOrder() != null) {
                responseToOrderDAO.deleteResponseApartments(connection, order.getResponseToOrder().getId());
                responseToOrderDAO.delete(connection, order.getResponseToOrder().getId());
            }
            orderDAO.delete(connection, order.getId());
            return true;
            }
        );
    }
    @Override
    public int getRowsNumber(){
        return rowsNumber;
    }
}
