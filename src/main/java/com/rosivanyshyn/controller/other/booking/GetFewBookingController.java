package com.rosivanyshyn.controller.other.booking;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Booking;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.BookingService;
import com.rosivanyshyn.utils.MySQLQueryBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;

import java.util.ArrayList;

import static com.rosivanyshyn.utils.MySQLQueryBuilder.LogicalOperation.*;
import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.BOOKINGS_JSP;
import static com.rosivanyshyn.db.dao.constant.Field.BOOKING_ACCOUNT_ID;
import static com.rosivanyshyn.exeption.Message.BOOKINGS_GET_ERROR;

/** Get Few Booking Controller class.
 * <br> Get current user bookings (paginated) and forward to bookings JSP
 *
 * @author Rostyslav Ivanyshyn.
 */
public class GetFewBookingController implements Controller {

    BookingService bookingService;
    int pageId, recordsPerPage, currentRecord;
    public GetFewBookingController(AppContext appContext){
        bookingService = appContext.getBookingService();
    }
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        try {
            HttpSession session = request.getSession(false);
            @NonNull final Long accountId = (Long) session.getAttribute("id");

            ArrayList<Booking> bookings;

            getPageConfig(request);

            MySQLQueryBuilder queryBuilder = new MySQLQueryBuilder();
            queryBuilder.setLabel("booking");
            queryBuilder.where(BOOKING_ACCOUNT_ID, EQUAL, true);
            queryBuilder.limit(currentRecord, recordsPerPage);
            bookings = bookingService.findFewBookingAndSort(queryBuilder.getQuery(), accountId);

            //orders = orderService.findFewOrders(currentRecord, recordsPerPage);
            request.setAttribute("bookings", bookings);

            request.setAttribute("page", pageId);
            //for pagination links
            request.setAttribute("currentController", "getBookings");

            //Pagination totalPagesCount
            int totalRecordCount = bookingService.getRowsNumber();
            int totalPagesCount = (int) Math.ceil(totalRecordCount * 1.0 / recordsPerPage);

            request.setAttribute("totalPagesCount", totalPagesCount);

            resolver.forward(BOOKINGS_JSP);

        } catch (RuntimeException ex){
            throw new AppException(BOOKINGS_GET_ERROR, ex);
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
