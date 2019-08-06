package ua.andrii.project_7.factory;

import ua.andrii.project_7.dao.ItemsDao;
import ua.andrii.project_7.dao.UserDao;
import ua.andrii.project_7.entity.Exposition;
import ua.andrii.project_7.entity.Order;
import ua.andrii.project_7.entity.Showroom;
import ua.andrii.project_7.enums.DaoType;

public abstract class DaoFactory {
    public abstract UserDao getUserDao();

    public abstract ItemsDao<Showroom> getShowroomDao();

    public abstract ItemsDao<Order> getOrderDao();

    public abstract ItemsDao<Exposition> getExpositionDao();

    public static DaoFactory getFactory(DaoType type) {
        if (type == DaoType.JDBC) {
            return JdbcDaoFactory.getInstance();
        } else {
            return null;
        }
    }
}
