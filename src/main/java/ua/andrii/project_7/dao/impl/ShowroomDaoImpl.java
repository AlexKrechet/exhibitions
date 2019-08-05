package ua.andrii.project_7.dao.impl;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import ua.andrii.project_7.dao.ItemsDao;
import ua.andrii.project_7.entity.Exposition;
import ua.andrii.project_7.entity.Showroom;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ShowroomDaoImpl implements ItemsDao<Showroom> {
    private static final Logger LOGGER = Logger.getLogger(ShowroomDaoImpl.class);
    private DataSource dataSource;
    private final String SQL_BASE_QUERY_SELECTION_TEXT = "SELECT showroom.*, exposition.* FROM Showroom as showroom LEFT JOIN Exposition as exposition ON showroom.exposition_id = showroom.id";

    public ShowroomDaoImpl(@NotNull DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Long create(Showroom showroom) {
        String query_text = "INSERT INTO showroom (name, exposition_id, price) VALUES (?, ?, ?)";
        LOGGER.info(query_text);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_text, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, showroom.getName());
            statement.setLong(2, showroom.getExposition().getId());
            statement.setBigDecimal(3, showroom.getPrice());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                long id = result.getLong(1);
                showroom.setId(id);
                return id;
            } else {
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to insert into Showroom! " + e.getMessage());
            return null;
        }
    }

    @Override
    public Showroom read(Long id) {
        String query_text = SQL_BASE_QUERY_SELECTION_TEXT + " WHERE showroom.id = ?";
        LOGGER.info(query_text);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            List<Showroom> showrooms = getShowroomFromResultSet(result);
            if (showrooms.size() > 0) {
                Showroom showroom = showrooms.get(0);
                return showroom;
            } else {
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to read from Showroom! " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Showroom showroom) {
        String query_text = "UPDATE showroom SET name = ?, exposition_id = ?, price = ? WHERE id = ?";
        LOGGER.info(query_text);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setString(1, showroom.getName());
            statement.setLong(2, showroom.getExposition().getId());
            statement.setBigDecimal(3, showroom.getPrice());
            statement.setLong(4, showroom.getId());
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            LOGGER.error("Failed to update Showroom! " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Showroom showroom) {
        String query_text = "DELETE FROM showroom WHERE id = ?";
        LOGGER.info(query_text);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1, showroom.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error("Failed to delete Showroom!" + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Showroom> findAll() {
        String query_text = SQL_BASE_QUERY_SELECTION_TEXT;
        LOGGER.info(query_text);
        List<Showroom> showrooms = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(query_text);
            showrooms = getShowroomFromResultSet(result);
        } catch (SQLException e) {
            LOGGER.error("Failed to read from Showroom! " + e.getMessage());
        }
        return showrooms;
    }

    private List<Showroom> getShowroomFromResultSet(ResultSet result) throws SQLException {
        List<Showroom> showrooms = new ArrayList<>();
        while (result.next()) {

            long id = result.getLong("showroom.id");
            String name = result.getString("showroom.name");
            Long exposition_id = result.getLong("showroom.exposition_id");
            String expositionName = result.getString("exposition.name");

            Timestamp start = result.getTimestamp("eventStartDate");
            Timestamp end = result.getTimestamp("eventEndDate");

            Exposition exposition = new Exposition.Builder().withName(expositionName).withEventStartDate(start)
                    .withEventEndDate(end).build();
            exposition.setId(exposition_id);
            BigDecimal price = result.getBigDecimal("showroom.price");

            Showroom showroom = new Showroom.Builder().withShowroomName(name).withExposition(exposition).withPrice(price).build();
            showroom.setId(id);
            showrooms.add(showroom);
        }
        return showrooms;
    }
}
