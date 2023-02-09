package com.rosivanyshyn.controller.other.responseToOrder;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.constant.Field;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.ApartmentService;
import com.rosivanyshyn.utils.MySQLQueryBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

import java.util.ArrayList;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.NEW_RESPONSE_TO_ORDER_JSP;

/** Get Create Response To Order Controller class.
 * <br> Get JSP form (for moderator) for create new response-to-order.
 *
 * @author Rostyslav Ivanyshyn.
 */
public class GetCreateResponseToOrderController implements Controller {
    ApartmentService apartmentService = AppContext.getInstance().getApartmentService();
    int pageId, recordsPerPage, currentRecord;

    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        ArrayList<Apartment> apartments;

        //getPageConfig(request);

        try {
            @NonNull final Long orderId = Long.valueOf(request.getParameter("orderId"));
            request.setAttribute("orderId", orderId);

            MySQLQueryBuilder queryBuilder = new MySQLQueryBuilder();
            queryBuilder.setLabel(Field.APARTMENT);
            //queryBuilder.limit(currentRecord, recordsPerPage);

            apartments = apartmentService.findFewApartmentsAndSort(queryBuilder.getQuery());
            request.setAttribute("apartments", apartments);

            //request.setAttribute("page", pageId);
            //for pagination links
            //request.setAttribute("currentController", "newResponseToOrder");

            //Pagination totalPagesCount
            //int totalRecordCount = apartmentService.getRowsNumber();
            //int totalPagesCount = (int) Math.ceil(totalRecordCount * 1.0 / recordsPerPage);

            //request.setAttribute("totalPagesCount", totalPagesCount);


            resolver.forward(NEW_RESPONSE_TO_ORDER_JSP);
        } catch (RuntimeException ex) {
            throw new AppException("Cannot get create-response to order page", ex);
        }
        return resolver;
    }

    /*private void getPageConfig(HttpServletRequest request) {
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
    }*/
}
