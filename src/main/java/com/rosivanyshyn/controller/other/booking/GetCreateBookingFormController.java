package com.rosivanyshyn.controller.other.booking;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.exeption.AppException;

import com.rosivanyshyn.service.BookingService;
import com.rosivanyshyn.utils.DateUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;


import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;
import static com.rosivanyshyn.exeption.Message.BOOKING_GET_CREATE_PAGE_ERROR;

/** Get Create Booking Form Controller class.
 * <br> Get JSP form for create new booking by apartment id and prepare data to working there.
 * <br> Also get current date and bookings dates of related apartment (using requested apartment id)
 *
 * @author Rostyslav Ivanyshyn.
 */
public class GetCreateBookingFormController implements Controller {
    BookingService bookingService;

    public GetCreateBookingFormController(AppContext appContext){
        bookingService = appContext.getBookingService();
    }
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();
        @NonNull HttpSession session = request.getSession(false);
        DateUtil dateUtil = new DateUtil();

        try {
            @NonNull final Long requestedApartmentId = Long.valueOf(request.getParameter("apartmentId"));

            request.setAttribute("currentDate", LocalDate.now());

            HashMap<Date, Date> disabledDatesMap = getDisabledDatesOfRelatedApartmentFromSession(session, requestedApartmentId);
            ArrayList<LocalDate> disabledDatesList = dateUtil.convertDatesRangeToList(disabledDatesMap);

            request.setAttribute("disabledDatesList",  disabledDatesList);

            resolver.forward(NEW_BOOKING_JSP);
        } catch (RuntimeException ex) {
            throw new AppException(BOOKING_GET_CREATE_PAGE_ERROR, ex);
        }
        return resolver;
    }

    private HashMap<Date, Date> getDisabledDatesOfRelatedApartmentFromSession(HttpSession session, Long requestedApartmentId) {
        final Long apartmentIdOfDisabledDates = (Long) session.getAttribute("apartmentIdOfDisabledDates");

        @SuppressWarnings("unchecked")
        HashMap<Date, Date> disabledDatesMap = (HashMap<Date, Date>) session.getAttribute("disabledDatesMap");

        if(apartmentIdOfDisabledDates == null
                || disabledDatesMap == null
                || !apartmentIdOfDisabledDates.equals(requestedApartmentId))
        {
            disabledDatesMap = bookingService.getBookingsDatesFromDB(requestedApartmentId);
        }
        return disabledDatesMap;
    }
}