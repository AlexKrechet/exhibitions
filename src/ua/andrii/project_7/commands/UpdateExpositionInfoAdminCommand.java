package ua.andrii.project_7.commands;

import org.apache.log4j.Logger;
import ua.andrii.project_7.entity.Exposition;
import ua.andrii.project_7.entity.User;
import ua.andrii.project_7.exception.WrongExpositionDataException;
import ua.andrii.project_7.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UpdateExpositionInfoAdminCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(LogOutCommand.class);
    private final AdminService adminService;

    public UpdateExpositionInfoAdminCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(true);
        LOGGER.debug("UpdateExpositionInfoAdminCommand");

        User user = (User) session.getAttribute("user");
        List<Exposition> expositionsList = adminService.getExpositions();
        request.setAttribute("expositionslist", expositionsList);

        String expositionId = request.getParameter("exposition_id");
        Exposition newExposition = adminService.getExposition(new Long(expositionId.trim()).longValue());

        String expositionName = request.getParameter("exposition_name");


        newExposition.setName(expositionName);

        try {
            boolean result = adminService.updateExposition(newExposition);
            if (result) {

                request.setAttribute("message", "Changing is successful. " + newExposition.getName() + " is updated");

                request.setAttribute("exposition_id", newExposition.getId());
                request.setAttribute("exposition_name", newExposition.getName());
                request.setAttribute("exposition_event_start_date", newExposition.getEventStartDate());
                request.setAttribute("exposition_event_end_date", newExposition.getEventEndDate());

                expositionsList = adminService.getExpositions();
                request.setAttribute("expositionslist", expositionsList);
                return "/exposition.jsp";

            } else {
                request.setAttribute("error", "An internal error occurred while trying to register a new exposition");
            }
        } catch (WrongExpositionDataException e) {
            request.setAttribute("error", e.getMessage());
        }

        request.setAttribute("exposition_id", newExposition.getId());
        request.setAttribute("exposition_name", newExposition.getName());

        return "/exposition_edit.jsp";
    }
}
