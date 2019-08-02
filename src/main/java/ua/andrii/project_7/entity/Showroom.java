package ua.andrii.project_7.entity;

import com.sun.istack.internal.NotNull;

import java.math.BigDecimal;

public class Showroom {
    private Long id;
    private String showroomName;
    private Exposition exposition;
    private BigDecimal price = new BigDecimal("0.00").setScale(2, BigDecimal.ROUND_CEILING);

    public static class Builder {
        private Showroom newShowroom;

        public Builder withShowroomName(@NotNull String showroomName) {
            newShowroom.showroomName = showroomName;
            return this;
        }

        public Builder withExposition(@NotNull Exposition exposition) {
            newShowroom.exposition = exposition;
            return this;
        }

        public Builder withPrice(@NotNull BigDecimal price) {
            newShowroom.price = price;
            return this;
        }

        public Showroom build() {
            return newShowroom;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShowroomName() {
        return showroomName;
    }

    public void setShowroomName(String showroomName) {
        this.showroomName = showroomName;
    }

    public Exposition getExposition() {
        return exposition;
    }

    public void setExposition(Exposition exposition) {
        this.exposition = exposition;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Showroom showroom = (Showroom) o;
        return id.equals(showroom.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Showroom : ").append("id = ").append(id).append(", showroom name = ")
                .append(showroomName).append("\\").append(", exposition = ").append(exposition).append(", price = ")
                .append(price).append(".").toString();
    }

    public String getPresentation() {
        return getId() + " | " + getShowroomName() + " | " + getExposition().getId() + " | " + getExposition().getName()
                + " | " + getPrice();
    }
}
