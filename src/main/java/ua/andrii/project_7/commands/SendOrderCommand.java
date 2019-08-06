package ua.andrii.project_7.commands;

import org.apache.log4j.Logger;
import ua.andrii.project_7.entity.Ticket;
import ua.andrii.project_7.entity.User;
import ua.andrii.project_7.exception.WrongOrderDataException;
import ua.andrii.project_7.service.AdminService;
import ua.andrii.project_7.service.ClientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

public class SendOrderCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(AdminService.class);
    private final ClientService clientService;

    public SendOrderCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("SendOrderCommand()");
        HttpSession session = request.getSession(true);
        User user = (User) request.getSession().getAttribute("user");

        List buylist =
                (List) session.getAttribute("shoppingcart");
        BigDecimal total = new BigDecimal("0.00");
        for (Object item : buylist) {
            Ticket anOrder = (Ticket) item;
            BigDecimal price = anOrder.getShowroom().getPrice();
            int qty = anOrder.getTicketQuantity();
            total = total.add(price.multiply(new BigDecimal(qty)));
        }

        try {
            boolean result = clientService.addNewOrder(buylist, user, total);
            if (result) {

                request.setAttribute("message", "Sending is successful. " + "order is added");

                buylist.clear();
                session.setAttribute("shoppingcart", buylist);
                List showroomsList = clientService.getShowrooms();
                request.setAttribute("showroomslist", showroomsList);

                return "/client_page.jsp";

            } else {
                request.setAttribute("error", "An internal error occurred while trying to send a new order");
            }
        } catch (WrongOrderDataException e) {
            request.setAttribute("error", e.getMessage());
        }

        return "/client_page.jsp";
    }
}
