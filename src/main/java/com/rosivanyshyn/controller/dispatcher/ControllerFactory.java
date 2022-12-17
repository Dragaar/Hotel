package com.rosivanyshyn.controller.dispatcher;

import com.rosivanyshyn.controller.authorization.LoginController;
import com.rosivanyshyn.controller.authorization.LogoutController;
import com.rosivanyshyn.controller.authorization.RegistrationController;
import com.rosivanyshyn.controller.other.apartment.DeleteApartmentController;
import com.rosivanyshyn.controller.other.apartment.GetApartmentDetailsController;
import com.rosivanyshyn.controller.other.apartment.GetFewApartmentsController;
import com.rosivanyshyn.controller.other.booking.CreateBookingController;
import com.rosivanyshyn.controller.other.booking.GetBookingFormController;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;
public class ControllerFactory {
    public ControllerFactory() {

    }

    public Controller getController(final String controllerName) {

        switch (controllerName) {
            case LOGIN_CONTROLLER:
                return new LoginController();
            case LOGOUT_CONTROLLER:
                return new LogoutController();
            case REGISTRATION_CONTROLLER:
                return new RegistrationController();

            case GET_APARTMENTS_CONTROLLER:
                return new GetFewApartmentsController();
            case GET_APARTMENT_DETAILS_CONTROLLER:
                return new GetApartmentDetailsController();
            case DELETE_APARTMENT_CONTROLLER:
                return new DeleteApartmentController();

            case NEW_BOOKING_CONTROLLER:
                return new GetBookingFormController();
            case CREATE_BOOKING_CONTROLLER:
                return new CreateBookingController();

            default:
        }
        return null;
    }
}
