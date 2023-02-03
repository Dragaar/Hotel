package com.rosivanyshyn.db.dao;

import com.rosivanyshyn.db.dao.entity.Apartment;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Apartment DAO interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
public interface ApartmentDAO extends GenericDAO<Apartment>{
    /** Get a list of unique booked apartments and apply to them query-builder part (Sorting, limits, etc.)
     *
     * @param con connection to database
     * @param secondQueryPart query builder string part
     * @param fields fields for insertion in query builder string part statement
     * @return ArrayList  result array
     */
    ArrayList<Apartment>        getUniqueApartmentsWhichAreBookedWithDynamicQuery(Connection con, String secondQueryPart, Object... fields);
    }
