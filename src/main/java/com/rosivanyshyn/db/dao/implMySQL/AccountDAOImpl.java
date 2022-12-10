package com.rosivanyshyn.db.dao.implMySQL;

import com.rosivanyshyn.db.dao.AccountDAO;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.entity.Order;

import static com.rosivanyshyn.db.dao.constant.Query.*;
import static com.rosivanyshyn.db.dao.constant.Field.*;
public class AccountDAOImpl extends GenericDAOImpl<Account> implements AccountDAO {
    //------------------ Queries initialising ------------------------\\
    @Override
    String insertQuery() { return INSERT_ACCOUNT; }
    @Override
    String selectQuery() { return SELECT_ACCOUNT; }
    @Override
    String selectAllQuery() { return SELECT_ALL_ACCOUNTS; }
    @Override
    String selectFewQuery() { return SELECT_FEW_ACCOUNTS; }
    @Override
    String selectByFieldQuery() { return SELECT_ACCOUNT_BY_FIELD; }
    @Override
    String updateQuery() { return UPDATE_ACCOUNT; }
    @Override
    String deleteQuery() { return DELETE_ACCOUNT; }

    //------------------ DBStatementOperations initialising ------------------------\\
    @Override
     DBStatementOperations<Account> insertOperations() {
        return (stmt, entity) -> {
            stmt.setString(1, entity.getRole());
            stmt.setString(2, entity.getFirstName());
            stmt.setString(3, entity.getLastName());
            stmt.setString(4, entity.getEmail());
            stmt.setString(5, entity.getPassword());
            stmt.setBoolean(6, entity.getState());
        };
    }

    @Override
    DBStatementOperations<Account> updateOperations() {
        return (stmt, entity) -> {
            stmt.setString(1, entity.getRole());
            stmt.setString(2, entity.getFirstName());
            stmt.setString(3, entity.getLastName());
            stmt.setString(4, entity.getEmail());
            stmt.setString(5, entity.getPassword());
            stmt.setBoolean(6, entity.getState());

            stmt.setLong(7, entity.getId());
        };
    }
    //------------------ ExtractEntity initialising ------------------------\\
    @Override
    SetGeneratedValuesToEntity<Account> setGeneratedValuesToEntity() {
        return (rs, entity) ->
                entity.setId( (long) rs.getInt(1) );
    }
    @Override
    ExtractEntity<Account> entityFromGet() {
        return (rs) ->
            Account.builder()
                    .id(rs.getLong(ENTITY_ID))
                    .role(rs.getString(ACCOUNT_ROLE))
                    .firstName(rs.getString(ACCOUNT_FIRST_NAME))
                    .lastName(rs.getString(ACCOUNT_LAST_NAME))
                    .email(rs.getString(ACCOUNT_EMAIL))
                    .password(rs.getString(ACCOUNT_PASSWORD))
                    .state(rs.getBoolean(ENTITY_STATE))
                    .build();
    }


}
