package ua.andrii.project_7.service;

import ua.andrii.project_7.entity.Exposition;
import ua.andrii.project_7.entity.Order;
import ua.andrii.project_7.entity.Showroom;
import ua.andrii.project_7.entity.User;
import ua.andrii.project_7.enums.UserType;
import ua.andrii.project_7.exception.*;

import java.math.BigDecimal;
import java.util.List;

public interface AdminService {
    /**
     * Looks for a record by given parameters; if founds, returns appropriate User object, otherwise - returns null.
     *
     * @param login    user login
     * @param password user passwords
     * @return found User object
     * @throws AuthenticationException in case login and/or password are empty
     */
    User login(String login, String password) throws AuthenticationException;

    /**
     * Create a record of a new user with given parameters;
     *
     * @param login                user login
     * @param password             user password
     * @param passwordConfirmation password confirmation (must match password)
     * @param name                 user name
     * @param lastName             user family name
     * @param isBlocked            is user in black list
     * @param userType             user type; available values: UserType.ADMIN or UserType.CLIENT
     * @return <code><b>true</b></code> if operation's successful, otherwise - <code><b>false</b></code>
     * @throws RegistrationException in case some parameters are invalid
     */
    boolean registerUser(String login, String password, String passwordConfirmation, String name, String lastName, boolean isBlocked, UserType userType) throws RegistrationException;

    /**
     * Changes user's password to a new one
     *
     * @param user                 current user
     * @param oldPassword          current user's password
     * @param newPassword          new user's password
     * @param passwordConfirmation new password confirmation (must match new password)
     * @return <code><b>true</b></code> if operation's successful, otherwise - <code><b>false</b></code>
     * @throws WrongUserDataException in case some parameters are invalid
     */
    boolean changePassword(User user, String oldPassword, String newPassword, String passwordConfirmation) throws WrongUserDataException;

    /**
     * Changes user's personal data e.g. name, last name and patronymic
     *
     * @param user      current user
     * @param password  user's current password
     * @param name      user's new name
     * @param lastName  user's new last name
     * @param isBlocked is user in black list
     * @return <code><b>true</b></code> if operation's successful, otherwise - <code><b>false</b></code>
     * @throws WrongUserDataException in case some parameters are invalid
     */
    boolean changeUserData(User user, String password, String passwordConfirmation, String name, String lastName, boolean isBlocked) throws WrongUserDataException;

    /**
     * Returns list of all users who have user type == Client
     *
     * @return list of Client objects
     */
    List<User> getClients();

    /**
     * Returns a User object by given id.
     * If no record with such id found, returns null
     *
     * @param userId id of the Showroom to be found
     * @return found Showroom object
     */
    User getUser(Long userId);


    /**
     * Returns list of all users
     *
     * @return list of User objects
     */
    List<User> getUsers();

    /**
     * Returns list of all showrooms
     *
     * @return list of Showroom objects
     */
    List<Showroom> getShowrooms();

    /**
     * Returns a Showroom object by given id.
     * If no record with such id found, returns null
     *
     * @param showroomId id of the Showroom to be found
     * @return found Showroom object
     */
    Showroom getShowroom(Long showroomId);

    /**
     * Updates given showroom or creates a new one in case showroom's id is null
     *
     * @param showroom showroom to be updated/created
     * @return <code><b>true</b></code> if operation's successful, otherwise - <code><b>false</b></code>
     * @throws WrongShowroomDataException in case showroom name is empty
     */
    boolean updateShowroom(Showroom showroom) throws WrongShowroomDataException;

    /**
     * Creates a new record of Showroom object with given parameters.
     *
     * @param showroomName name of the showroom
     * @param expositionId exposition id of the showroom
     * @param price        price of the showroom
     * @return <code><b>true</b></code> if operation's successful, otherwise - <code><b>false</b></code>
     * @throws WrongShowroomDataException in case some showroom data is wrong
     */
    boolean addNewShowroom(String showroomName, Long expositionId, BigDecimal price) throws WrongShowroomDataException;

    /**
     * Creates a new record of Exposition object with given parameters.
     *
     * @param expositionName name of the exposition
     * @return <code><b>true</b></code> if operation's successful, otherwise - <code><b>false</b></code>
     * @throws WrongExpositionDataException in case some exposition data is wrong
     */
    boolean addNewExposition(String expositionName) throws WrongExpositionDataException;

    /**
     * Returns an Exposition object by given id.
     * If no record with such id found, returns null
     *
     * @param expositionId id of the Exposition to be found
     * @return found Exposition object
     */
    Exposition getExposition(Long expositionId);

    /**
     * Returns list of all expositions
     *
     * @return list of Exposition objects
     */
    List<Exposition> getExpositions();

    /**
     * Updates given exposition or creates a new one in case exposition's id is null
     *
     * @param exposition exposition to be updated/created
     * @return <code><b>true</b></code> if operation's successful, otherwise - <code><b>false</b></code>
     * @throws WrongExpositionDataException in case exposition's name is empty
     */
    boolean updateExposition(Exposition exposition) throws WrongExpositionDataException;

    /**
     * Creates a new record of Order object with given parameters.
     *
     * @param ticketOrder list of showrooms
     * @param user        user
     * @param totalPrice  total price of order
     * @return <code><b>true</b></code> if operation's successful, otherwise - <code><b>false</b></code>
     * @throws WrongOrderDataException in case some order data is wrong
     */
    public boolean addNewOrder(List ticketOrder, User user, BigDecimal totalPrice) throws WrongOrderDataException;

    /**
     * Updates given order or creates a new one in case order's id is null
     *
     * @param order order to be deleted
     * @return <code><b>true</b></code> if operation's successful, otherwise - <code><b>false</b></code>
     * @throws WrongOrderDataException in case order is empty
     */
    boolean updateOrder(Order order) throws WrongOrderDataException;

    /**
     * Updates given order or creates a new one in case order's id is null
     *
     * @param order order to be deleted
     * @return <code><b>true</b></code> if operation's successful, otherwise - <code><b>false</b></code>
     * @throws WrongOrderDataException in case order is empty
     */
    boolean deleteOrder(Order order) throws WrongOrderDataException;

    /**
     * Returns list of all orders
     *
     * @return list of Order objects
     */
    List<Order> getOrders();
}
