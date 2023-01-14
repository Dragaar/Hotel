package com.rosivanyshyn.db.dao.implMySQL;

import com.rosivanyshyn.db.dao.OrderDAO;
import com.rosivanyshyn.db.dao.entity.*;
import com.rosivanyshyn.db.manager.DBManager;
import com.rosivanyshyn.db.transaction.TransactionManager;

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
            stmt.setInt(1, entity.getGuestsNumber());
            stmt.setString(2, entity.getRoomsNumber());
            stmt.setString(3, entity.getApartmentClass());
            stmt.setLong(4, entity.getPrice());
            stmt.setString(5, entity.getDescription());
            stmt.setDate(6, entity.getCheckOutDate());
            stmt.setDate(7, entity.getCheckInDate());

            stmt.setLong(8, entity.getAccount().getId());
           //ResponseToOrder = NULL
        };
    }

    @Override
    DBStatementOperations<Order> updateOperations() {
        return (stmt, entity) -> {
            stmt.setInt(1, entity.getGuestsNumber());
            stmt.setString(2, entity.getRoomsNumber());
            stmt.setString(3, entity.getApartmentClass());
            stmt.setLong(4, entity.getPrice());
            stmt.setString(5, entity.getDescription());
            stmt.setDate(6, entity.getCheckOutDate());
            stmt.setDate(7, entity.getCheckInDate());
            
           //Author Account unchanged
          if(entity.getResponseToOrder() != null){
            stmt.setLong(8, entity.getResponseToOrder().getId());
            stmt.setLong(9, entity.getId());
          }
          else {stmt.setNull(8, Types.INTEGER);}
            //WHERE
            stmt.setLong(9, entity.getId());
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
                    .guestsNumber(rs.getInt(ORDER_GUESTS_NUMBER))
                    .roomsNumber(rs.getString(ORDER_ROOMS_NUMBER))
                    .apartmentClass(rs.getString(ORDER_APARTMENT_CLASS))
                    .price(rs.getLong(ORDER_APARTMENT_PRICE))
                    .description(rs.getString(ORDER_DESCRIPTION))
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

        return (Account) TransactionManager.execute(connection,
                ()-> accountDAO.get(connection, id)
        );
    }
    //foreign key
    private ResponseToOrder getResponseToOrderForeignKey(Long id){
        Connection connection= DBManager.getConnection();
        ResponseToOrderDAOImpl responseToOrderDAO = new ResponseToOrderDAOImpl();

        return (ResponseToOrder) TransactionManager.execute(connection,
                ()-> responseToOrderDAO.get(connection, id)
        );
    }

}
