package ua.andrii.project_7.commands;

import org.apache.log4j.Logger;
import ua.andrii.project_7.entity.Exposition;
import ua.andrii.project_7.exception.WrongExpositionDataException;
import ua.andrii.project_7.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddNewExpositionCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(AdminService.class);
    private final AdminService adminService;

    public AddNewExpositionCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("AddNewExpositionCommand()");
        try {
            String name = request.getParameter("name");
            String strStartDate = request.getParameter("event_start_date");
            String strEndDate = request.getParameter("event_end_date");
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            // you can change format of date
            Date dateStart = formatter.parse(strStartDate);
            Date dateEnd = formatter.parse(strEndDate);
            Timestamp timeStampStartDate = new Timestamp(dateStart.getTime());
            Timestamp timeStampEndDate = new Timestamp(dateEnd.getTime());

            try {
                boolean result = adminService.addNewExposition(name, timeStampStartDate, timeStampEndDate);
                if (result) {
                    List<Exposition> expositionsList = adminService.getExpositions();
                    request.setAttribute("expositionslist", expositionsList);
                    request.setAttribute("message", "Exposition creation is successful. " + name + " is added");
                    return "/exposition.jsp";

                } else {
                    request.setAttribute("error", "An internal error occurred while trying to register a new exposition");
                }
            } catch (WrongExpositionDataException e) {
                request.setAttribute("error", e.getMessage());
            }
        } catch (ParseException e) {
            System.out.println("Exception :" + e);
            return "/error.jsp";
        }

        List<Exposition> expositionsList = adminService.getExpositions();
        request.setAttribute("expositionslist", expositionsList);
        return "/exposition_create_new.jsp";
    }
}
