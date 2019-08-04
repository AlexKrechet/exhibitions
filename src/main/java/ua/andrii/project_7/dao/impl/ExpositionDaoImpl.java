package ua.andrii.project_7.dao.impl;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import ua.andrii.project_7.dao.ItemsDao;
import ua.andrii.project_7.entity.Exposition;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpositionDaoImpl implements ItemsDao<Exposition> {

    private static final Logger LOGGER = Logger.getLogger(ExpositionDaoImpl.class);
    private DataSource dataSource;
    private final static String SQL_BASE_QUERY_SELECTION_TEXT = "SELECT * FROM exposition";

    public ExpositionDaoImpl(@NotNull DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Long create(Exposition exposition) {
        String query_text = "INSERT INTO exposition (name, eventStartDate, eventEndDate) VALUES (?, ?, ?)";
        LOGGER.info(query_text);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setString(1, exposition.getName());
            statement.setTimestamp(2, exposition.getEventStartDate());
            statement.setTimestamp(3, exposition.getEventEndDate());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                long id = result.getLong(1);
                exposition.setId(id);
                return id;
            } else {
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to insert into expositions! " + e.getMessage());
            return null;
        }
    }

    @Override
    public Exposition read(Long id) {
        String query_text = SQL_BASE_QUERY_SELECTION_TEXT + " WHERE id = ?";
        LOGGER.info(query_text);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_text)) {
            ResultSet result = statement.executeQuery();
            List<Exposition> expositions = getExpositionFromResultSet(result);
            if (expositions.size() > 0) {
                Exposition exposition = expositions.get(0);
                return exposition;
            } else {
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to read from expositions! " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Exposition exposition) {
        String query_text = "UPDATE exposition SET name = ?, eventStartDate = ?, eventEndDate = ? WHERE id = ?";
        LOGGER.info(query_text);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setString(1, exposition.getName());
            statement.setTimestamp(2, exposition.getEventStartDate());
            statement.setTimestamp(3, exposition.getEventEndDate());
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            LOGGER.error("Failed to update expositions! " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Exposition exposition) {
        String query_text = "DELETE FROM exposition WHERE id = ?";
        LOGGER.info(query_text);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1, exposition.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error("Failed to delete from expositions! " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Exposition> findAll() {
        String query_text = SQL_BASE_QUERY_SELECTION_TEXT;
        LOGGER.info(query_text);
        List<Exposition> expositions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(query_text);
            expositions = getExpositionFromResultSet(result);
        } catch (SQLException e) {
            LOGGER.error("Failed to read from expositions! " + e.getMessage());
        }
        return expositions;
    }

    private List<Exposition> getExpositionFromResultSet(ResultSet result) throws SQLException {
        List<Exposition> expositions = new ArrayList<>();
        while (result.next()) {
            long id = result.getLong("id");
            String name = result.getString("name");
            Timestamp eventStart = result.getTimestamp("eventStartDate");
            Timestamp eventEnd = result.getTimestamp("eventEndDate");

            Exposition exposition = new Exposition.Builder().withName(name).withEventStartDate(eventStart).withEventEndDate(eventEnd).build();
            exposition.setId(id);
            expositions.add(exposition);
        }
        return expositions;
    }
}
