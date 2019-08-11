package ua.andrii.project_7.commands;

import org.apache.log4j.Logger;
import ua.andrii.project_7.entity.Exposition;
import ua.andrii.project_7.entity.Showroom;
import ua.andrii.project_7.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowroomCreateCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(AdminService.class);
    private final AdminService adminService;

    public ShowroomCreateCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("ShowroomCreateCommand()");

        List<Showroom> showroomsList = adminService.getShowrooms();
        List<Exposition> expositionsList = adminService.getExpositions();

        request.setAttribute("showroom_expositionslist", expositionsList);
        request.setAttribute("showroomslist", showroomsList);

        return "/showroom.jsp";
    }
}
