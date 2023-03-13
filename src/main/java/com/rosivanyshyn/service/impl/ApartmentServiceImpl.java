package com.rosivanyshyn.service.impl;

import com.rosivanyshyn.db.dao.ApartmentDAO;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.manager.DBManager;
import com.rosivanyshyn.db.transaction.TransactionManager;
import com.rosivanyshyn.service.ApartmentService;

import java.sql.Connection;
import java.util.ArrayList;

public class ApartmentServiceImpl implements ApartmentService {

    ApartmentDAO apartmentDAO;
    DBManager dbManager;
    private int rowsNumber;

    public ApartmentServiceImpl(ApartmentDAO apartmentDAO,   DBManager dbManager) {
        this.apartmentDAO = apartmentDAO;
        this.dbManager = dbManager;
    }
    @Override
    public Boolean createApartment(Apartment apartment) {
        return null;
    }

    @Override
    public Apartment findApartmentByField(String field, Object value) {
        Connection connection = dbManager.getConnection();

        return  (Apartment) TransactionManager.execute(connection,
                ()-> apartmentDAO.getByField(connection, field, value)
        );
    }

    @Override
    public ArrayList<Apartment> findFewApartment(int start, int total) {
        Connection connection = dbManager.getConnection();

        //sql start indexing from 0
        @SuppressWarnings("unchecked")
         ArrayList<Apartment> result = (ArrayList<Apartment>) TransactionManager.execute(connection,
                ()-> {
                ArrayList<Apartment> r = apartmentDAO.getFew(connection, start-1, total);
                rowsNumber = apartmentDAO.countRowsInLastQuery(connection);
                return r;
                }
        );
        return result;
    }

    @Override
    public ArrayList<Apartment> searchApartment(String value, int start, int total) {
        Connection connection = dbManager.getConnection();
        @SuppressWarnings("unchecked")
        ArrayList<Apartment> result = (ArrayList<Apartment>) TransactionManager.execute(connection,
                ()-> {
                    ArrayList<Apartment> r = apartmentDAO.searchApartments(connection, value, start-1, total);
                    rowsNumber = apartmentDAO.countRowsInLastQuery(connection);
                    return r;
                }
        );
        return result;
    }

    @Override
    public ArrayList<Apartment> findFewApartmentsAndSort(String secondQueryPart, String... fields) {
        Connection connection = dbManager.getConnection();

        //sql start indexing from 0
        @SuppressWarnings("unchecked")
        ArrayList<Apartment> result = (ArrayList<Apartment>) TransactionManager.execute(connection,
                ()-> {
                ArrayList<Apartment> r = apartmentDAO.getWithDynamicQuery(connection, secondQueryPart, fields);
                rowsNumber = apartmentDAO.countRowsInLastQuery(connection);
                return r;
                }
        );
        return result;
    }
    @Override
    public ArrayList<Apartment> findFewApartmentsWhichAreBooked(String secondQueryPart, String... fields) {
        Connection connection = dbManager.getConnection();

        //sql start indexing from 0
        @SuppressWarnings("unchecked")
        ArrayList<Apartment> result = (ArrayList<Apartment>) TransactionManager.execute(connection,
                ()-> {
                    ArrayList<Apartment> r = apartmentDAO.getUniqueApartmentsWhichAreBookedWithDynamicQuery(connection, secondQueryPart, fields);
                    rowsNumber = apartmentDAO.countRowsInLastQuery(connection);
                    return r;
                }
        );
        return result;
    }

    @Override
    public ArrayList<Apartment> findFewApartmentsWhichAreFree(String secondQueryPart, String... fields) {
        Connection connection = dbManager.getConnection();

        //sql start indexing from 0
        @SuppressWarnings("unchecked")
        ArrayList<Apartment> result = (ArrayList<Apartment>) TransactionManager.execute(connection,
                ()-> {
                    ArrayList<Apartment> r = apartmentDAO.getUniqueApartmentsWhichAreFree(connection, secondQueryPart, fields);
                    rowsNumber = apartmentDAO.countRowsInLastQuery(connection);
                    return r;
                }
        );
        return result;
    }

    @Override
    public Boolean updateApartment(Apartment apartment) {
        return null;
    }

    @Override
    public Boolean deleteApartment(Apartment apartment) {
        Connection connection = dbManager.getConnection();
        return (Boolean) TransactionManager.execute(connection,
                ()-> apartmentDAO.delete(connection, apartment.getId())
        );
    }
    @Override
    public int getRowsNumber(){
        return rowsNumber;
    }
}
