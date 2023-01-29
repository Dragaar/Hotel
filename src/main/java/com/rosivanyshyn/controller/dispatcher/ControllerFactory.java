package com.rosivanyshyn.controller.dispatcher;

import com.rosivanyshyn.controller.authorization.LoginController;
import com.rosivanyshyn.controller.authorization.LogoutController;
import com.rosivanyshyn.controller.authorization.RegistrationController;
import com.rosivanyshyn.controller.other.langueage.ChangeLanguageController;
import com.rosivanyshyn.controller.other.apartment.DeleteApartmentController;
import com.rosivanyshyn.controller.other.apartment.GetApartmentDetailsController;
import com.rosivanyshyn.controller.other.apartment.GetFewApartmentsController;
import com.rosivanyshyn.controller.other.booking.*;
import com.rosivanyshyn.controller.other.order.*;
import com.rosivanyshyn.controller.other.responseToOrder.CreateResponseToOrderController;
import com.rosivanyshyn.controller.other.responseToOrder.GetCreateResponseToOrderController;
import com.rosivanyshyn.controller.other.responseToOrder.GetResponseToOrderController;

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

            case GET_BOOKINGS_CONTROLLER:
                return new GetFewBookingController();
            case GET_ALL_BOOKINGS_CONTROLLER:
                return new GetAllBookingController();
            case NEW_BOOKING_CONTROLLER:
                return new GetCreateBookingFormController();
            case CREATE_BOOKING_CONTROLLER:
                return new CreateBookingController();
            case MAKE_PAYMENT_FOR_BOOKING:
                return new PaymentForBooking();
            case DELETE_BOOKING_CONTROLLER:
                return new DeleteBookingController();



            case GET_ORDERS_CONTROLLER:
                return new GetFewOrdersController();
            case GET_ALL_ORDERS_CONTROLLER:
                return new GetAllOrdersController();
            case NEW_ORDER_CONTROLLER:
                return new GetCreateOrderFormController();
            case CREATE_ORDER_CONTROLLER:
                return new CreateOrderController();
            case DELETE_ORDER_CONTROLLER:
                return new DeleteOrderController();


            case GET_RESPONSE_TO_ORDER_CONTROLLER:
                return new GetResponseToOrderController();
            case NEW_RESPONSE_TO_ORDER_CONTROLLER:
                return new GetCreateResponseToOrderController();
            case CREATE_RESPONSE_TO_ORDER_CONTROLLER:
                return new CreateResponseToOrderController();


            case CHANGE_LANGUAGE_CONTROLLER:
                return new ChangeLanguageController();
            default:
        }
        return null;
    }
}
