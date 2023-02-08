package com.rosivanyshyn.service.impl;

import com.rosivanyshyn.db.dao.OrderDAO;
import com.rosivanyshyn.db.dao.ResponseToOrderDAO;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.Order;
import com.rosivanyshyn.db.dao.entity.ResponseToOrder;
import com.rosivanyshyn.db.manager.DBManager;
import com.rosivanyshyn.db.transaction.TransactionManager;
import com.rosivanyshyn.service.ResponseToOrderService;

import java.sql.Connection;
import java.util.ArrayList;

public class ResponseToOrderServiceImpl implements ResponseToOrderService {
    ResponseToOrderDAO responseToOrderDAO;
    OrderDAO orderDAO;
    DBManager dbManager;

    public ResponseToOrderServiceImpl(ResponseToOrderDAO responseToOrderDAO, OrderDAO orderDAO, DBManager dbManager) {
        this.responseToOrderDAO = responseToOrderDAO;
        this.orderDAO = orderDAO;
        this.dbManager = dbManager;
    }
    /** Attach response-to-order in order and submit apartment to response-to-order
     * @param order order in which attach response
     * @param responseToOrder response-to-order in which submit apartment
     * @param apartments submittable apartments
     * @return result of operation
     */
    @Override
    public Boolean createResponseToOrder(Order order, ResponseToOrder responseToOrder, ArrayList<Apartment> apartments) {
        Connection connection = dbManager.getConnection();

        return  (Boolean) TransactionManager.execute(connection,
                ()-> {
                    responseToOrderDAO.insert(connection, responseToOrder);

                    //Update foreign key
                    order.setResponseToOrder(responseToOrder);
                    orderDAO.update(connection, order);

                    //Create many-to-many relationship
                    for (Apartment apartment : apartments) {
                        responseToOrderDAO.setApartmentToResponse(connection, responseToOrder, apartment);
                    }
                    return true;
                }
        );
    }

    @Override
    public ResponseToOrder findResponseToOrderByField(String field, Object value) {
        Connection connection = dbManager.getConnection();

        return  (ResponseToOrder) TransactionManager.execute(connection,
                ()-> responseToOrderDAO.getByField(connection, field, value)
        );
    }

    @Override
    public Boolean updateResponseToOrder(ResponseToOrder responseToOrder) {
        return null;
    }


    /**
     * Get submitted apartments to response-to-order
     * @param responseToOrder response-to-order in which submitted apartments
     * @return submitted apartments
     */
    @Override
    public ArrayList<Apartment> findAllResponseApartments(ResponseToOrder responseToOrder) {
        Connection connection = dbManager.getConnection();

        @SuppressWarnings("unchecked")
        ArrayList<Apartment> result = (ArrayList<Apartment>) TransactionManager.execute(connection,
                ()-> responseToOrderDAO.getResponseApartments(connection, responseToOrder.getId())
        );
        return result;
    }

}
