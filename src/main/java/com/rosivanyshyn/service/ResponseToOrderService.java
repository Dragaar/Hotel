package com.rosivanyshyn.service;

import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.Order;
import com.rosivanyshyn.db.dao.entity.ResponseToOrder;

import java.util.ArrayList;

public interface ResponseToOrderService {
    Boolean                            createResponseToOrder(Order order, ResponseToOrder responseToOrder, ArrayList<Apartment> apartments);
    ResponseToOrder                    findResponseToOrderByField(String field, Object value);
    ArrayList<ResponseToOrder>         findAllResponseToOrders();
    ArrayList<ResponseToOrder>         findFewResponseToOrders(int start, int total);
    Boolean                            updateResponseToOrder(ResponseToOrder responseToOrder);

    //-----------------------Many to Many---------------------------\\

    ArrayList<Apartment>     findAllResponseApartments(ResponseToOrder responseToOrder);

}
