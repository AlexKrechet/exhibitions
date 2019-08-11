package ua.andrii.project_7.factory;

import ua.andrii.project_7.commands.*;
import ua.andrii.project_7.enums.DaoType;
import ua.andrii.project_7.enums.ServiceType;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static Map<String, Command> commands;

    static {
        ServiceFactory serviceFactory;
        DaoFactory daoFactory = DaoFactory.getFactory(DaoType.JDBC);
        serviceFactory = ServiceFactory.getFactory(daoFactory, ServiceType.SIMPLE);

        commands = new HashMap<>();
        commands.put("", new IndexJspCommand());

        commands.put("ADD", new AddToCartCommand(serviceFactory.getClientService()));
        commands.put("DELETE", new DeleteFromCartCommand(serviceFactory.getClientService()));
        commands.put("CHECKOUT", new CheckOutCommand());
        commands.put("SEND_ORDER", new SendOrderCommand(serviceFactory.getClientService()));

        commands.put("AUTHENTICATE", new AuthenticateCommand(serviceFactory.getAdminService()));
        commands.put("CLIENT_REGISTRATION", new ClientRegistrationCommand());

        commands.put("USER_INFO", new UserInfoCommand());
        commands.put("UPDATE_USER_PASSWORD", new UserPasswordChangeCommand());
        commands.put("NEW_USER_PASSWORD", new NewUserPasswordCommand(serviceFactory.getAdminService()));

        commands.put("ADD_NEW_USER", new AddNewUserCommand(serviceFactory.getAdminService()));
        commands.put("ADD_NEW_USER_BY_ADMIN", new AddNewUserAdminCommand(serviceFactory.getAdminService()));
        commands.put("UPDATE_USER", new MenuUpdateUserAdminCommand(serviceFactory.getAdminService()));
        commands.put("SELECT_USER", new SelectUserAdminCommand(serviceFactory.getAdminService()));
        commands.put("UPDATE_USER_INFO", new UpdateUserInfoAdminCommand(serviceFactory.getAdminService()));

        commands.put("EXPOSITION_CREATE_MENU", new ExpositionCreateCommand(serviceFactory.getAdminService()));
        commands.put("EXPOSITION_CREATE_LINK", new ReturnPageExpositionCreationCommand(serviceFactory.getAdminService()));
        commands.put("ADD_NEW_EXPOSITION_BY_ADMIN", new AddNewExpositionCommand(serviceFactory.getAdminService()));
        commands.put("EDIT_EXPOSITION_LINK", new ReturnPageEditExpositionCreationCommand(serviceFactory.getAdminService()));
        commands.put("UPDATE_EXPOSITION_INFO", new UpdateExpositionInfoAdminCommand(serviceFactory.getAdminService()));

        commands.put("SHOWROOM_CREATE_MENU", new ShowroomCreateCommand(serviceFactory.getAdminService()));
        commands.put("SHOWROOM_CREATE_LINK", new ReturnPageShowroomCreationCommand(serviceFactory.getAdminService()));
        commands.put("ADD_NEW_SHOWROOM_BY_ADMIN", new AddNewShowroomCommand(serviceFactory.getAdminService()));
        commands.put("EDIT_SHOWROOM_LINK", new ReturnPageEditShowroomCreationCommand(serviceFactory.getAdminService()));
        commands.put("UPDATE_SHOWROOM_INFO", new UpdateShowroomInfoAdminCommand(serviceFactory.getAdminService()));

        commands.put("ORDER_CREATE_MENU_ADMIN", new OrderCreateAdminCommand(serviceFactory.getAdminService()));
        commands.put("DELETE_ORDER_ADMIN", new DeleteOrderAdminCommand(serviceFactory.getAdminService()));
        commands.put("PAY_ORDER_ADMIN", new SetPaidOrderAdminCommand(serviceFactory.getAdminService()));

        commands.put("CLIENT_INFO", new ClientInfoCommand());
        commands.put("ORDER_CREATE_MENU_CLIENT", new OrderCreateClientCommand(serviceFactory.getAdminService()));


        commands.put("LOGOUT", new LogOutCommand());
    }

    private CommandFactory() {
    }

    public static Command getCommand(String commandName) {
        return commands.get(commandName);
    }
}
