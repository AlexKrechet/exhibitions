package ua.andrii.project_7.commands;

import org.apache.log4j.Logger;
import ua.andrii.project_7.entity.Showroom;
import ua.andrii.project_7.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class OrderCreateClientCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(AdminService.class);
    private final AdminService adminService;

    public OrderCreateClientCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("OrderCreateClientCommand()");
        HttpSession session = request.getSession(true);
        List buyList = (List) session.getAttribute("shoppingcart");
        buyList = new ArrayList();
        buyList.clear();
        session.setAttribute("shoppingcart", buyList);

        List<Showroom> showroomsList = adminService.getShowrooms();
        request.setAttribute("showroomslist", showroomsList);

        return "/order.jsp";
    }
}
