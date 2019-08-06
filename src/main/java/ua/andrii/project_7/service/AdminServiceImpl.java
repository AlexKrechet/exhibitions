package ua.andrii.project_7.service;

import com.sun.istack.internal.NotNull;
import ua.andrii.project_7.dao.ItemsDao;
import ua.andrii.project_7.dao.UserDao;
import ua.andrii.project_7.entity.Exposition;
import ua.andrii.project_7.entity.Order;
import ua.andrii.project_7.entity.Showroom;
import ua.andrii.project_7.entity.User;
import ua.andrii.project_7.enums.UserType;
import ua.andrii.project_7.exception.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminServiceImpl implements AdminService {
    private UserDao<User> userDao;
    private ItemsDao<Showroom> showroomDao;
    private ItemsDao<Exposition> expositionDao;
    private ItemsDao<Order> orderDao;

    public AdminServiceImpl(@NotNull UserDao userDao, @NotNull ItemsDao showroomDao, @NotNull ItemsDao expositionDao, @NotNull ItemsDao orderDao) {
        this.userDao = userDao;
        this.showroomDao = showroomDao;
        this.expositionDao = expositionDao;
        this.orderDao = orderDao;
    }

    @Override
    public List<User> getClients() {
        return userDao.getVisitors();
    }

    @Override
    public User getUser(Long userId) {
        return userDao.read(userId);
    }

    /**
     * @throws AuthenticationException {@inheritDoc}
     */
    @Override
    public User login(String login, String password) throws AuthenticationException {
        if (login == null || login.isEmpty()) {
            throw new AuthenticationException("Login is a required field!");
        }
        if (password == null || password.isEmpty()) {
            throw new AuthenticationException("Password is a required field!");
        }
        return userDao.getUser(login, password);
    }

    /**
     * @throws RegistrationException {@inheritDoc}
     */
    @Override
    public boolean registerUser(String login, String password, String passwordConfirmation, String name, String lastName, boolean isBlocked, UserType userType) throws RegistrationException {
        checkRegistrationData(login, name, lastName, password, passwordConfirmation);
        User user = User.getUser(login, password, name, lastName, isBlocked, userType);
        return userDao.create(user) != null;
    }

    /**
     * @throws WrongUserDataException {@inheritDoc}
     */
    @Override
    public boolean changePassword(User user, String oldPassword, String newPassword, String passwordConfirmation) throws WrongUserDataException {
        if (!user.getPassword().equals(oldPassword)) {
            throw new WrongUserDataException("Wrong old password");
        }
        checkPassword(newPassword, passwordConfirmation);
        user.setPassword(newPassword);
        return userDao.update(user);
    }

    /**
     * @throws WrongUserDataException {@inheritDoc}
     */
    @Override
    public boolean changeUserData(User user, String password, String passwordConfirmation, String name, String lastName, boolean isBlocked) throws WrongUserDataException {
        checkForScript(name);
        checkForScript(lastName);
        checkPassword(password, passwordConfirmation);
        checkDataIsNotEmpty(name, "Name");
        checkDataIsNotEmpty(lastName, "Last name");
        user.setName(name);
        user.setLastName(lastName);
        user.setBlocked(isBlocked);

        return userDao.update(user);
    }

    /**
     * Checks if user's registration data is OK
     *
     * @param login                user login
     * @param password             user password
     * @param passwordConfirmation password confirmation (must match password)
     * @param name                 user name
     * @param lastName             user last name
     * @throws WrongUserDataException in case some data is invalid
     */
    private void checkRegistrationData(String login, String name, String lastName, String password, String passwordConfirmation) throws RegistrationException {
        try {
            checkForScript(name);
            checkForScript(lastName);
            checkDataIsNotEmpty(login, "Login");
            if (!login.toLowerCase().matches("^[a-zA-Z0-9]+$")) {
                throw new RegistrationException("Login should be only letters and numbers.");
            }
            checkPassword(password, passwordConfirmation);
            checkDataIsNotEmpty(name, "Name");
            checkDataIsNotEmpty(lastName, "Last name");
        } catch (WrongUserDataException e) {
            throw new RegistrationException(e.getMessage());
        }
        if (userDao.hasUser(login)) {
            throw new RegistrationException("User with login '" + login + "' already exists");
        }
    }

    /**
     * Checks if password is OK and password confirmation matches password
     *
     * @param password             user password
     * @param passwordConfirmation password confirmation (must match password)
     * @throws WrongUserDataException
     */
    private void checkPassword(String password, String passwordConfirmation) throws WrongUserDataException {
        if (password.isEmpty()) {
            throw new WrongUserDataException("Password is a required field!");
        }
        if (password.length() < 8) {
            throw new WrongUserDataException("Password must be 8 symbols minimum");
        }
        Pattern pattern = Pattern.compile("[a-zA-Z]|[\\u0400-\\u044F]");
        Matcher matcher = pattern.matcher(password);
        if (!matcher.find()) {
            throw new WrongUserDataException("Password must contain at least one letter");
        }
        pattern = Pattern.compile("[0-9]");
        matcher = pattern.matcher(password);
        if (!matcher.find()) {
            throw new WrongUserDataException("Password must contain at least one digit");
        }
        if (!password.equals(passwordConfirmation)) {
            throw new WrongUserDataException("Password and password confirmation do not match");
        }
    }

    /**
     * Checks if password is OK and password confirmation matches password
     *
     * @param text string for check
     * @throws WrongUserDataException
     */
    private void checkForScript(String text) throws WrongUserDataException {

        if (text.contains("<") || (text.contains(">"))) {
            throw new WrongUserDataException("Text must not contain tag symbols");
        }
    }

    /**
     * Checks if given String object is not empty
     *
     * @param data     data to check
     * @param dataName data name representation
     * @throws WrongUserDataException
     */
    private void checkDataIsNotEmpty(String data, String dataName) throws WrongUserDataException {
        if (data == null || data.isEmpty()) {
            throw new WrongUserDataException(dataName + " can't be empty!");
        }
    }

    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Override
    public List<Showroom> getShowrooms() {
        return showroomDao.findAll();
    }

    @Override
    public Showroom getShowroom(Long showroomId) {
        return showroomDao.read(showroomId);
    }


    @Override
    public boolean updateShowroom(Showroom showroom) throws WrongShowroomDataException {
        if (showroom.getName().isEmpty()) {
            throw new WrongShowroomDataException("Showroom name can't be empty");
        }
        if (showroom.getPrice() == null) {
            throw new WrongShowroomDataException("Price is a required field!");
        }
        if (showroom.getId() == null) {
            return showroomDao.create(showroom) != null;
        } else {
            return showroomDao.update(showroom);
        }
    }

    @Override
    public boolean addNewShowroom(String showroomName, Long expositionId, BigDecimal price) throws WrongShowroomDataException {
        if (showroomName.isEmpty()) {
            throw new WrongShowroomDataException("Showroom name is a required field!");
        }
        if (expositionId == null) {
            throw new WrongShowroomDataException("Exposition is a required field!");
        }
        Exposition exposition = (Exposition) expositionDao.read(expositionId);
        if (price == null) {
            throw new WrongShowroomDataException("Price is a required field!");
        }
        Showroom showroom = new Showroom.Builder().withShowroomName(showroomName).withExposition(exposition).withPrice(price).build();
        return showroomDao.create(showroom) != null;
    }

    @Override
    public boolean addNewExposition(String expositionName, Timestamp startDate, Timestamp endDate) throws WrongExpositionDataException {
        if (expositionName.isEmpty()) {
            throw new WrongExpositionDataException("Exposition's name is a required field!");
        }
        Exposition exposition = new Exposition.Builder().withName(expositionName).withEventStartDate(startDate).withEventEndDate(endDate).build();

        return expositionDao.create(exposition) != null;
    }

    @Override
    public Exposition getExposition(Long expositionId) {
        return expositionDao.read(expositionId);
    }

    @Override
    public List<Exposition> getExpositions() {
        return expositionDao.findAll();
    }

    public boolean updateExposition(Exposition exposition) throws WrongExpositionDataException {
        if (exposition.getName().isEmpty()) {
            throw new WrongExpositionDataException("Exposition's name can't be empty");
        }
        if (exposition.getId() == null) {
            return expositionDao.create(exposition) != null;
        } else {
            return expositionDao.update(exposition);
        }
    }

    @Override
    public boolean addNewOrder(List tickets, User user, BigDecimal totalPrice) throws WrongOrderDataException {
        if (tickets.isEmpty()) {
            throw new WrongOrderDataException("List is empty!");
        }
        Order order = new Order.Builder().withTickets(tickets).withUser(user).withTimestamp(new Timestamp(new Date().getTime())).withPaidStatus(false).withTotalPrice(totalPrice).build();

        return orderDao.create(order) != null;
    }

    @Override
    public boolean updateOrder(Order order) throws WrongOrderDataException {
        if (order == null) {
            throw new WrongOrderDataException("Order can't be null");
        }
        return orderDao.update(order);
    }

    @Override
    public boolean deleteOrder(Order order) throws WrongOrderDataException {
        if (order == null) {
            throw new WrongOrderDataException("Order can't be null");
        }
        return orderDao.delete(order);
    }

    @Override
    public List<Order> getOrders() {
        return orderDao.findAll();
    }
}
