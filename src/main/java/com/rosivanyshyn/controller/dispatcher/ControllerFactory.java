package com.rosivanyshyn.controller.dispatcher;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.security.LoginController;
import com.rosivanyshyn.controller.security.LogoutController;
import com.rosivanyshyn.controller.security.RegistrationController;
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

/**
 * Controller Factory class. Contains all available controller and method to get any of them.
 *
 * @author Rostyslav Ivanyshyn.
 */
public class ControllerFactory {
    AppContext appContext;
    public ControllerFactory() {
        appContext = AppContext.getInstance();
    }

    public Controller getController(final String controllerName) {

        switch (controllerName) {
            case LOGIN_CONTROLLER:
                return new LoginController(appContext);
            case LOGOUT_CONTROLLER:
                return new LogoutController();
            case REGISTRATION_CONTROLLER:
                return new RegistrationController(appContext);

            case GET_APARTMENTS_CONTROLLER:
                return new GetFewApartmentsController(appContext);
            case GET_APARTMENT_DETAILS_CONTROLLER:
                return new GetApartmentDetailsController(appContext);
            case DELETE_APARTMENT_CONTROLLER:
                return new DeleteApartmentController(appContext);

            case GET_BOOKINGS_CONTROLLER:
                return new GetFewBookingController(appContext);
            case GET_ALL_BOOKINGS_CONTROLLER:
                return new GetAllBookingController(appContext);
            case NEW_BOOKING_CONTROLLER:
                return new GetCreateBookingFormController();
            case CREATE_BOOKING_CONTROLLER:
                return new CreateBookingController(appContext);
            case MAKE_PAYMENT_FOR_BOOKING:
                return new PaymentForBookingController(appContext);
            case DELETE_BOOKING_CONTROLLER:
                return new DeleteBookingController(appContext);



            case GET_ORDERS_CONTROLLER:
                return new GetFewOrdersController(appContext);
            case GET_ALL_ORDERS_CONTROLLER:
                return new GetAllOrdersController(appContext);
            case NEW_ORDER_CONTROLLER:
                return new GetCreateOrderFormController();
            case CREATE_ORDER_CONTROLLER:
                return new CreateOrderController(appContext);
            case DELETE_ORDER_CONTROLLER:
                return new DeleteOrderController(appContext);


            case GET_RESPONSE_TO_ORDER_CONTROLLER:
                return new GetResponseToOrderController(appContext);
            case NEW_RESPONSE_TO_ORDER_CONTROLLER:
                return new GetCreateResponseToOrderController(appContext);
            case CREATE_RESPONSE_TO_ORDER_CONTROLLER:
                return new CreateResponseToOrderController(appContext);


            case CHANGE_LANGUAGE_CONTROLLER:
                return new ChangeLanguageController();
            default:
        }
        return null;
    }
}
