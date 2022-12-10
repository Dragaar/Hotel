package com.rosivanyshyn.db.dao.implMySQL;

import com.mysql.cj.xdevapi.Type;
import com.rosivanyshyn.db.dao.OrderDAO;
import com.rosivanyshyn.db.dao.entity.*;
import com.rosivanyshyn.db.manager.DBManager;

import java.sql.Connection;
import java.sql.Types;

import static com.rosivanyshyn.db.dao.constant.Field.*;
import static com.rosivanyshyn.db.dao.constant.Query.*;

public class OrderDAOImpl extends GenericDAOImpl<Order> implements OrderDAO {
    //------------------ Queries initialising ------------------------\\
    @Override
    String insertQuery() { return INSERT_ORDER; }
    @Override
    String selectQuery() { return SELECT_ORDER; }
    @Override
    String selectAllQuery() { return SELECT_ALL_ORDERS; }

    @Override
    String selectFewQuery() { return SELECT_FEW_ORDERS; }
    @Override
    String selectByFieldQuery() { return SELECT_ORDER_BY_FIELD; }
    @Override
    String updateQuery() { return UPDATE_ORDER; }
    @Override
    String deleteQuery() { return DELETE_ORDER; }

    //------------------ DBStatementOperations initialising ------------------------\\
    @Override
    DBStatementOperations<Order> insertOperations() {
        return (stmt, entity) -> {
            stmt.setString(1, entity.getGuestsNumber());
            stmt.setString(2, entity.getRoomsNumber());
            stmt.setString(3, entity.getApartmentClass());
            stmt.setDate(4, entity.getCheckOutDate());
            stmt.setDate(5, entity.getCheckInDate());

            stmt.setLong(6, entity.getAccount().getId());
           //ResponseToOrder = NULL
        };
    }

    @Override
    DBStatementOperations<Order> updateOperations() {
        return (stmt, entity) -> {
            stmt.setString(1, entity.getGuestsNumber());
            stmt.setString(2, entity.getRoomsNumber());
            stmt.setString(3, entity.getApartmentClass());
            stmt.setDate(4, entity.getCheckOutDate());
            stmt.setDate(5, entity.getCheckInDate());

           //Author Account unchanged
          if(entity.getResponseToOrder() != null){
            stmt.setLong(6, entity.getResponseToOrder().getId());}
          else {stmt.setNull(6, Types.INTEGER);}
            //WHERE
            stmt.setLong(7, entity.getId());
        };
    }



    //------------------ ExtractEntity initialising ------------------------\\
    @Override
    SetGeneratedValuesToEntity<Order> setGeneratedValuesToEntity() {
        return (rs, entity) ->
                entity.setId( (long) rs.getInt(1) );
    }
    @Override
    ExtractEntity<Order> entityFromGet() {
        return (rs) -> {

            return Order.builder()
                    .id(rs.getLong(ENTITY_ID))
                    .guestsNumber(rs.getString(ORDER_GUESTS_NUMBER))
                    .roomsNumber(rs.getString(ORDER_ROOMS_NUMBER))
                    .apartmentClass(rs.getString(ORDER_APARTMENT_CLASS))
                    .checkOutDate(rs.getDate(ORDER_CHECK_OUT_DATE))
                    .checkInDate(rs.getDate(ORDER_CHECK_IN_DATE))
                    //foreign keys
                    .account(
                            geAccountForeignKey(rs.getLong(ORDER_ACCOUNT_ID))
                    )
                    //may be null
                    .responseToOrder(getResponseToOrderForeignKey(rs.getLong(ORDER_RESPONSE_TO_ORDER_ID)))
                    .build();

           /* Long  tempFK = rs.getLong(ORDER_RESPONSE_TO_ORDER_ID);
            if(tempFK!=null) {
                return Order.builder()
                    .responseToOrder(getResponseToOrderForeignKey(tempFK))
                    .build();
            } else return Order.builder().build();*/
        };
    }

    //foreign key
    private Account geAccountForeignKey(Long id){
        Connection connection= DBManager.getConnection();
        AccountDAOImpl accountDAO = new AccountDAOImpl();

        return accountDAO.get(connection, id);
    }
    //foreign key
    private ResponseToOrder getResponseToOrderForeignKey(Long id){
        Connection connection= DBManager.getConnection();
        ResponseToOrderDAOImpl responseToOrderDAO = new ResponseToOrderDAOImpl();

        return responseToOrderDAO.get(connection, id);
    }

}
