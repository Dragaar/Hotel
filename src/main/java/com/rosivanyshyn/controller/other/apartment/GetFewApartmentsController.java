package com.rosivanyshyn.controller.other.apartment;

import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.exeption.ValidationException;
import com.rosivanyshyn.service.ApartmentService;
import com.rosivanyshyn.service.implMySQL.ApartmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.net.http.HttpRequest;
import java.util.ArrayList;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;

public class GetFewApartmentsController implements Controller {
    ApartmentService apartmentService = new ApartmentServiceImpl();
    int pageId, recordsPerPage, currentRecord, totalRecordCount, totalPagesCount;
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        try {
            ArrayList<Apartment> apartments;

            getPageConfig(request);

            apartments = apartmentService.findFewApartment(currentRecord, recordsPerPage);
            request.setAttribute("apartments", apartments);

            request.setAttribute("page", pageId);
            //for pagination links
            request.setAttribute("currentController", "getApartments");

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
}
