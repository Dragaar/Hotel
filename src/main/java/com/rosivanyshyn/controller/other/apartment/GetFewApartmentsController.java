package com.rosivanyshyn.controller.other.apartment;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.constant.Field;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.exeption.ValidationException;
import com.rosivanyshyn.service.ApartmentService;
import com.rosivanyshyn.utils.MySQLQueryBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;

import java.util.ArrayList;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;
import static com.rosivanyshyn.exeption.Message.APARTMENTS_GET_ERROR;
import static com.rosivanyshyn.exeption.Message.INCORRECT_SORTING_ORDER;

/** Get Few Apartments Controller class.
 * <br> Get apartments (paginated) by sorting parameters and forward to apartments JSP
 *
 * @author Rostyslav Ivanyshyn.
 */
public class GetFewApartmentsController implements Controller {
    ApartmentService apartmentService;
    int pageId, recordsPerPage, currentRecord;
    boolean findBookedApartment = false;
    boolean findFreeApartment = false;

    public GetFewApartmentsController(AppContext appContext){
        apartmentService = appContext.getApartmentService();
    }
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        try {
            @NonNull HttpSession session = request.getSession(false);

            MySQLQueryBuilder queryBuilder = new MySQLQueryBuilder();
            queryBuilder.setLabel(Field.APARTMENT);
            
            //Sorting
            sortingOrder(request, session, queryBuilder);

            //Pagination
            getPageConfig(request);
            request.setAttribute("page", pageId);
            request.setAttribute("currentController", "getApartments");
            queryBuilder.limit(currentRecord, recordsPerPage);

            //Result list
            ArrayList<Apartment> apartments = null;
            if(findBookedApartment) {
                apartments = apartmentService.findFewApartmentsWhichAreBooked(
                        queryBuilder.getQuery());
            }
            else if(findFreeApartment) {
                apartments = apartmentService.findFewApartmentsWhichAreFree(
                        queryBuilder.getQuery());
            }
            else {
                apartments = apartmentService.findFewApartmentsAndSort(
                        queryBuilder.getQuery());
            }


            request.setAttribute("apartments", apartments);

            //Pagination totalPagesCount
            int totalRecordCount = apartmentService.getRowsNumber();
            int totalPagesCount = (int) Math.ceil(totalRecordCount * 1.0 / recordsPerPage);

            request.setAttribute("totalPagesCount", totalPagesCount);

            resolver.forward(APARTMENTS_JSP);

        } catch (RuntimeException ex){
            throw new AppException(APARTMENTS_GET_ERROR, ex);
        }
        return resolver;
    }

    private void sortingOrder(HttpServletRequest request, HttpSession session, MySQLQueryBuilder queryBuilder) {
        if(request.getParameter("newSortingOrder") !=null)
        {
            performSorting(queryBuilder,
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

            performSorting(queryBuilder,
                (String) session.getAttribute("price"),
                (String) session.getAttribute("maxGuestsNumber"),
                (String) session.getAttribute("class"),
                (String) session.getAttribute("status")
            );
        }
    }

    private void performSorting(MySQLQueryBuilder queryBuilder,
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
            case "Free" -> findFreeApartment = true;

            case "Booked" -> findBookedApartment = true;

            case "Busy" -> findBookedApartment = true;

            case "Unavailable" -> queryBuilder.order(
                    SortingFields.STATUS_AVAILABILITY.getField(),
                    false);
            default ->  throw new ValidationException(INCORRECT_SORTING_ORDER, new RuntimeException());
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
            throw new ValidationException(INCORRECT_SORTING_ORDER, new RuntimeException());
        }
    }

    private void setAttributeInSession(HttpSession session, String attributeName, Object attribute){
        if(attribute!=null){
            session.setAttribute(attributeName, attribute);
        }else {
            session.removeAttribute(attributeName);
        }
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

}
