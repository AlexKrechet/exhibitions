package ua.andrii.project_7.commands;

import org.apache.log4j.Logger;
import ua.andrii.project_7.entity.Exposition;
import ua.andrii.project_7.entity.Showroom;
import ua.andrii.project_7.entity.User;
import ua.andrii.project_7.exception.WrongShowroomDataException;
import ua.andrii.project_7.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateShowroomInfoAdminCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(LogOutCommand.class);
    private final AdminService adminService;

    public UpdateShowroomInfoAdminCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(true);
        LOGGER.debug("UpdateShowroomInfoAdminCommand");

        User user = (User) session.getAttribute("user");
        List showroomsList = adminService.getShowrooms();
        List expositionsList = adminService.getExpositions();

        String showroomId = request.getParameter("showroom_id");
        String showroomName = request.getParameter("showroom_name");

        String showroomPriceString = request.getParameter("showroom_price");
        Exposition showroomExposition = GetExpositionFromRequest(request);
        BigDecimal showroomPrice;

        try {
            checkPrice(showroomPriceString);
            showroomPrice = new BigDecimal(showroomPriceString);
            Showroom newShowroom = new Showroom(showroomName, showroomExposition, showroomPrice);
            newShowroom.setId(new Long(showroomId.trim()).longValue());

            expositionsList = deleteExpositionFromList(expositionsList, newShowroom.getExposition());
            expositionsList.add(0, newShowroom.getExposition());

            request.setAttribute("showroom_expositionslist", expositionsList);
            request.setAttribute("showroomslist", showroomsList);

            try {
                boolean result = adminService.updateShowroom(newShowroom);
                if (result) {

                    request.setAttribute("message", "Changing is successful. " + newShowroom.getName() + " is updated");

                    request.setAttribute("showroom_id", newShowroom.getId());
                    request.setAttribute("showroom_name", newShowroom.getName());
                    request.setAttribute("showroom_price", newShowroom.getPrice());

                    showroomsList = adminService.getShowrooms();
                    expositionsList = adminService.getExpositions();

                    request.setAttribute("showroom_expositionslist", expositionsList);
                    request.setAttribute("showroomslist", showroomsList);

                    return "/showroom.jsp";

                } else {
                    request.setAttribute("error", "An internal error occurred while trying to update a showroom");
                    request.setAttribute("showroom_id", showroomId);
                    request.setAttribute("showroom_name", showroomName);
                    request.setAttribute("showroom_price", showroomPriceString);

                    expositionsList = deleteExpositionFromList(expositionsList, showroomExposition);
                    expositionsList.add(0, showroomExposition);
                    request.setAttribute("showroom_expositionslist", expositionsList);
                    request.setAttribute("showroomslist", showroomsList);

                    return "/showroom_edit.jsp";
                }
            } catch (WrongShowroomDataException e) {
                request.setAttribute("error", e.getMessage());

                request.setAttribute("showroom_id", showroomId);
                request.setAttribute("showroom_name", showroomName);
                request.setAttribute("showroom_price", showroomPriceString);

                showroomsList = adminService.getShowrooms();
                expositionsList = adminService.getExpositions();
                expositionsList = deleteExpositionFromList(expositionsList, showroomExposition);
                expositionsList.add(0, showroomExposition);
                request.setAttribute("showroom_expositionslist", expositionsList);
                request.setAttribute("expositionsList", showroomsList);

                return "/showroom_edit.jsp";
            }

        } catch (WrongShowroomDataException e) {
            request.setAttribute("error", e.getMessage());

            request.setAttribute("showroom_id", showroomId);
            request.setAttribute("showroom_name", showroomName);
            request.setAttribute("showroom_price", showroomPriceString);

            showroomsList = adminService.getShowrooms();
            expositionsList = adminService.getExpositions();
            expositionsList = deleteExpositionFromList(expositionsList, showroomExposition);
            expositionsList.add(0, showroomExposition);
            request.setAttribute("showroom_expositionslist", expositionsList);
            request.setAttribute("showroomslist", showroomsList);

            return "/showroom_edit.jsp";
        }
    }

    private Exposition GetExpositionFromRequest(HttpServletRequest request) {
        String items = request.getParameter("showrooms.exposition_selected");
        StringTokenizer t = new StringTokenizer(items, "|");
        String itemId = t.nextToken();

        return adminService.getExposition(new Long(itemId.trim()).longValue());
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

    private void checkPrice(String price) throws WrongShowroomDataException {
        if (price.isEmpty() || price == null) {
            throw new WrongShowroomDataException("Price is a required field!");
        }
        Pattern
                pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(price);
        if (!matcher.find()) {
            throw new WrongShowroomDataException("Price must contain only digit");
        }
    }
}
