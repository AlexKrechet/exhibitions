package ua.andrii.project_7.entity;

import com.sun.istack.internal.NotNull;

import java.sql.Timestamp;

public class Exposition {
    private Long id;
    private String name;
    private Timestamp eventStartDate;
    private Timestamp getEventEndDate;

    public static class Builder {
        private Exposition newExposition;

        public Builder withName(@NotNull String name) {
            newExposition.name = name;
            return this;
        }

        public Builder withEventStartDate(@NotNull Timestamp eventStartDate) {
            newExposition.eventStartDate = eventStartDate;
            return this;
        }

        public Builder withEventEndDate(@NotNull Timestamp eventEndDate) {
            return this;
        }

        public Exposition build() {
            return newExposition;
        }
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

    public Timestamp getGetEventEndDate() {
        return getEventEndDate;
    }

    public void setGetEventEndDate(Timestamp eventEndDate) {
        this.getEventEndDate = eventEndDate;
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
