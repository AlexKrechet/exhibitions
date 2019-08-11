package ua.andrii.project_7.dao.impl;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import ua.andrii.project_7.dao.UserDao;
import ua.andrii.project_7.entity.User;
import ua.andrii.project_7.entity.Visitor;
import ua.andrii.project_7.enums.UserType;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements UserDao<User> {
    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);
    private DataSource dataSource;
    private final String SQL_BASE_QUERY_SELECTION_TEXT = "SELECT * FROM Users";

    public UserDaoImpl(@NotNull DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Long create(User user) {
        String query_text = "INSERT INTO Users (login, password, name, last_name, isBlocked, user_type) VALUES (?, ?, ?, ?, ?, ?)";
        LOGGER.info(query_text);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_text, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getIsBlocked() ? "1" : "0");
            statement.setString(6, user.getUserType().toString());

            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                long id = result.getLong(1);
                user.setId(id);
                return id;
            } else {
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to insert into Users! " + e.getMessage());
            return null;
        }
    }

    @Override
    public User read(Long id) {
        String query_text = SQL_BASE_QUERY_SELECTION_TEXT + " WHERE id = ?";
        LOGGER.info(query_text);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            List<User> users = getUsersFromResultSet(result);
            if (users.size() > 0) {
                User user = users.get(0);
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to read from Users! " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(User user) {
        String query_text = "UPDATE Users SET login = ?, password = ?, name = ?, last_name = ?, user_type = ?, isBlocked = ? WHERE id =?";
        LOGGER.info(query_text);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getUserType().toString());
            statement.setInt(6, user.getIsBlocked() ? 1 : 0);
            statement.setLong(7, user.getId());
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            LOGGER.error("Failed to update Users! " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(User user) {
        String query_text = "DELETE FROM Users WHERE id = ?";
        LOGGER.info(query_text);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1, user.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error("FAiled to delete from Users! " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<User> findAll() {
        String query_text = SQL_BASE_QUERY_SELECTION_TEXT;
        LOGGER.info(query_text);
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(query_text);
            users = getUsersFromResultSet(result);
        } catch (SQLException e) {
            LOGGER.error("Failed to read from Users! " + e.getMessage());
        }
        return users;
    }

    @Override
    public User getUser(String login, String password) {
        String query_text = SQL_BASE_QUERY_SELECTION_TEXT + " WHERE login = ? AND password = ?";
        LOGGER.info(query_text);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            List<User> users = getUsersFromResultSet(result);
            if (users.size() > 0) {
                return users.get(0);
            } else {
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to read from Users! " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean hasUser(String login) {
        String query_text = SQL_BASE_QUERY_SELECTION_TEXT + " WHERE login = ?";
        LOGGER.info(query_text);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setString(1, login);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to read from Users! " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<User> getVisitors() {
        String query_text = SQL_BASE_QUERY_SELECTION_TEXT + " WHERE user_type = ?";
        LOGGER.info(query_text);
        List<User> list = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setString(1, UserType.VISITOR.toString());
            ResultSet result = statement.executeQuery();
            List<User> users = getUsersFromResultSet(result);
            for (User user : users) {
                list.add((Visitor) user);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to read from Users! " + e.getMessage());
        } catch (ClassCastException e) {
            LOGGER.error("Failed to cast Users! " + e.getMessage());
        }
        return list;
    }

    private List<User> getUsersFromResultSet(ResultSet result) throws SQLException {
        List<User> users = new ArrayList<>();
        while (result.next()) {
            String login = result.getString("login");
            String password = result.getString("password");
            String name = result.getString("name");
            String lastName = result.getString("last_name");
            boolean isBlocked = result.getBoolean("isBlocked");
            UserType userType = UserType.valueOf(result.getString("user_type").toUpperCase());
            long id = result.getLong("id");

            User user = User.getUser(login, password, name, lastName, isBlocked, userType);
            user.setId(id);
            users.add(user);
        }
        return users;
    }
}
