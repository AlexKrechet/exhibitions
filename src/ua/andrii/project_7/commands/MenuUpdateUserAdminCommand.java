package ua.andrii.project_7.commands;

import org.apache.log4j.Logger;
import ua.andrii.project_7.entity.User;
import ua.andrii.project_7.enums.UserType;
import ua.andrii.project_7.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class MenuUpdateUserAdminCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(LogOutCommand.class);
    private final AdminService adminService;

    public MenuUpdateUserAdminCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(true);
        LOGGER.debug("MenuUpdateUserAdminCommand");

        User user = (User) session.getAttribute("user");
        User newUser = user;

        List usersList = adminService.getUsers();

        request.setAttribute("userslist", usersList);

        request.setAttribute("user_id", newUser.getId());
        request.setAttribute("login", newUser.getLogin());
        request.setAttribute("password", newUser.getPassword());
        request.setAttribute("password_confirmation", newUser.getPassword());
        request.setAttribute("name", newUser.getName());
        request.setAttribute("last_name", newUser.getLastName());
        if (newUser.getUserType() == UserType.ADMIN) request.setAttribute("user_type_admin", newUser.getUserType());
        else request.setAttribute("user_type_client", newUser.getUserType());

        if (newUser.getIsBlocked()) request.setAttribute("is_blocked", "1");
        else request.setAttribute("is_blocked", "0");

        return "/admin_user_update.jsp";
    }
}
