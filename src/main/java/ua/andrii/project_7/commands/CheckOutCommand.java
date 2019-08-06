package ua.andrii.project_7.commands;

import org.apache.log4j.Logger;
import ua.andrii.project_7.entity.Ticket;
import ua.andrii.project_7.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

public class CheckOutCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(AdminService.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("CheckOutCommand()");
        HttpSession session = request.getSession(true);

        List buylist =
                (List) session.getAttribute("shoppingcart");
        BigDecimal total = new BigDecimal("0.00");

        for (Object item : buylist) {
            Ticket anOrder = (Ticket) item;
            BigDecimal price = anOrder.getShowroom().getPrice();
            int qty = anOrder.getTicketQuantity();
            total = total.add(price.multiply(new BigDecimal(qty)));
        }
        total.add(new BigDecimal("0.005"));
        String amount = total.toString();
        int n = amount.indexOf('.');
        amount = amount.substring(0, n + 3);

        request.setAttribute("amount", amount);

        return "/order_checkout.jsp";
    }
}
