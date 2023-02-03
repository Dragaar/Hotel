package com.rosivanyshyn.db.dao;

import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.ResponseToOrder;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Response To Order DAO interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
public interface ResponseToOrderDAO extends GenericDAO<ResponseToOrder> {

    //Many-to-Many relationship

    /** Set(attach) apartment to response-to-order(RTO).
     * <br> Create new reference to the apartment in the RTO
     * @param con connection to database
     * @param rto response to order
     * @param apartment apartment to set to response
     * @return Boolean operation result
     */
    boolean setApartmentToResponse(Connection con, ResponseToOrder rto, Apartment apartment);

    /** Get all attached to response-to-order(RTO) apartments by RTO id.
     *
     * @param con connection to database
     * @param id response to order id
     * @return ArrayList of attached apartments
     */
    ArrayList<Apartment> getResponseApartments(Connection con, Long id);

    /** Delete all attached to response-to-order(RTO) apartments by RTO id.
     *  <br> Delete only references to apartments
     * @param con connection to database
     * @param id response to order id
     * @return Boolean operation result
     */
    boolean deleteResponseApartments(Connection con, Long id);

}
