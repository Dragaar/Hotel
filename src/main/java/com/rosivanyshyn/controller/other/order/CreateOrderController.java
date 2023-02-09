package com.rosivanyshyn.controller.other.order;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.entity.Order;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.exeption.ValidationException;
import com.rosivanyshyn.service.OrderService;
import com.rosivanyshyn.utils.Validation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;

import java.sql.Date;
import java.time.LocalDate;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.GET_APARTMENTS_CONTROLLER;

/** Create Order Controller class.
 * <br> Add new order to database and redirect to apartments JSP.
 *
 * @author Rostyslav Ivanyshyn.
 */
public class CreateOrderController implements Controller {
    OrderService orderService = AppContext.getInstance().getOrderService();


    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        try {
            HttpSession session = request.getSession(false);

            @NonNull final Long accountId = (Long) session.getAttribute("id");

            Account account = new Account();
            account.setId(accountId);

                @NonNull final Integer guestsNumber = Integer.valueOf(request.getParameter("guestsNumber"));
                @NonNull final String roomsNumber = request.getParameter("roomsNumber");
                @NonNull final String apartmentClass = request.getParameter("apartmentClass");
                @NonNull final Long price = Long.valueOf(request.getParameter("price"));
                @NonNull final String description = request.getParameter("description");
                @NonNull final LocalDate  checkInDate = LocalDate.parse(
                        request.getParameter("checkInDate"));
                @NonNull final LocalDate checkOutDate = LocalDate.parse(
                        request.getParameter("checkOutDate"));

                Order order = Order.builder()
                        .id(0L)
                        .guestsNumber(guestsNumber)
                        .roomsNumber(roomsNumber)
                        .apartmentClass(apartmentClass)
                        .price(price)
                        .description(description)
                        .checkInDate(Date.valueOf(checkInDate))
                        .checkOutDate(Date.valueOf(checkOutDate))
                        //reservation data auto-generated
                        //foreign keys
                        .account(account)
                        //responseToOrder is null
                        .build();

                Validation validation = new Validation();
                validation.validateOrder(order);


                 orderService.createOrder(order);

            resolver.redirect(request.getContextPath()+"/front?controller="+ GET_APARTMENTS_CONTROLLER +
                    "&message=" + "app.message.order.create");

        } catch (ValidationException ex){
            throw new ValidationException(ex.getMessage(), ex);
        } catch (RuntimeException ex){
            throw new AppException("Cannot create order", ex);
        }
        return resolver;
    }
}
