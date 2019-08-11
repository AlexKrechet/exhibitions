package ua.andrii.project_7.commands;

import org.apache.log4j.Logger;
import ua.andrii.project_7.enums.UserType;
import ua.andrii.project_7.exception.RegistrationException;
import ua.andrii.project_7.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddNewUserCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(AdminService.class);
    private final AdminService adminService;

    public AddNewUserCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("AddNewUserCommand");
        UserType userType;
        userType = UserType.valueOf(request.getParameter("user_type").toUpperCase());
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("password_confirmation");
        String name = request.getParameter("name");
        String lastName = request.getParameter("last_name");
        try {
            boolean result = adminService.registerUser(login, password, passwordConfirmation, name, lastName, false, userType);
            if (result) {

                request.setAttribute("message", "Registration is successful");
                return "/index.jsp";

            } else {
                request.setAttribute("error", "An internal error occurred while trying to register a new user");
            }
        } catch (RegistrationException e) {
            request.setAttribute("error", e.getMessage());
        }

        request.setAttribute("login", login);
        request.setAttribute("name", name);
        request.setAttribute("last_name", lastName);

        return "/client_registration.jsp";
    }
}