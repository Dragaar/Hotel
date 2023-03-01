package com.rosivanyshyn.controller.other.apartment;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.ApartmentService;
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
import static com.rosivanyshyn.db.dao.constant.Field.*;
import static com.rosivanyshyn.exeption.Message.APARTMENT_GET_DETAILS_ERROR;

/** Get Apartment Details Controller class.
 * <br> Get apartment by id and forward to apartment details JSP.
 * <br> Also get current date and existed booking dates for related apartment
 * @author Rostyslav Ivanyshyn.
 */
public class GetApartmentDetailsController implements Controller {

    ApartmentService apartmentService;
    BookingService bookingService;
    public GetApartmentDetailsController(AppContext appContext){
        apartmentService = appContext.getApartmentService();
        bookingService = appContext.getBookingService();
    }
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();
        @NonNull HttpSession session = request.getSession(false);
        DateUtil dateUtil = new DateUtil();

        try {

            @NonNull final Long apartmentId = Long.valueOf(request.getParameter("apartmentId"));
            Apartment apartment = apartmentService.findApartmentByField(ENTITY_ID, apartmentId);

            request.setAttribute("apartment", apartment);

            request.setAttribute("currentDate", LocalDate.now());
            HashMap<Date, Date> disabledDatesMap = setDisabledDatesOfRelatedApartmentInSession(session, apartmentId);
            ArrayList<LocalDate> disabledDatesList = dateUtil.convertDatesRangeToList(disabledDatesMap);

            request.setAttribute("disabledDatesList",  disabledDatesList);

            resolver.forward(APARTMENT_DETAILS_JSP);

        } catch (RuntimeException ex) {
            throw new AppException(APARTMENT_GET_DETAILS_ERROR, ex);
        }
        return resolver;
    }

    private HashMap<Date, Date> setDisabledDatesOfRelatedApartmentInSession(HttpSession session, Long requestedApartmentId) {
        HashMap<Date, Date> disabledDatesMap;

        disabledDatesMap = bookingService.getBookingsDatesFromDB(requestedApartmentId);
        session.setAttribute("apartmentIdOfBookingDates", requestedApartmentId);
        session.setAttribute("disabledDatesMap", disabledDatesMap);

        return disabledDatesMap;
    }
}
