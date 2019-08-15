package ua.andrii.project_7.commands;

import org.apache.log4j.Logger;
import ua.andrii.project_7.entity.Order;
import ua.andrii.project_7.entity.Showroom;
import ua.andrii.project_7.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class OrderCreateAdminCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(AdminService.class);
    private final AdminService adminService;

    public OrderCreateAdminCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("OrderCreateAdminCommand()");
        HttpSession session = request.getSession(true);

        List<Showroom> showroomsList = adminService.getShowrooms();
        request.setAttribute("showroomslist", showroomsList);

        List<Order> ordersList = adminService.getOrders();

        session.setAttribute("orderslist", ordersList);

        return "/admin_orders.jsp";
    }
}
