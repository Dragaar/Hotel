package com.rosivanyshyn.db.dao.implMySQL;


import com.rosivanyshyn.db.dao.ApartmentDAO;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.Order;

import static com.rosivanyshyn.db.dao.constant.Field.*;
import static com.rosivanyshyn.db.dao.constant.Query.*;


public class ApartmentDAOImpl extends GenericDAOImpl<Apartment> implements ApartmentDAO {
    //------------------ Queries initialising ------------------------\\
    @Override
    String insertQuery() { return INSERT_APARTMENT; }
    @Override
    String selectQuery() { return SELECT_APARTMENT; }
    @Override
    String selectAllQuery() { return SELECT_ALL_APARTMENTS; }
    @Override
    String selectByFieldQuery() { return SELECT_APARTMENT_BY_FIELD; }
    @Override
    String updateQuery() { return UPDATE_APARTMENT; }
    @Override
    String deleteQuery() { return DELETE_APARTMENT; }

    //------------------ DBStatementOperations initialising ------------------------\\
    @Override
    DBStatementOperations<Apartment> insertOperations() {
        return (stmt, entity) -> {
            stmt.setString(1, entity.getMaxGuestsNumber());
            stmt.setString(2, entity.getRoomsNumber());
            stmt.setString(3, entity.getApartmentClass());
            stmt.setLong(4, entity.getPrice());
            stmt.setBoolean(5, entity.getState());
        };
    }

    @Override
    DBStatementOperations<Apartment> updateOperations() {
        return (stmt, entity) -> {
            stmt.setString(1, entity.getMaxGuestsNumber());
            stmt.setString(2, entity.getRoomsNumber());
            stmt.setString(3, entity.getApartmentClass());
            stmt.setLong(4, entity.getPrice());
            stmt.setBoolean(5, entity.getState());

            stmt.setLong(6, entity.getId());
        };
    }
    //------------------ ExtractEntity initialising ------------------------\\
    @Override
    SetGeneratedValuesToEntity<Apartment> setGeneratedValuesToEntity() {
        return (rs, entity) ->
                entity.setId( (long) rs.getInt(1) );
    }
    @Override
    ExtractEntity<Apartment> entityFromGet() {
        return (rs) ->
                Apartment.builder()
                        .id(rs.getLong(ENTITY_ID))
                        .maxGuestsNumber(rs.getString(APARTMENT_MAX_GUEST_NUMBER))
                        .roomsNumber(rs.getString(APARTMENT_ROOMS_NUMBER))
                        .apartmentClass(rs.getString(APARTMENT_CLASS))
                        .price(rs.getLong(APARTMENT_PRICE))
                        .state(rs.getBoolean(ENTITY_STATE))
                        .build();
    }


}