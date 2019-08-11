package ua.andrii.project_7.factory;

import org.apache.log4j.Logger;
import ua.andrii.project_7.dao.ItemsDao;
import ua.andrii.project_7.dao.UserDao;
import ua.andrii.project_7.dao.impl.ExpositionDaoImpl;
import ua.andrii.project_7.dao.impl.OrderDaoImpl;
import ua.andrii.project_7.dao.impl.ShowroomDaoImpl;
import ua.andrii.project_7.dao.impl.UserDaoImpl;
import ua.andrii.project_7.entity.Exposition;
import ua.andrii.project_7.entity.Order;
import ua.andrii.project_7.entity.Showroom;
import ua.andrii.project_7.entity.User;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JdbcDaoFactory extends DaoFactory {
    private static final Logger LOGGER = Logger.getLogger(JdbcDaoFactory.class);
    private static JdbcDaoFactory instance;
    private UserDao<User> userDao;
    private ItemsDao<Showroom> showroomDao;
    private ItemsDao<Order> orderDao;
    private ItemsDao<Exposition> expositionDao;

    private JdbcDaoFactory() {
        try {
            Context context = (Context) new InitialContext().lookup("java:comp/env");
            DataSource datasource = (DataSource) context.lookup("jdbc/MySQLDataSource");
            userDao = new UserDaoImpl(datasource);
            showroomDao = new ShowroomDaoImpl(datasource);
            expositionDao = new ExpositionDaoImpl(datasource);
            orderDao = new OrderDaoImpl(datasource);

        } catch (NamingException e) {
            LOGGER.error("Failed to initialize context: " + e.getMessage());
        }
    }

    public static JdbcDaoFactory getInstance() {
        synchronized (JdbcDaoFactory.class) {
            if (instance == null) {
                instance = new JdbcDaoFactory();
            }
        }
        return instance;
    }

    @Override
    public UserDao<User> getUserDao() {
        return userDao;
    }

    @Override
    public ItemsDao getShowroomDao() {
        return showroomDao;
    }

    @Override
    public ItemsDao getOrderDao() {
        return orderDao;
    }

    @Override
    public ItemsDao getExpositionDao() {
        return expositionDao;
    }

}
