package ua.andrii.project_7.entity;

import com.sun.istack.internal.NotNull;
import ua.andrii.project_7.enums.UserType;

import java.io.Serializable;

public abstract class User implements Serializable {
    private Long id;
    private String login;
    private String password;
    private String name;
    private String lastName;
    private boolean isBlocked;

    public User(@NotNull String login, @NotNull String password, @NotNull String name, @NotNull String lastName,
                @NotNull boolean isBlocked) {
        this(login, password, name, lastName);
        this.isBlocked = isBlocked;
    }

    public User(@NotNull String login, @NotNull String password, @NotNull String name, @NotNull String lastName) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
    }

    public User(@NotNull String login, @NotNull String password) {
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName() {
        this.lastName = lastName;
    }

    public void setIsBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public boolean getIsBlocked() {
        return isBlocked;
    }

    public abstract UserType getUserType();

    public static User getUser(@NotNull String login, @NotNull String password, @NotNull String name,
                               @NotNull String lastName, boolean isBlocked, UserType userType) {
        if (userType == UserType.ADMIN) {
            return new Admin(login, password, name, lastName, isBlocked);
        } else {
            return new Visitor(login, password, name, lastName, isBlocked);
        }
    }

    public String getFullName() {
        return name + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    @Override
    public String toString() {
        String isBlockedText = isBlocked ? "Banned" : "Allowed";
        return new StringBuilder().append("User: ").append("id = ").append(id).append(", login = ").append(login)
                .append(", name = ").append(name).append(", last name = ").append(lastName).append(", access = ")
                .append(isBlockedText).append(".").toString();
    }

    public String getDisplay() {
        return getId() + " | " + getFullName();
    }
}
