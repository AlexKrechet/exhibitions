package ua.andrii.project_7.entity;

import com.sun.istack.internal.NotNull;

import java.math.BigDecimal;

public class Showroom {
    private Long id;
    private String name;
    private Exposition exposition;
    private BigDecimal price = new BigDecimal("0.00").setScale(2, BigDecimal.ROUND_CEILING);

    public Showroom(@NotNull String name, @NotNull Exposition exposition, @NotNull BigDecimal price) {
        this.name = name;
        this.exposition = exposition;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                .append(name).append("\\").append(", exposition = ").append(exposition).append(", price = ")
                .append(price).append(".").toString();
    }

    public String getPresentation() {
        return getId() + " | " + getName() + " | " + getExposition().getId() + " | " + getExposition().getName()
                + " | " + getPrice();
    }
}
