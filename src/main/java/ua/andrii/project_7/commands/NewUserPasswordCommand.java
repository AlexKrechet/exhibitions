package ua.andrii.project_7.commands;

import org.apache.log4j.Logger;
import ua.andrii.project_7.entity.User;
import ua.andrii.project_7.exception.WrongUserDataException;
import ua.andrii.project_7.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NewUserPasswordCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(AdminService.class);
    private final AdminService adminService;

    public NewUserPasswordCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("NewUserPasswordCommand");
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");

        String old_password = request.getParameter("old_password");
        String new_password = request.getParameter("new_password");
        String passwordConfirmation = request.getParameter("password_confirmation");

        try {
            boolean result = adminService.changePassword(user, old_password, new_password, passwordConfirmation);
            if (result) {

                request.setAttribute("message", "Password update is successful");
                return "/admin_page.jsp";

            } else {
                request.setAttribute("error", "An internal error occurred while trying to change password");
            }
        } catch (WrongUserDataException e) {
            request.setAttribute("error", e.getMessage());
        }

        return "/admin_page.jsp";
    }
}