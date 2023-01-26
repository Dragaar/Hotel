package com.rosivanyshyn.controller.other.order;

import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;

import com.rosivanyshyn.db.dao.constant.AccountRole;
import com.rosivanyshyn.db.dao.entity.Order;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.OrderService;
import com.rosivanyshyn.service.implMySQL.OrderServiceImpl;
import com.rosivanyshyn.utils.MySQLQueryBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;

import java.util.ArrayList;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.ALL_ORDERS_JSP;

public class GetAllOrdersController implements Controller {

    OrderService orderService = new OrderServiceImpl();
    int pageId, recordsPerPage, currentRecord;
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        try {
            HttpSession session = request.getSession(false);
            @NonNull final AccountRole role = (AccountRole) session.getAttribute("role");

            if(role.equals(AccountRole.MANAGER)) {
                ArrayList<Order> orders;

                getPageConfig(request);

                MySQLQueryBuilder queryBuilder = new MySQLQueryBuilder();
                queryBuilder.setLabel("order");
                queryBuilder.limit(currentRecord, recordsPerPage);
                orders = orderService.findFewOrdersAndSort(queryBuilder.getQuery());

                //orders = orderService.findFewOrders(currentRecord, recordsPerPage);
                request.setAttribute("orders", orders);

                request.setAttribute("page", pageId);
                //for pagination links
                request.setAttribute("currentController", "getAllOrders");

                //Pagination totalPagesCount
                int totalRecordCount = orderService.getRowsNumber();
                int totalPagesCount = (int) Math.ceil(totalRecordCount * 1.0 / recordsPerPage);
                System.out.println("totalPagesCount = "+totalPagesCount);
                request.setAttribute("totalPagesCount", totalPagesCount);

                resolver.forward(ALL_ORDERS_JSP);
            }
        } catch (RuntimeException ex){
            throw new AppException("Cannot get All Orders", ex);
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
