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
    int pageId, recordsPerPage, currentRecord, totalRecordCount, totalPagesCount;
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        try {
            MySQLQueryBuilder queryBuilder = new MySQLQueryBuilder();
            queryBuilder.setLabel("apartment");

            //Search
            /*if(request.getParameter("newSearch") !=null)
            {
                queryBuilder.where(Field.APARTMENT_IMAGE_URL, true);
            }*/

            //Sorting
            if(request.getParameter("newSortingOrder") !=null)
            {
                Map<SortingFields, Boolean> sortingParamMap = getSortingParameters(request);
                System.out.println("sorting");
                System.out.println( request.getParameter("sorting"));

                if(!sortingParamMap.isEmpty()) {
                for (Map.Entry<SortingFields, Boolean> entry : sortingParamMap.entrySet()) {
                    //TODO зробити окрему обробку state
                    System.out.println("sorting for-each");
                    queryBuilder.order(entry.getKey().getField(), entry.getValue());
                }

                }
            }

            //Pagination
            getPageConfig(request);
            request.setAttribute("page", pageId);
            request.setAttribute("currentController", "getApartments");
            queryBuilder.limit(currentRecord, recordsPerPage);

            //Result list
            ArrayList<Apartment> apartments = apartmentService.findFewApartmentsAndSort(queryBuilder.getQuery());
            request.setAttribute("apartments", apartments);


           /* ArrayList<Apartment> apartments;
            getPageConfig(request);
            apartments = apartmentService.findFewApartment(currentRecord, recordsPerPage);
            request.setAttribute("apartments", apartments);
            */

            //for pagination links

            //totalRecordCount should work with search, pagination of the table and user filters
            //int totalRecordCount = apartmentService.getRecordCount();
            //int totalPagesCount = (int) Math.ceil(totalRecordCount * 1.0 / recordsPerPage);
            //request.setAttribute("totalPagesCount", apartments);


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

    private Map<SortingFields, Boolean> getSortingParameters(HttpServletRequest request)
    {
        Map<SortingFields, Boolean> sortingParametersMap = new HashMap<>();
        if(request.getParameter("price")!= null)
        {
            sortingParametersMap.put(SortingFields.PRICE,
                    getSortingOrder(request.getParameter("price"))
            );
        }
        if(request.getParameter("maxGuestsNumber")!= null)
        {
            sortingParametersMap.put(SortingFields.MAX_GUESTS_NUMBER,
                    getSortingOrder(request.getParameter("maxGuestsNumber"))
            );
        }
        if(request.getParameter("class")!= null)
        {
            sortingParametersMap.put(SortingFields.CLASS,
                    getSortingOrder(request.getParameter("class"))
            );
        }
        if(request.getParameter("status")!= null)
        {
            sortingParametersMap.put(SortingFields.STATUS,
                    getSortingOrder(request.getParameter("status"))
            );
        }
        return sortingParametersMap;
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
}
