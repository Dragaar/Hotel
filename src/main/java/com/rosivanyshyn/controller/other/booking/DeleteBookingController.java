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
import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;
import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.BOOKING_SUCCEED_DELETE;
import static com.rosivanyshyn.db.dao.constant.Field.ENTITY_ID;
import static com.rosivanyshyn.exeption.Message.BOOKING_DELETE_ERROR;

/** Delete Booking Controller class.
 * <br> Delete booking from database and redirect to apartments JSP
 *
 * @author Rostyslav Ivanyshyn.
 */
public class DeleteBookingController implements Controller {

    BookingService bookingService;
    public DeleteBookingController(AppContext appContext){
        bookingService = appContext.getBookingService();
    }
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();
        try {
            HttpSession session = request.getSession(false);
            @NonNull final Long accountId = (Long) session.getAttribute("id");

            @NonNull final Long bookingId = Long.valueOf(request.getParameter("bookingId"));

            MySQLQueryBuilder queryBuilder = new MySQLQueryBuilder();
            queryBuilder.setLabel("booking");
            queryBuilder.where(ENTITY_ID, EQUAL, true);
            @NonNull ArrayList<Booking> booking = bookingService.findFewBookingAndSort(queryBuilder.getQuery(), bookingId);

            if(booking.get(0).getAccount().getId().equals(accountId))
            {
                bookingService.deleteBooking(booking.get(0));
            }

            resolver.redirect(request.getContextPath() + INITIALIZE_CONTROLLER + GET_APARTMENTS_CONTROLLER +
                    "&message=" + BOOKING_SUCCEED_DELETE);

        } catch (RuntimeException ex){
            throw new AppException(BOOKING_DELETE_ERROR, ex);
        }
        return resolver;
    }
}
