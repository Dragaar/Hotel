package com.rosivanyshyn.service.implMySQL;

import com.rosivanyshyn.db.dao.ResponseToOrderDAO;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.Booking;
import com.rosivanyshyn.db.dao.entity.Order;
import com.rosivanyshyn.db.dao.implMySQL.OrderDAOImpl;
import com.rosivanyshyn.db.dao.implMySQL.ResponseToOrderDAOImpl;
import com.rosivanyshyn.db.manager.DBManager;
import com.rosivanyshyn.db.transaction.TransactionManager;
import com.rosivanyshyn.service.OrderService;

import java.sql.Connection;
import java.util.ArrayList;

public class OrderServiceImpl implements OrderService {

    OrderDAOImpl orderDAO = new OrderDAOImpl();
    ResponseToOrderDAO responseToOrderDAO = new ResponseToOrderDAOImpl();

    @Override
    public Boolean createOrder(Order order) {
        Connection connection = DBManager.getConnection();

        return  (Boolean) TransactionManager.execute(connection,
                ()-> orderDAO.insert(connection, order)
        );
    }

    @Override
    public Order findOrderByField(String field, Object value) {
        Connection connection = DBManager.getConnection();

        return  (Order) TransactionManager.execute(connection,
                ()-> orderDAO.getByField(connection, field, value)
        );
    }

    @Override
    public ArrayList<Order> findAllOrders() {
        return null;
    }

    @Override
    public ArrayList<Order> findFewOrders(int start, int total) {
        Connection connection = DBManager.getConnection();

        //sql start indexing from 0
        @SuppressWarnings("unchecked")
        ArrayList<Order> result = (ArrayList<Order>) TransactionManager.execute(connection,
                ()-> orderDAO.getFew(connection, start-1, total)
        );
        return result;
    }

    @Override
    public ArrayList<Order> findFewOrdersAndSort(String secondQueryPart, Object... fields) {
        Connection connection = DBManager.getConnection();

        //sql start indexing from 0
        @SuppressWarnings("unchecked")
        ArrayList<Order> result = (ArrayList<Order>) TransactionManager.execute(connection,
                ()-> orderDAO.getWithDynamicQuery(connection, secondQueryPart, fields)
        );
        return result;
    }

    @Override
    public Boolean updateOrder(Order order) {
        return null;
    }

    @Override
    public Boolean deleteOrder(Order order) {
    Connection connection = DBManager.getConnection();

        return (Boolean) TransactionManager.execute(connection,
            ()-> {
            responseToOrderDAO.deleteResponseApartments(connection, order.getResponseToOrder().getId());
            responseToOrderDAO.delete(connection, order.getResponseToOrder().getId());
            orderDAO.delete(connection, order.getId());
            return true;
            }
        );
    }
}
