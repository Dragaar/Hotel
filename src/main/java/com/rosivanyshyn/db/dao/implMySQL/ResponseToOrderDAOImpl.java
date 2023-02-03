package com.rosivanyshyn.db.dao.implMySQL;

import com.rosivanyshyn.db.dao.ResponseToOrderDAO;

import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.ResponseToOrder;
import com.rosivanyshyn.exeption.DAOException;

import java.sql.*;
import java.util.ArrayList;


import static com.rosivanyshyn.db.dao.constant.Field.*;
import static com.rosivanyshyn.db.dao.constant.Query.*;
import static com.rosivanyshyn.exeption.Message.*;

/**
 * Response To Order DAO interface implementation.
 *
 * @author Rostyslav Ivanyshyn.
 */
public class ResponseToOrderDAOImpl extends GenericDAOImpl<ResponseToOrder> implements ResponseToOrderDAO {
    //------------------ Queries initialising ------------------------\\
    @Override
    String insertQuery() { return INSERT_RESPONSE_TO_ORDER; }
    @Override
    String selectQuery() { return SELECT_RESPONSE_TO_ORDER; }
    @Override
    String selectAllQuery() { return SELECT_ALL_RESPONSES_TO_ORDER; }

    @Override
    String selectFewQuery() { return SELECT_FEW_RESPONSES_TO_ORDER; }
    @Override
    String selectByFieldQuery() { return SELECT_RESPONSE_TO_ORDER_BY_FIELD; }
    @Override
    String updateQuery() { return UPDATE_RESPONSE_TO_ORDER; }
    @Override
    String deleteQuery() { return DELETE_RESPONSE_TO_ORDER; }

    //------------------ DBStatementOperations initialising ------------------------\\
    @Override
    DBStatementOperations<ResponseToOrder> insertOperations() {
        return (stmt, entity) ->
                stmt.setString(1, entity.getDescription());
    }

    @Override
    DBStatementOperations<ResponseToOrder> updateOperations() {
        return (stmt, entity) -> {
            stmt.setString(1, entity.getDescription());

            stmt.setLong(2, entity.getId());
        };
    }
    //------------------ ExtractEntity initialising ------------------------\\
    @Override
    SetGeneratedValuesToEntity<ResponseToOrder> setGeneratedValuesToEntity() {
        return (rs, entity) ->
                entity.setId( (long) rs.getInt(1) );
    }
    @Override
    ExtractEntity<ResponseToOrder> entityFromGet() {
        return (rs) -> ResponseToOrder.builder()
                .id(rs.getLong(ENTITY_ID))
                .description(rs.getString(RESPONSE_TO_ORDER_DESKR))
                .build();
    }


    //-----------------------Unique Queries---------------------------\\

    //Many to Many

    /** Set apartments references to RTO
     * @param con Connection
     * @param rto RTO
     * @param apartment Apartment
     * @return query result
     */
    public boolean setApartmentToResponse(Connection con, ResponseToOrder rto, Apartment apartment) {
        LOG.info("Query: " + INSERT_RESPONSE_TO_ORDER_APARTMENTS);

        try (PreparedStatement stmt = con.prepareStatement(INSERT_RESPONSE_TO_ORDER_APARTMENTS)){
            stmt.setLong(1, rto.getId());
            stmt.setLong(2, apartment.getId());

            int countSuccessfulInsertions = stmt.executeUpdate();

            if(countSuccessfulInsertions >0) return true;

        } catch (SQLException ex){
            LOG.error(className + " " + MANY_TO_MANY_ERROR, ex);
            throw new DAOException(className + " " + MANY_TO_MANY_ERROR, ex);
        }
        return  false;
    }

    /** Get apartments references attached to RTO
     * @param con Connection
     * @param id Response to order id
     * @return Apartments array
     */
    public ArrayList<Apartment> getResponseApartments(Connection con, Long id) {
        LOG.info("Query: " + SELECT_RESPONSE_TO_ORDER_APARTMENTS);

        ResultSet rs;
        ArrayList<Apartment> apartments = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(SELECT_RESPONSE_TO_ORDER_APARTMENTS)) {

            stmt.setLong(1, id);

            rs = stmt.executeQuery();
            while(rs.next()){
                apartments.add(Apartment.builder()
                                .id(rs.getLong(ENTITY_ID))
                                .title(rs.getString(APARTMENT_TITLE))
                                .description(rs.getString(APARTMENT_DESCRIPTION))
                                .imageURL(rs.getString(APARTMENT_IMAGE_URL))
                                .address(rs.getString(APARTMENT_ADRESS))
                                .maxGuestsNumber(rs.getString(APARTMENT_MAX_GUEST_NUMBER))
                                .roomsNumber(rs.getString(APARTMENT_ROOMS_NUMBER))
                                .apartmentClass(rs.getString(APARTMENT_CLASS))
                                .price(rs.getLong(APARTMENT_PRICE))
                                .state(rs.getBoolean(ENTITY_STATE))
                                .build()
                               );
            }
            if(apartments.size()>0) return apartments;

        } catch (SQLException ex){
            LOG.error(className + " " + MANY_TO_MANY_ERROR, ex);
            throw new DAOException(className + " " + MANY_TO_MANY_ERROR, ex);
        }
        return null;
    }

    /** Delete apartments references attached to RTO
     * @param con Connection
     * @param id Response to order id
     * @return query result
     */
    public boolean deleteResponseApartments(Connection con, Long id) {
        LOG.info("Query: " + DELETE_RESPONSE_TO_ORDER_APARTMENTS);

        try (PreparedStatement stmt = con.prepareStatement(DELETE_RESPONSE_TO_ORDER_APARTMENTS) ) {
            stmt.setLong(1, id);

            int countSuccessfulDeletes = stmt.executeUpdate();
            return countSuccessfulDeletes  > 0;

        } catch (SQLException ex){
            LOG.error(className + " " + MANY_TO_MANY_ERROR, ex);
            throw new DAOException(className + " " + MANY_TO_MANY_ERROR, ex);
        }
    }




}
