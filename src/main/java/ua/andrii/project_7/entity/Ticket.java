package ua.andrii.project_7.entity;

import com.sun.istack.internal.NotNull;

public class Ticket {
    private Showroom showroom;
    private int ticketQuantity;

    public static class Builder{
        private Ticket newTicket;

        public Builder(){
            newTicket = new Ticket();
        }
        public Builder withShowroom(@NotNull Showroom showroom){
            newTicket.showroom = showroom;
            return this;
        }
        public Builder withQuantity(@NotNull int ticketQuantity){
            newTicket.ticketQuantity = ticketQuantity;
            return this;
        }
        public Ticket build(){
            return newTicket;
        }
    }
    public Showroom getShowroom(){
        return showroom;
    }
    public void setShowroom(Showroom showroom){
        this.showroom = showroom;
    }
    public int getTicketQuantity(){
        return ticketQuantity;
    }
    public void setTicketQuantity(int ticketQuantity){
        this.ticketQuantity = ticketQuantity;
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket oTicket = (Ticket) o;

        if (ticketQuantity != oTicket.ticketQuantity) return false;
        return showroom.equals(oTicket.showroom);
    }
    @Override
    public int hashCode(){
        int result = showroom.hashCode();
        result = 31 * result + ticketQuantity;
        return  result;
    }
    @Override
    public String toString(){
        return new StringBuilder().append("Ticket : ").append("showroom = ").append(showroom)
                .append(", tickets quantity =").append(ticketQuantity).append(".").toString();
    }
}
