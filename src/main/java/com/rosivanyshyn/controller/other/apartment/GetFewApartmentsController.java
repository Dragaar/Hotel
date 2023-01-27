package com.rosivanyshyn.controller.other.apartment;

import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.constant.Field;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.exeption.ValidationException;
import com.rosivanyshyn.service.ApartmentService;
import com.rosivanyshyn.service.implMySQL.ApartmentServiceImpl;
import com.rosivanyshyn.utils.MySQLQueryBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;

public class GetFewApartmentsController implements Controller {
    ApartmentService apartmentService = new ApartmentServiceImpl();
    int pageId, recordsPerPage, currentRecord;
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        try {
            HttpSession session = request.getSession(true);

            MySQLQueryBuilder queryBuilder = new MySQLQueryBuilder();
            queryBuilder.setLabel(Field.APARTMENT);

            //Search
            /*if(request.getParameter("newSearch") !=null)
            {
                queryBuilder.where(Field.APARTMENT_IMAGE_URL, true);
            }*/

            //Sorting
            if(request.getParameter("newSortingOrder") !=null)
            {
                performSorting(request, queryBuilder,
                    request.getParameter("price"),
                    request.getParameter("maxGuestsNumber"),
                    request.getParameter("class"),
                    request.getParameter("status")
                );
                setAttributeInSession(session, "existingSortingOrder", "exist");

                setAttributeInSession(session, "price", request.getParameter("price"));
                setAttributeInSession(session, "maxGuestsNumber", request.getParameter("maxGuestsNumber"));
                setAttributeInSession(session, "class", request.getParameter("class"));
                setAttributeInSession(session, "status", request.getParameter("status"));

            } else if (session.getAttribute("existingSortingOrder")!=null) {

                performSorting(request, queryBuilder,
                    (String) session.getAttribute("price"),
                    (String) session.getAttribute("maxGuestsNumber"),
                    (String) session.getAttribute("class"),
                    (String)session.getAttribute("status")
                );
            }

            //Pagination
            getPageConfig(request);
            request.setAttribute("page", pageId);
            request.setAttribute("currentController", "getApartments");
            queryBuilder.limit(currentRecord, recordsPerPage);



            //Result list
            ArrayList<Apartment> apartments = apartmentService.findFewApartmentsAndSort(queryBuilder.getQuery());
            request.setAttribute("apartments", apartments);

            //Pagination totalPagesCount
            int totalRecordCount = apartmentService.getRowsNumber();
            int totalPagesCount = (int) Math.ceil(totalRecordCount * 1.0 / recordsPerPage);

            request.setAttribute("totalPagesCount", totalPagesCount);

            resolver.forward(APARTMENTS_JSP);

        } catch (RuntimeException ex){
            throw new AppException("Cannot get Apartments", ex);
        }
        return resolver;
    }

    private void getPageConfig(HttpServletRequest request) {
        String reqPageId = request.getParameter("page");
        String reqRecordsPerPage = request.getParameter("recordsPerPage");

        if(reqPageId==null ){
            pageId = 1;
            currentRecord=1;
        } else {
            pageId = Integer.parseInt(reqPageId);
            currentRecord = pageId;
        }

        if(reqRecordsPerPage==null){
            recordsPerPage = 8;
        } else {
            recordsPerPage = Integer.parseInt(reqRecordsPerPage);
        }

        if(pageId>1)
        {
            int temp = pageId-1;
            currentRecord = temp*recordsPerPage+1;
        }
    }

    private void performSorting(HttpServletRequest request, MySQLQueryBuilder queryBuilder,
                                String price, String maxGuestsNumber, String apartmentClass, String status)
    {

        if(price != null)
        {
            queryBuilder.order(
                    SortingFields.PRICE.getField(),
                    getSortingOrder(price)
                    );
        }
        if(maxGuestsNumber!= null)
        {
            queryBuilder.order(
                    SortingFields.MAX_GUESTS_NUMBER.getField(),
                    getSortingOrder(maxGuestsNumber)
            );
        }
        if(apartmentClass!= null)
        {
            queryBuilder.order(
                    SortingFields.CLASS.getField(),
                    getSortingOrder(apartmentClass)
            );
        }
        if(status!= null)
        {
            switch (status)
            {
            case "Free" -> {
                    queryBuilder.order(
                        SortingFields.STATUS_AVAILABILITY.getField(),
                        true);
                    queryBuilder.excludeJoin(Field.BOOKING,
                        Field.BOOKING,
                        Field.BOOKING_APARTMENT_ID,
                        Field.ENTITY_ID
                    );
            }
            case "Booked" -> queryBuilder.join(Field.BOOKING,
                Field.BOOKING,
                Field.BOOKING_APARTMENT_ID,
                Field.ENTITY_ID
            );
            case "Busy" -> queryBuilder.join(Field.BOOKING,
                    Field.BOOKING,
                    Field.BOOKING_APARTMENT_ID,
                    Field.ENTITY_ID
            );
            case "Unavailable" -> queryBuilder.order(
                    SortingFields.STATUS_AVAILABILITY.getField(),
                    false);
            default ->  throw new ValidationException("Input sorting order doesn`t exist", new RuntimeException());
            }
        }
    }

    Boolean getSortingOrder(String order){
        if(order.equals("Asc"))
        {
           return false;
        } else if(order.equals("Desc"))
        {
            return true;
        } else {
            throw new ValidationException("Input sorting order doesn`t exist", new RuntimeException());
        }
    }

    private void setAttributeInSession(HttpSession session, String attributeName, Object attribute){
        if(attribute!=null){
            session.setAttribute(attributeName, attribute);
        }else {
            session.removeAttribute(attributeName);
        }
    }


}
