package ua.andrii.project_7.commands;

import org.apache.log4j.Logger;
import ua.andrii.project_7.entity.Showroom;
import ua.andrii.project_7.service.ClientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class DeleteFromCartCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(ClientService.class);
    private final ClientService clientService;

    public DeleteFromCartCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("DeleteFromCartCommand()");
        HttpSession session = request.getSession(true);

        List buylist =
                (List) session.getAttribute("shoppingcart");

        String del = request.getParameter("delindex");
        int d = (new Integer(del)).intValue();
        buylist.remove(d);

        session.setAttribute("shoppingcart", buylist);

        List<Showroom> showroomsList = clientService.getShowrooms();
        request.setAttribute("showroomslist", showroomsList);
        return "/order.jsp";
    }
}
