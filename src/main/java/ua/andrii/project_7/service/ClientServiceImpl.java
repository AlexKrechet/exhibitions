package ua.andrii.project_7.service;

import com.sun.istack.internal.NotNull;
import ua.andrii.project_7.dao.ItemsDao;
import ua.andrii.project_7.dao.UserDao;
import ua.andrii.project_7.entity.*;
import ua.andrii.project_7.exception.WrongOrderDataException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class ClientServiceImpl implements ClientService {
    private UserDao userDao;
    private ItemsDao<Showroom> showroomDao;
    private ItemsDao<Exposition> expositionDao;
    private ItemsDao<Order> orderDao;

    public ClientServiceImpl(@NotNull UserDao userDao, @NotNull ItemsDao showroomDao, @NotNull ItemsDao expositionDao, @NotNull ItemsDao orderDao) {
        this.userDao = userDao;
        this.showroomDao = showroomDao;
        this.expositionDao = expositionDao;
        this.orderDao = orderDao;
    }

    @Override
    public Showroom getShowroom(Long showroomId) {
        return showroomDao.read(showroomId);
    }

    @Override
    public List<Showroom> getShowrooms() {
        return showroomDao.findAll();
    }

    @Override
    public boolean addNewOrder(List<Ticket> tickets, User user, BigDecimal totalPrice) throws WrongOrderDataException {
        if (tickets.isEmpty()) {
            throw new WrongOrderDataException("List is empty!");
        }

        //Checking every showroom price for negative inappropriate value
        for (Ticket ticket : tickets) {
            if (ticket.getShowroom().getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new WrongOrderDataException("Price is invalid!");
            }
        }

        //Checking total price for negative inappropriate value
        if (totalPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new WrongOrderDataException("Price is invalid!");
        }
        Order order = new Order.Builder().withTickets(tickets).withUser(user).withTimestamp(new Timestamp(new Date().getTime())).withPaidStatus(false).withTotalPrice(totalPrice).build();

        return orderDao.create(order) != null;
    }
}
