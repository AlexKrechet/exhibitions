package ua.andrii.project_7.commands;

import org.apache.log4j.Logger;
import ua.andrii.project_7.entity.Exposition;
import ua.andrii.project_7.entity.Showroom;
import ua.andrii.project_7.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ReturnPageEditShowroomCreationCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(AdminService.class);
    private final AdminService adminService;

    public ReturnPageEditShowroomCreationCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        LOGGER.debug("ReturnPageEditShowroomCreationCommand()");
        List<Showroom> showroomsList = adminService.getShowrooms();
        List<Exposition> expositionsList = adminService.getExpositions();

        Showroom newShowroom = GetShowroomFromRequest(request);
        expositionsList = deleteExpositionFromList(expositionsList, newShowroom.getExposition());
        expositionsList.add(0, newShowroom.getExposition());

        request.setAttribute("showroom_expositionslist", expositionsList);
        request.setAttribute("showroomslist", showroomsList);

        request.setAttribute("showroom_id", newShowroom.getId());
        request.setAttribute("showroom_name", newShowroom.getName());
        request.setAttribute("showroom_price", newShowroom.getPrice());

        return "/showroom_edit.jsp";
    }

    private Showroom GetShowroomFromRequest(HttpServletRequest request) {
        String items = request.getParameter("showrooms.showroom_selected");
        StringTokenizer t = new StringTokenizer(items, "|");
        String itemId = t.nextToken();

        return adminService.getShowroom(new Long(itemId.trim()).longValue());
    }

    private List<Exposition> deleteExpositionFromList(List<Exposition> old, Exposition exposition) {
        List<Exposition> expositions = new ArrayList<>();

        for (Exposition newExposition : old) {
            if (!newExposition.equals(exposition)) {
                expositions.add(newExposition);
            }
        }
        return expositions;
    }
}
