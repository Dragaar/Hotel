package com.rosivanyshyn.db.dao;

import com.rosivanyshyn.db.dao.entity.Apartment;

import java.sql.Connection;
import java.util.ArrayList;

public interface ApartmentDAO extends GenericDAO<Apartment>{
    ArrayList<Apartment>        getUniqueApartmentsWhichAreBookedWithDynamicQuery(Connection con, String secondQueryPart, Object... fields);
    }
