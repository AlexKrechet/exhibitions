package ua.andrii.project_7.entity;

import com.sun.istack.internal.NotNull;
import ua.andrii.project_7.enums.UserType;

public class Admin extends User {

    public Admin(@NotNull String login, @NotNull String password, @NotNull String name, @NotNull String lastName,
                 @NotNull boolean isBlocked) {
        super(login, password, name, lastName);
    }

    @Override
    public UserType getUserType() {
        return UserType.ADMIN;
    }

    @Override
    public String toString() {
        String isBlockedText = getIsBlocked() ? "Denied" : "Allowed";
        return new StringBuilder().append("Admin: ").append("id = ").append(getId()).append(", login = ")
                .append(getLogin()).append("\\").append(", password = ").append(getPassword()).append("\\")
                .append(", name = ").append(getName()).append("\\").append(", last name = ").append(getLastName())
                .append("\\").append(", access = ").append(getIsBlocked()).append(".").toString();
    }
}
