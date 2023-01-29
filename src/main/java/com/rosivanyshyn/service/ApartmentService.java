package com.rosivanyshyn.service;

import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.entity.Apartment;

import java.util.ArrayList;

public interface ApartmentService {

    Boolean                  createApartment(Apartment apartment);

    Boolean                  isApartmentExist(Apartment apartment);
    Apartment                findApartmentByField(String field, Object value);
    ArrayList<Apartment>     findAllApartment();
    ArrayList<Apartment>     findFewApartment(int start, int total);

    ArrayList<Apartment>     findFewApartmentsAndSort(String secondQueryPart, String... fields);
    ArrayList<Apartment>     findFewApartmentsWhichAreBooked(String secondQueryPart, String... fields);

    Boolean                  updateApartment(Apartment apartment);
    Boolean                  deleteApartment(Apartment apartment);

    /** Get last statement rows count
     * @return last statement rows count
     */
    int                      getRowsNumber();
}
