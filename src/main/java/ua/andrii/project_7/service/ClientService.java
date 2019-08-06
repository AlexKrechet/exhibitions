package ua.andrii.project_7.service;

import ua.andrii.project_7.entity.Showroom;
import ua.andrii.project_7.entity.Ticket;
import ua.andrii.project_7.entity.User;
import ua.andrii.project_7.exception.WrongOrderDataException;

import java.math.BigDecimal;
import java.util.List;

public interface ClientService {
    /**
     * Returns a Showroom object by given id.
     * If no record with such id found, returns null
     *
     * @param showroomId id of the Showroom to be found
     * @return found Showroom object
     */
    Showroom getShowroom(Long showroomId);

    /**
     * Returns list of all showrooms
     *
     * @return list of Showroom objects
     */
    List<Showroom> getShowrooms();

    /**
     * Creates a new record of Order object with given parameters.
     *
     * @param ticketOrder list of showrooms
     * @param user        user
     * @param totalPrice  total price of order
     * @return <code><b>true</b></code> if operation's successful, otherwise - <code><b>false</b></code>
     * @throws WrongOrderDataException in case some order data is wrong
     */
    public boolean addNewOrder(List<Ticket> ticketOrder, User user, BigDecimal totalPrice) throws WrongOrderDataException;

}
