package ua.andrii.project_7.entity;

import com.sun.istack.internal.NotNull;

import java.sql.Timestamp;

public class Exposition {
    private Long id;
    private String name;
    private Timestamp eventStartDate;
    private Timestamp eventEndDate;

    public Exposition(@NotNull String name, @NotNull Timestamp eventStartDate, @NotNull Timestamp eventEndDate) {
        this.name = name;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
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

    public Timestamp getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(Timestamp eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public Timestamp getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(Timestamp eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Exposition exposition = (Exposition) o;
        return id.equals(exposition.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Exposition : ").append("id = ").append(id).append(", name = ").append(name)
                .append(".").append("\\").toString();
    }

    public String getPresentation() {
        return getId() + " | " + getName();
    }
}
