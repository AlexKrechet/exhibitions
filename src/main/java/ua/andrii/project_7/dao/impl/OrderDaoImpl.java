package ua.andrii.project_7.dao.impl;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import ua.andrii.project_7.dao.ItemsDao;
import ua.andrii.project_7.entity.*;
import ua.andrii.project_7.enums.UserType;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class OrderDaoImpl implements ItemsDao<Order> {
    private static final Logger LOGGER = Logger.getLogger(OrderDaoImpl.class);
    private DataSource dataSource;
    private final String SQL_BASE_QUERY_SELECTION_TEXT = "SELECT orders.*, users.*, showroom.*, tickets.*, exposition.*" +
            "FROM orders " +
            "LEFT JOIN users AS users ON users.id = orders.user_id " +
            "LEFT JOIN tickets AS tickets ON tickets.tickets_id = orders.id " +
            "LEFT JOIN showroom AS showroom ON showroom.id = tickets.tickets.id " +
            "LEFT JOIN exposition AS exposition ON showroom.exposition_id = exposition.id";

    public OrderDaoImpl(@NotNull DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Long create(Order order) {
        String query_text = "INSERT INTO orders (total_price, purchase_date, user_id, paid) VALUES (?, ?, ?, ?)";
        LOGGER.info(query_text);
        Long id = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_text, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setBigDecimal(1, order.getTotalPrice());
            statement.setTimestamp(2, new Timestamp(order.getPurchaseDate().getTime()));
            statement.setLong(3, order.getUser().getId());
            statement.setBoolean(4, order.isPaid());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                id = result.getLong(1);
                order.setId(id);

                boolean resultCreateTicketList = createTicketList(connection, order, order.getTickets());
                if (!resultCreateTicketList) {
                    connection.rollback();
                    LOGGER.error("Error occurred while creating ticket list");
                }
            }
            connection.commit();
            return id;
        } catch (SQLException e) {
            LOGGER.error("Failed to insert into Orders! " + e.getMessage());
            return null;
        }
    }

    private boolean createTicketList(Connection connection, Order order, List<Ticket> tickets) {
        int result = 0;
        for (Ticket ticket : tickets) {
            result += createTicket(connection, order, ticket.getShowroom(), ticket.getTicketQuantity());
        }
        return result == tickets.size();
    }

    private int createTicket(Connection connection, Order order, Showroom showroom, int ticketQuantity) {
        String query_text = "INSERT INTO tickets (showroom_id, tickets_id, ticket_quantity) VALUES (?, ?, ?)";
        LOGGER.info(query_text);
        try (PreparedStatement statement = connection.prepareStatement(query_text, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, showroom.getId());
            statement.setLong(2, order.getId());
            statement.setInt(3, ticketQuantity);
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Failed to insert into tickets! " + e.getMessage());
            return -1;
        }
    }

    @Override
    public Order read(Long id) {
        String query_text = SQL_BASE_QUERY_SELECTION_TEXT + " WHERE orders.id = ?";
        LOGGER.info(query_text);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            List<Order> orders = getOrderFromResultSet(result);
            if (orders.size() > 0) {
                Order order = orders.get(0);
                order.setTickets(getTicketsFromResultSet(result));
                return order;
            } else {
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to read from Orders! " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Order order) {
        String query_text = "UPDATE orders SET total_price = ?, purchase_date = ?, user_id = ?, paid = ? WHERE id = ?";
        LOGGER.info(query_text);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setBigDecimal(1, order.getTotalPrice());
            statement.setTimestamp(2, new Timestamp(order.getPurchaseDate().getTime()));
            statement.setLong(3, order.getUser().getId());
            statement.setBoolean(4, order.isPaid());
            System.out.println("*******************************************************************");
            System.out.println(order.isPaid());
            statement.setLong(5, order.getId());

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            LOGGER.error("Failed to update orders! " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Order order) {
        String query_text = "DELETE orders, tickets FROM orders LEFT JOIN tickets as tickets ON tickets_id = id WHERE orders.id = ?";
        LOGGER.info(query_text);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_text, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setLong(1, order.getId());
            int resultDeleteOrders = statement.executeUpdate();

            if (!(resultDeleteOrders > 0)) {
                connection.rollback();
                LOGGER.error("Error occurred while deleting orders, tickets list");
            }
            connection.commit();
            return true;
        } catch (SQLException e) {
            LOGGER.error("Failed to delete from Orders " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Order> findAll() {
        String query_text = "SELECT orders.*, users.*, FROM orders LEFT JOIN users AS users ON users.id = orders.user_id";
        LOGGER.info(query_text);
        List<Order> orders = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(query_text);
            orders = getOrderFromResultSetSimple(result);
        } catch (SQLException e) {
            LOGGER.error("Failed to read from orders! " + e.getMessage());
        }
        return orders;
    }

    private List<Order> getOrderFromResultSetSimple(ResultSet result) throws SQLException {
        List<Order> orders = new ArrayList<>();
        while (result.next()) {

            long orderId = result.getLong("orders.id");
            Timestamp purchaseDate = result.getTimestamp("order.purchase_date");
            boolean paid = result.getBoolean("orders.paid");
            BigDecimal totalPrice = result.getBigDecimal("orders.total_price");
            long userId = result.getLong("users.id");

            String login = result.getString("users.login");
            String password = result.getString("users.password");
            String name = result.getString("users.name");
            String lastName = result.getString("users.lastName");
            boolean isBlocked = result.getBoolean("users.isBlocked");
            UserType userType = UserType.valueOf(result.getString("users.user_type").toUpperCase());

            User user = User.getUser(login, password, name, lastName, isBlocked, userType);
            user.setId(userId);

            Order order = new Order.Builder().withTickets(null).withUser(user).withTimestamp(purchaseDate).withPaidStatus(paid)
                    .withTotalPrice(totalPrice).build();
            order.setId(orderId);
            orders.add(order);
        }
        return orders;
    }

    private List<Order> getOrderFromResultSet(ResultSet result) throws SQLException {
        List<Order> orders = new ArrayList<>();
        while (result.next()) {

            long orderId = result.getLong("orders.id");
            Timestamp purchaseDate = result.getTimestamp("orders.purchase_date");
            boolean paid = result.getBoolean("orders.paid");
            BigDecimal totalPrice = result.getBigDecimal("orders.total_price");

            String login = result.getString("users.login");
            String password = result.getString("users.password");
            String name = result.getString("users.name");
            String lastName = result.getString("users.lastName");
            boolean isBlocked = result.getBoolean("users.isBlocked");
            UserType userType = UserType.valueOf(result.getString("users.user_type").toUpperCase());
            long userId = result.getLong("users.id");

            User user = User.getUser(login, password, name, lastName, isBlocked, userType);
            user.setId(userId);

            Order order = new Order.Builder().withTickets(getTicketsFromResultSet(result)).withUser(user).withTimestamp(purchaseDate)
                    .withPaidStatus(paid).withTotalPrice(totalPrice).build();
            order.setId(orderId);
            orders.add(order);
        }
        return orders;
    }

    private List<Ticket> getTicketsFromResultSet(ResultSet result) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        while (result.next()) {

            Long expositionId = result.getLong("showroom.exposition_id");
            String expositionName = result.getString("exposition.name");
            Timestamp eventStart = result.getTimestamp("eventStartDate");
            Timestamp eventEnd = result.getTimestamp("eventEndDate");
            Exposition exposition = new Exposition.Builder().withName(expositionName).withEventStartDate(eventStart).withEventEndDate(eventEnd).build();

            exposition.setId(expositionId);

            int ticketQuantity = result.getInt("ticket.tickets_quantity");
            long showroomId = result.getLong("showroom.id");
            String name = result.getString("showroom.name");
            BigDecimal price = result.getBigDecimal("showroom.price");

            Showroom showroom = new Showroom.Builder().withShowroomName(name).withExposition(exposition).withPrice(price).build();
            showroom.setId(showroomId);

            Ticket ticket = new Ticket.Builder().withShowroom(showroom).withQuantity(ticketQuantity).build();
            tickets.add(ticket);
        }
        return tickets;
    }
}
