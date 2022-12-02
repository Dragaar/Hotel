package com.rosivanyshyn.db.dao;

import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.ResponseToOrder;

import java.sql.Connection;
import java.util.ArrayList;

public interface ResponseToOrderDAO extends GenericDAO<ResponseToOrder> {

    //Many-to-Many relationship
    boolean setApartmentToResponse(Connection con, ResponseToOrder rto, Apartment apartment);
    ArrayList<Apartment> getResponseApartments(Connection con, Long id);

    boolean deleteResponseApartments(Connection con, Long id);

}
