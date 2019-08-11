package ua.andrii.project_7.commands;

import org.apache.log4j.Logger;
import ua.andrii.project_7.entity.Exposition;
import ua.andrii.project_7.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.StringTokenizer;

public class ReturnPageEditExpositionCreationCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(AdminService.class);
    private final AdminService adminService;

    public ReturnPageEditExpositionCreationCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        LOGGER.debug("ReturnPageExpositionCreationCommand()");
        List<Exposition> expositionsList = adminService.getExpositions();
        request.setAttribute("expositionslist", expositionsList);

        Exposition newExposition = getExpositionFromRequest(request);

        request.setAttribute("exposition_id", newExposition.getId());
        request.setAttribute("exposition_name", newExposition.getName());
        request.setAttribute("event_start_date", newExposition.getEventStartDate());
        request.setAttribute("event_end_date", newExposition.getEventEndDate());

        return "/exposition_edit.jsp";
    }

    private Exposition getExpositionFromRequest(HttpServletRequest request) {
        String items = request.getParameter("expositions.exposition_selected");
        StringTokenizer t = new StringTokenizer(items, "|");
        String itemId = t.nextToken();

        return adminService.getExposition(new Long(itemId.trim()).longValue());
    }
}
