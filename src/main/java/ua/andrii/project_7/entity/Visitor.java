package ua.andrii.project_7.entity;

import com.sun.istack.internal.NotNull;
import ua.andrii.project_7.enums.UserType;

public class Visitor extends User {
    public Visitor(@NotNull String login, @NotNull String password, @NotNull String name, @NotNull String lastName,
                   @NotNull boolean isBlocked) {
        super(login, password, name, lastName, isBlocked);
    }

    public Visitor(@NotNull String login, @NotNull String password, @NotNull String name, @NotNull String lastName) {
        super(login, password, name, lastName);
    }

    @Override
    public UserType getUserType() {
        return UserType.VISITOR;
    }

    @Override
    public String toString() {
        String isBlockedText = getIsBlocked() ? "Denied" : "Allowed";
        return new StringBuilder().append("Visitor: ").append("id = ").append(getId()).append(", login = ")
                .append(getLogin()).append("\\").append(", password = ").append(getPassword()).append("\\")
                .append(", name = ").append(getName()).append("\\").append(", lastName = ").append(getLastName())
                .append("\\").append(", access = ").append(isBlockedText).append(".").toString();
    }
}
