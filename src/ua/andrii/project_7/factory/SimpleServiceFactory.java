package ua.andrii.project_7.factory;

import com.sun.istack.internal.NotNull;
import ua.andrii.project_7.service.AdminService;
import ua.andrii.project_7.service.AdminServiceImpl;
import ua.andrii.project_7.service.ClientService;
import ua.andrii.project_7.service.ClientServiceImpl;

public class SimpleServiceFactory extends ServiceFactory {
    private static SimpleServiceFactory instance;
    private DaoFactory daoFactory;
    private AdminService adminService;
    private ClientService clientService;

    private SimpleServiceFactory(@NotNull DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
        adminService = new AdminServiceImpl(daoFactory.getUserDao(), daoFactory.getShowroomDao(), daoFactory.getExpositionDao(), daoFactory.getOrderDao());
        clientService = new ClientServiceImpl(daoFactory.getUserDao(), daoFactory.getShowroomDao(), daoFactory.getExpositionDao(), daoFactory.getOrderDao());
    }

    public static SimpleServiceFactory getInstance(@NotNull DaoFactory daoFactory) {
        synchronized (SimpleServiceFactory.class) {
            if (instance == null) {
                instance = new SimpleServiceFactory(daoFactory);
            }
        }
        return instance;
    }

    @Override
    public AdminService getAdminService() {
        return adminService;
    }

    @Override
    public ClientService getClientService() {
        return clientService;
    }
}
