package ua.andrii.project_7.commands;

import org.apache.log4j.Logger;
import ua.andrii.project_7.entity.User;
import ua.andrii.project_7.enums.UserType;
import ua.andrii.project_7.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticateCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(AdminService.class);
    private final AdminService adminService;

    public AuthenticateCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("AuthenticateCommand()");
        User user = null;
        try {
            user = adminService.login(request.getParameter("user_name"), request.getParameter("password"));
        } catch (Throwable theException) {
            LOGGER.info(theException);
        }

        request.setAttribute("action", "");

        if (user == null) {
            request.setAttribute("error", "No user with such data found. Wrong login or password!");
            return "/index.jsp";

        } else if (user.getIsBlocked()) {
            request.setAttribute("error", "Warning! User is banned!");
            return "/index.jsp";

        } else {
            request.getSession().setAttribute("user", user);
            request.setAttribute("message", user.getName());
            request.setAttribute("action", "USER_INFO");
            if (user.getUserType() == UserType.ADMIN) {
                return "/admin_page.jsp";
            }
            return "/client_page.jsp";
        }
    }
}