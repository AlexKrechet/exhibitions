package ua.andrii.project_7.commands;

import org.apache.log4j.Logger;
import ua.andrii.project_7.entity.Exposition;
import ua.andrii.project_7.entity.Showroom;
import ua.andrii.project_7.exception.WrongShowroomDataException;
import ua.andrii.project_7.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.StringTokenizer;

public class AddNewShowroomCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(AdminService.class);
    public static final String SHOWROOM_EXPOSITIONS_LIST = "showroom_expositionslist";
    private final AdminService adminService;

    public AddNewShowroomCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("AddNewShowroomCommand()");

        String showroom_name = request.getParameter("showroom_name");
        String showroom_price = request.getParameter("showroom_price");
        Showroom showroom_exposition = GetExpositionFromRequest(request);

        try {
            boolean result = adminService.addNewShowroom(showroom_name, showroom_exposition.getId(), new BigDecimal(showroom_price));
            if (result) {
                List<Showroom> showroomsList = adminService.getShowrooms();
                List<Exposition> expositionsList = adminService.getExpositions();
                request.setAttribute(SHOWROOM_EXPOSITIONS_LIST, expositionsList);
                request.setAttribute("showroomlist", showroomsList);
                request.setAttribute("message", "Showroom creation is successful. " + showroom_name + " is added");
                return "/periodical.jsp";

            } else {
                request.setAttribute("error", "An internal error occurred while trying to create a new showroom");
            }
        } catch (WrongShowroomDataException e) {
            request.setAttribute("error", e.getMessage());
        }

        List showroomsList = adminService.getShowrooms();
        List expositionsList = adminService.getExpositions();
        request.setAttribute(SHOWROOM_EXPOSITIONS_LIST, expositionsList);
        request.setAttribute("showroomslist", showroomsList);
        return "/showroom_create_new.jsp";
    }

    private Showroom GetExpositionFromRequest(HttpServletRequest request) {
        String items = request.getParameter("showrooms.exposition_selected");
        StringTokenizer t = new StringTokenizer(items, "|");
        String itemId = t.nextToken();

        return adminService.getShowroom(new Long(itemId.trim()));
    }
}
