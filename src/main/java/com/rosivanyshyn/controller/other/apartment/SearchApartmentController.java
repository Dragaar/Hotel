package com.rosivanyshyn.controller.other.apartment;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.ApartmentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;

import java.util.ArrayList;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.APARTMENTS_JSP;
import static com.rosivanyshyn.exeption.Message.APARTMENTS_GET_ERROR;

public class SearchApartmentController implements Controller {
    ApartmentService apartmentService;
    int pageId, recordsPerPage, currentRecord;

    public SearchApartmentController(AppContext appContext) {
        apartmentService = appContext.getApartmentService();
    }

    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        try {
            @NonNull HttpSession session = request.getSession(false);
            
            //Sorting
            request.setAttribute("dontShowFilters", true);
            //-------

            //Pagination
            getPageConfig(request);
            request.setAttribute("page", pageId);
            request.setAttribute("currentController", "searchApartment");

            //Result list
            ArrayList<Apartment> apartments = null;

            apartments = performSearch(session, request, apartments);


            request.setAttribute("apartments", apartments);

            //Pagination totalPagesCount
            int totalRecordCount = apartmentService.getRowsNumber();
            int totalPagesCount = (int) Math.ceil(totalRecordCount * 1.0 / recordsPerPage);

            request.setAttribute("totalPagesCount", totalPagesCount);

            resolver.forward(APARTMENTS_JSP);

        } catch (RuntimeException ex) {
            throw new AppException(APARTMENTS_GET_ERROR, ex);
        }
        return resolver;
    }

    private ArrayList<Apartment> performSearch(HttpSession session, HttpServletRequest request, ArrayList<Apartment> apartments) {

        @NonNull String searchValue = request.getParameter("searchValue");

        if(isNotBlank(searchValue)) {
            session.setAttribute("searchValue", searchValue);
            apartments = apartmentService.searchApartment(searchValue, currentRecord, recordsPerPage);
        } else {
            searchValue = (String) session.getAttribute("searchValue");

            if(isNotBlank(searchValue))
            {
                apartments = apartmentService.searchApartment(searchValue, currentRecord, recordsPerPage);
            }
        }
        return apartments;
    }

    private void getPageConfig(HttpServletRequest request) {
        String reqPageId = request.getParameter("page");
        String reqRecordsPerPage = request.getParameter("recordsPerPage");

        if (reqPageId == null) {
            pageId = 1;
            currentRecord = 1;
        } else {
            pageId = Integer.parseInt(reqPageId);
            currentRecord = pageId;
        }

        if (reqRecordsPerPage == null) {
            recordsPerPage = 8;
        } else {
            recordsPerPage = Integer.parseInt(reqRecordsPerPage);
        }

        if (pageId > 1) {
            int temp = pageId - 1;
            currentRecord = temp * recordsPerPage + 1;
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isEmpty();
    }
    private boolean isNotBlank(String value) {
        return !isBlank(value);
    }

}
