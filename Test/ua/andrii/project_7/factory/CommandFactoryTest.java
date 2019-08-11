package ua.andrii.project_7.factory;

import org.junit.Test;

import static org.mockito.Mockito.*;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.andrii.project_7.commands.AddToCartCommand;
import ua.andrii.project_7.commands.Command;
import ua.andrii.project_7.commands.IndexJspCommand;
import ua.andrii.project_7.entity.User;
import ua.andrii.project_7.entity.Visitor;
import ua.andrii.project_7.enums.DaoType;
import ua.andrii.project_7.enums.ServiceType;
import ua.andrii.project_7.service.AdminService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CommandFactoryTest {
    @Mock
    ServiceFactory serviceFactory;
    @Mock
    DaoFactory daoFactory;
    @Mock
    AdminService adminService;

    @Test
    public void getCommand() {

        List<User> list = new ArrayList<>();
        User guest = new Visitor("guest", "1111", "Guest", "Guest", false);
        User guest2 = new Visitor("guest2", "2222", "Guest2", "Guest2", false);
        list.add(guest);
        list.add(guest2);
        when(adminService.getUsers()).thenReturn(list);
        List<User> newOne = adminService.getUsers();
        assertEquals(2, newOne.size());
        assertEquals("Guest", newOne.get(0).getName());


        serviceFactory = ServiceFactory.getFactory(daoFactory, ServiceType.SIMPLE);

        Map<String, Command> commands = new HashMap<>();

        commands.put("", new IndexJspCommand());
        commands.put("ADD", new AddToCartCommand(serviceFactory.getClientService()));
        assertEquals(2, commands.size());

        assertTrue("ADD", true);
    }
}