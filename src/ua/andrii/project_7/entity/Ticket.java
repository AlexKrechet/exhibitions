package ua.andrii.project_7.entity;

import com.sun.istack.internal.NotNull;

public class Ticket {
    private Showroom showroom;
    private int ticketQuantity;

    public Ticket(@NotNull Showroom showroom, @NotNull int ticketQuantity) {
        this.showroom = showroom;
        this.ticketQuantity = ticketQuantity;
    }

    public Showroom getShowroom() {
        return showroom;
    }

    public void setShowroom(Showroom showroom) {
        this.showroom = showroom;
    }

    public int getTicketQuantity() {
        return ticketQuantity;
    }

    public void setTicketQuantity(int ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket oTicket = (Ticket) o;

        if (ticketQuantity != oTicket.ticketQuantity) return false;
        return showroom.equals(oTicket.showroom);
    }

    @Override
    public int hashCode() {
        int result = showroom.hashCode();
        result = 31 * result + ticketQuantity;
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Ticket : ").append("showroom = ").append(showroom)
                .append(", tickets quantity =").append(ticketQuantity).append(".").toString();
    }
}
