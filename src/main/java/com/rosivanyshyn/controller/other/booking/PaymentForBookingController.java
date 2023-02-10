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
import lombok.NonNull;

import java.util.ArrayList;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.GET_APARTMENTS_CONTROLLER;
import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.BOOKING_SUCCEED_PAYMENT;
import static com.rosivanyshyn.db.dao.constant.Field.ENTITY_ID;
import static com.rosivanyshyn.exeption.Message.BOOKING_PAYMENT_ERROR;

/** Payment For Booking Controller class.
 * <br> Make payment for specific booking
 *
 * @author Rostyslav Ivanyshyn.
 */
public class PaymentForBookingController implements Controller {

    BookingService bookingService;
    public PaymentForBookingController(AppContext appContext){
        bookingService = appContext.getBookingService();
    }
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();
        try {

            @NonNull final Long bookingId = Long.valueOf(request.getParameter("bookingId"));

            MySQLQueryBuilder queryBuilder = new MySQLQueryBuilder();
            queryBuilder.setLabel("booking");
            queryBuilder.where(ENTITY_ID, true);

            @NonNull ArrayList<Booking> booking = bookingService.findFewBookingAndSort(queryBuilder.getQuery(), bookingId);

            booking.get(0).setIsPaidForReservation(true);
            bookingService.updateBooking(booking.get(0));


            resolver.redirect(request.getContextPath() + "/front?controller="+ GET_APARTMENTS_CONTROLLER +
                    "&message=" + BOOKING_SUCCEED_PAYMENT);

        } catch (RuntimeException ex){
            throw new AppException(BOOKING_PAYMENT_ERROR, ex);
        }
        return resolver;
    }
}
