package com.rosivanyshyn.controller.other.booking;

import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.Booking;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.ApartmentService;
import com.rosivanyshyn.service.BookingService;
import com.rosivanyshyn.service.implMySQL.ApartmentServiceImpl;
import com.rosivanyshyn.service.implMySQL.BookingServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;

import java.sql.Date;
import java.time.LocalDate;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;
import static com.rosivanyshyn.db.dao.constant.Field.*;

public class CreateBookingController implements Controller {
    ApartmentService apartmentService = new ApartmentServiceImpl();
    BookingService bookingService = new BookingServiceImpl();


    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        try {
            HttpSession session = request.getSession(false);

            @NonNull final Long accountId = (Long) session.getAttribute("id");
            @NonNull final Long apartmentId = Long.valueOf(request.getParameter("apartmentId"));
            Apartment apartment = apartmentService.findApartmentByField(ENTITY_ID, apartmentId);
            Account account = new Account();
            account.setId(accountId);

                @NonNull final String guestsNumber = request.getParameter("guestsNumber");
                @NonNull final LocalDate  checkInDate = LocalDate.parse(
                        request.getParameter("checkInDate"));
                @NonNull final LocalDate checkOutDate = LocalDate.parse(
                        request.getParameter("checkOutDate"));
                //@NonNull final String isPaidForReservation = request.getParameter("password");

                Booking booking = Booking.builder()
                        .id(0L)
                        .guestsNumber(guestsNumber)
                        .checkInDate(Date.valueOf(checkInDate))
                        .checkOutDate(Date.valueOf(checkOutDate))
                        .isPaidForReservation(false)
                        //reservation data auto-generated
                        //foreign keys
                        .account(account)
                        .apartment(apartment)
                        .build();

                bookingService.createBooking(booking);

            resolver.redirect(request.getContextPath()+"/front?controller="+ GET_APARTMENTS_CONTROLLER +
                    "&message=" + "app.message.booking.create");

        } catch (RuntimeException ex){
            throw new AppException("Cannot booking apartment", ex);
        }
        return resolver;
    }
}
