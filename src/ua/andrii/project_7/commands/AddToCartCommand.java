package ua.andrii.project_7.commands;

import org.apache.log4j.Logger;
import sun.security.timestamp.TimestampToken;
import ua.andrii.project_7.entity.Exposition;
import ua.andrii.project_7.entity.Showroom;
import ua.andrii.project_7.entity.Ticket;
import ua.andrii.project_7.service.ClientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class AddToCartCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(ClientService.class);
    private final ClientService clientService;

    public AddToCartCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("AddToCartCommand");
        HttpSession session = request.getSession(true);

        List buylist = (List) session.getAttribute("shoppingcart");

        boolean match = false;
        Ticket newTicket = GetTicket(request);
        if (buylist == null) {
            //add first showroom to the tray
            buylist = new ArrayList(); //first order
            buylist.add(newTicket);
        } else { // not first order
            for (int i = 0; i < buylist.size(); i++) {
                Ticket ticket = (Ticket) buylist.get(i);
                if (ticket.getShowroom().getId().equals(newTicket.getShowroom().getId())) {
                    ticket.setTicketQuantity(ticket.getTicketQuantity() + newTicket.getTicketQuantity());
                    buylist.set(i, ticket);
                    match = true;
                }
            }
            if (!match)
                buylist.add(newTicket);
        }
        session.setAttribute("shoppingcart", buylist);

        List showroomsList = clientService.getShowrooms();
        request.setAttribute("showroomslist", showroomsList);

        return "/order.jsp";
    }

    private Ticket GetTicket(HttpServletRequest request) {
        String requestShowroom = request.getParameter("order.showroom_selected");
        String qty = request.getParameter("qty");
        StringTokenizer t = new StringTokenizer(requestShowroom, "|");
        String showroomId = t.nextToken();
        String showroomName = t.nextToken();
        String expositionId = t.nextToken();
        String expositionName = t.nextToken();
        String price = t.nextToken();
        price = price.replace('$', ' ').trim();
        Exposition exposition = new Exposition(expositionName, new Timestamp(1565658687720L), new Timestamp(1565658687720L));
        exposition.setId(new Long(expositionId.trim()).longValue());
        Showroom showroom = new Showroom(showroomName, exposition, new BigDecimal(price));
        showroom.setId(new Long(showroomId.trim()).longValue());
        Ticket ticket = new Ticket(showroom, new Integer(qty.trim()));
        return ticket;
    }
}


