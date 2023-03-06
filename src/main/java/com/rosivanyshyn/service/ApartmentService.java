package com.rosivanyshyn.service;

import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.entity.Apartment;

import java.util.ArrayList;

/**
 * Apartment Service interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
public interface ApartmentService {

    /**
     * Create new apartment
     * @param apartment the apartment to be added
     * @return operation result
     */
    Boolean                  createApartment(Apartment apartment);

    /**
     * Find apartment by field
     * @param field field name
     * @param value unique field value
     * @return found apartment
     */
    Apartment                findApartmentByField(String field, Object value);

    /**
     * Find few apartments by total count and from set start id
     * @param start countdown start id
     * @param total total rows count
     * @return found apartments
     */
    ArrayList<Apartment>     findFewApartment(int start, int total);

    /**
     * Find few apartments using direct search algorithm by total count and from set start id
     * @param value user value
     * @param start countdown start id
     * @param total total rows count
     * @return found apartments
     */
    ArrayList<Apartment> searchApartment(String value, int start, int total);

    /**
     * Find few apartments using second query part conditions and by fields for them
     * @param secondQueryPart QueryBuilder result string
     * @param fields fields to insert in second query part statement
     * @return found apartments
     */
    ArrayList<Apartment>     findFewApartmentsAndSort(String secondQueryPart, String... fields);

    /**
     * Find few apartments which are already booked using second query part conditions and by fields for them
     * @param secondQueryPart QueryBuilder result string
     * @param fields fields to insert in second query part statement
     * @return found apartments
     */
    ArrayList<Apartment>     findFewApartmentsWhichAreBooked(String secondQueryPart, String... fields);

    /**
     * Update apartment
     * @param apartment apartment to update
     * @return operation result
     */
    Boolean                  updateApartment(Apartment apartment);
    /**
     * Delete apartment
     * @param apartment apartment to delete
     * @return operation result
     */
    Boolean                  deleteApartment(Apartment apartment);

    /** Get last statement rows count
     * @return last statement rows count
     */
    int                      getRowsNumber();
}
