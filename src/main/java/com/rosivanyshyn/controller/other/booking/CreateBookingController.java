package com.rosivanyshyn.controller.other.booking;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.Booking;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.exeption.ValidationException;
import com.rosivanyshyn.service.ApartmentService;
import com.rosivanyshyn.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;

import java.sql.Date;
import java.time.LocalDate;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;
import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.BOOKING_SUCCEED_CREATE;
import static com.rosivanyshyn.db.dao.constant.Field.*;
import static com.rosivanyshyn.exeption.Message.BOOKING_CREATE_ERROR;

/** Create Booking Controller class.
 * <br> Add new booking to database and redirect to apartments JSP
 *
 * @author Rostyslav Ivanyshyn.
 */
public class CreateBookingController implements Controller {
    ApartmentService apartmentService;
    BookingService bookingService;
    public CreateBookingController(AppContext appContext){
        apartmentService = appContext.getApartmentService();
        bookingService = appContext.getBookingService();
    }
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        try {
            HttpSession session = request.getSession(false);

            @NonNull final Long accountId = (Long) session.getAttribute("id");
            Account account = new Account();
            account.setId(accountId);

            @NonNull final Long apartmentId = Long.valueOf(request.getParameter("apartmentId"));
            Apartment apartment = apartmentService.findApartmentByField(ENTITY_ID, apartmentId);


                @NonNull final String guestsNumber = request.getParameter("guestsNumber");
                @NonNull final LocalDate  checkInDate = LocalDate.parse(
                        request.getParameter("checkInDate"));
                @NonNull final LocalDate checkOutDate = LocalDate.parse(
                        request.getParameter("checkOutDate"));

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

               //Validation inside service

                bookingService.createBooking(booking);

            resolver.redirect(request.getContextPath()+ INITIALIZE_CONTROLLER + GET_APARTMENTS_CONTROLLER +
                    "&message=" + BOOKING_SUCCEED_CREATE);

        } catch (ValidationException ex){
            throw new ValidationException(ex.getMessage(), ex);
        } catch (RuntimeException ex){
            throw new AppException(BOOKING_CREATE_ERROR, ex);
        }
        return resolver;
    }
}
