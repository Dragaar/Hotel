package com.rosivanyshyn.service.implMySQL;

import com.rosivanyshyn.db.dao.ApartmentDAO;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.implMySQL.ApartmentDAOImpl;
import com.rosivanyshyn.db.manager.DBManager;
import com.rosivanyshyn.db.transaction.TransactionManager;
import com.rosivanyshyn.service.ApartmentService;

import java.sql.Connection;
import java.util.ArrayList;

public class ApartmentServiceImpl implements ApartmentService {

    ApartmentDAO apartmentDAO = new ApartmentDAOImpl();
    private int rowsNumber;

    @Override
    public Boolean createApartment(Apartment apartment) {
        return null;
    }

    @Override
    public Boolean isApartmentExist(Apartment apartment) {
        return null;
    }

    @Override
    public Apartment findApartmentByField(String field, Object value) {
        Connection connection = DBManager.getConnection();

        return  (Apartment) TransactionManager.execute(connection,
                ()-> apartmentDAO.getByField(connection, field, value)
        );
    }

    @Override
    public ArrayList<Apartment> findAllApartment() {
        return null;
    }

    @Override
    public ArrayList<Apartment> findFewApartment(int start, int total) {
        Connection connection = DBManager.getConnection();

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
    public ArrayList<Apartment> findFewApartmentsAndSort(String secondQueryPart, String... fields) {
        Connection connection = DBManager.getConnection();

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
        Connection connection = DBManager.getConnection();

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
    public Boolean updateApartment(Apartment apartment) {
        return null;
    }

    @Override
    public Boolean deleteApartment(Apartment apartment) {
        Connection connection = DBManager.getConnection();
        return (Boolean) TransactionManager.execute(connection,
                ()-> apartmentDAO.delete(connection, apartment.getId())
        );
    }

    public int getRowsNumber(){
        return rowsNumber;
    }
}
