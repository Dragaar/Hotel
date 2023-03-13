package com.rosivanyshyn.db.dao.implMySQL;


import com.rosivanyshyn.db.dao.ApartmentDAO;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.exeption.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;

import static com.rosivanyshyn.db.dao.constant.Field.*;
import static com.rosivanyshyn.db.dao.constant.Query.*;
import static com.rosivanyshyn.exeption.Message.SEARCH_ERROR;

/**
 * Apartment DAO interface implementation.
 *
 * @author Rostyslav Ivanyshyn.
 */
public class ApartmentDAOImpl extends GenericDAOImpl<Apartment> implements ApartmentDAO {
    //------------------ Queries initialising ------------------------\\
    @Override
    String insertQuery() { return INSERT_APARTMENT; }
    @Override
    String selectQuery() { return SELECT_APARTMENT; }
    @Override
    String selectAllQuery() { return SELECT_ALL_APARTMENTS; }
    @Override
    String selectFewQuery() { return SELECT_FEW_APARTMENTS; }
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
            stmt.setString(1, entity.getTitle());
            stmt.setString(2, entity.getDescription());
            stmt.setString(3, entity.getImageURL());
            stmt.setString(4, entity.getAddress());
            stmt.setString(5, entity.getMaxGuestsNumber());
            stmt.setString(6, entity.getRoomsNumber());
            stmt.setString(7, entity.getApartmentClass());
            stmt.setLong(8, entity.getPrice());
            stmt.setBoolean(9, entity.getState());
        };
    }

    @Override
    DBStatementOperations<Apartment> updateOperations() {
        return (stmt, entity) -> {
            stmt.setString(1, entity.getTitle());
            stmt.setString(2, entity.getDescription());
            stmt.setString(3, entity.getImageURL());
            stmt.setString(4, entity.getAddress());
            stmt.setString(5, entity.getMaxGuestsNumber());
            stmt.setString(6, entity.getRoomsNumber());
            stmt.setString(7, entity.getApartmentClass());
            stmt.setLong(8, entity.getPrice());
            stmt.setBoolean(9, entity.getState());

            stmt.setLong(10, entity.getId());
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
                        .title(rs.getString(APARTMENT_TITLE))
                        .description(rs.getString(APARTMENT_DESCRIPTION))
                        .imageURL(rs.getString(APARTMENT_IMAGE_URL))
                        .address(rs.getString(APARTMENT_ADRESS))
                        .maxGuestsNumber(rs.getString(APARTMENT_MAX_GUEST_NUMBER))
                        .roomsNumber(rs.getString(APARTMENT_ROOMS_NUMBER))
                        .apartmentClass(rs.getString(APARTMENT_CLASS))
                        .price(rs.getLong(APARTMENT_PRICE))
                        .state(rs.getBoolean(ENTITY_STATE))
                        .build();
    }

    //-----------------------Unique Queries---------------------------\\
    @Override
    public ArrayList<Apartment> getUniqueApartmentsWhichAreBookedWithDynamicQuery(Connection con, String secondQueryPart, Object... fields){

        Formatter formatter = new Formatter();
        formatter.format(UNIQUE_APARTMENTS_WHICH_ARE_BOOKED_AND_ACTUAL, secondQueryPart);

        return getWithDynamicQuery(con, formatter.toString(), fields);
    }
    @Override
    public ArrayList<Apartment> getUniqueApartmentsWhichAreFree(Connection con, String secondQueryPart, Object... fields){

        Formatter formatter = new Formatter();
        formatter.format(UNIQUE_APARTMENTS_WHICH_ARE_FREE_AND_ACTUAL, secondQueryPart);

        return getWithDynamicQuery(con, formatter.toString(), fields);
    }

    @Override
    public ArrayList<Apartment> searchApartments(Connection con, String value, int start, int total){
        LOG.info("Query: " + SEARCH_APARTMENTS);
        ArrayList<Apartment> apartments = new ArrayList<>();

        ResultSet rs;

        try (PreparedStatement stmt = con.prepareStatement(SEARCH_APARTMENTS)) {
            stmt.setString(1, value);
            stmt.setString(2, value);
            stmt.setLong(3, start);
            stmt.setLong(4, total);

            rs = stmt.executeQuery();
            while (rs.next()) {
                apartments.add(entityFromGet().extractEntity(rs));
            }
            return apartments;

        } catch (SQLException ex){
            LOG.error(className + " " + SEARCH_ERROR, ex);
            throw new DAOException(className + " " + SEARCH_ERROR, ex);
        }
    }


    }