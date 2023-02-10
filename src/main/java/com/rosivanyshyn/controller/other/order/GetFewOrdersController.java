package com.rosivanyshyn.controller.other.order;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;

import com.rosivanyshyn.db.dao.entity.Order;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.OrderService;
import com.rosivanyshyn.utils.MySQLQueryBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;

import java.util.ArrayList;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.ORDERS_JSP;
import static com.rosivanyshyn.db.dao.constant.Field.*;

/** Get Few Orders Controller class.
 * <br> Get current user orders (paginated) and forward to orders JSP.
 *
 * @author Rostyslav Ivanyshyn.
 */
public class GetFewOrdersController implements Controller {

    OrderService orderService;
    int pageId, recordsPerPage, currentRecord;
    public GetFewOrdersController(AppContext appContext){
        orderService = appContext.getOrderService();
    }
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        try {
            ArrayList<Order> orders;

            getPageConfig(request);

            HttpSession session = request.getSession(false);
            @NonNull final Long accountId = (Long) session.getAttribute("id");

            MySQLQueryBuilder queryBuilder = new MySQLQueryBuilder();
            queryBuilder.setLabel("order");
            queryBuilder.where(ORDER_ACCOUNT_ID, true);
            queryBuilder.limit(currentRecord, recordsPerPage);
            orders = orderService.findFewOrdersAndSort(queryBuilder.getQuery(), accountId);

            //orders = orderService.findFewOrders(currentRecord, recordsPerPage);
            request.setAttribute("orders", orders);

            request.setAttribute("page", pageId);
            //for pagination links
            request.setAttribute("currentController", "getOrders");

            //Pagination totalPagesCount
            int totalRecordCount = orderService.getRowsNumber();
            int totalPagesCount = (int) Math.ceil(totalRecordCount * 1.0 / recordsPerPage);

            request.setAttribute("totalPagesCount", totalPagesCount);

            resolver.forward(ORDERS_JSP);

        } catch (RuntimeException ex){
            throw new AppException("Cannot get Orders", ex);
        }
        return resolver;
    }

    private void getPageConfig(HttpServletRequest request) {
        String reqPageId = request.getParameter("page");
        String reqRecordsPerPage = request.getParameter("recordsPerPage");

        if(reqPageId==null ){
            pageId = 1;
            currentRecord=1;
        } else {
            pageId = Integer.parseInt(reqPageId);
            currentRecord = pageId;
        }

        if(reqRecordsPerPage==null){
            recordsPerPage = 8;
        } else {
            recordsPerPage = Integer.parseInt(reqRecordsPerPage);
        }

        if(pageId>1)
        {
            int temp = pageId-1;
            currentRecord = temp*recordsPerPage+1;
        }
    }
}
