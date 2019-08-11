package ua.andrii.project_7.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Order {
    private Long id;
    private List<Ticket> tickets;
    private User user;
    private Timestamp purchaseDate;
    private boolean paid;
    private BigDecimal totalPrice = new BigDecimal("0.00").setScale(2, BigDecimal.ROUND_CEILING);

    public static class Builder {
        private Order newOrder;

        public Builder() {
            newOrder = new Order();
        }

        public Builder withTickets(List<Ticket> tickets) {
            newOrder.tickets = tickets;
            return this;
        }

        public Builder withUser(User user) {
            newOrder.user = user;
            return this;
        }

        public Builder withTimestamp(Timestamp purchaseDate) {
            newOrder.purchaseDate = purchaseDate;
            return this;
        }

        public Builder withPaidStatus(boolean paid) {
            newOrder.paid = paid;
            return this;
        }

        public Builder withTotalPrice(BigDecimal totalPrice) {
            newOrder.totalPrice = totalPrice;
            return this;
        }

        public Order build() {
            return newOrder;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Timestamp purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Order = ").append("id = ").append(id).append(", tickets = ")
                .append(tickets).append(", user = ").append(user).append(", purchase date = ").append(purchaseDate)
                .append(", paid = ").append(paid).append(", total price = ").append(totalPrice).append(".").toString();
    }

    public String getPresentation() {
        return getId() + " | " + getPurchaseDate() + " | " + getUser().getId() + " | " + getUser().getFullName() +
                " | " + (isPaid() ? " paid " : " not paid ") + " | " + getTotalPrice();
    }
}
