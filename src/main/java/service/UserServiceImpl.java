package service;

import dao.UserDao;
import dao.UserDaoImplHib;
import model.User;
import org.hibernate.SessionFactory;
import util.DBHelper;
import util.UserDaoFactory;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl userService;
    private UserDaoFactory userDaoFactory = UserDaoFactory.getInstance();

    private UserServiceImpl() {}

    public static UserServiceImpl getInstance() {
        if (userService == null) {
            userService = new UserServiceImpl();
      }
        return userService;
    }

    @Override
    public List<User> getAllUsers() {
        return userDaoFactory.getUserDao().getAllUsers();
    }

    @Override
    public boolean addUser(User u) {
        return userDaoFactory.getUserDao().addUser(u);
    }

    @Override
    public boolean delUser(String id) {
        return userDaoFactory.getUserDao().delUser(id);
    }

    @Override
    public boolean updateUser(String id, String firstName, String lastName, String phoneNumber, String role, String login, String password) {
        return userDaoFactory.getUserDao().updateUser(id, firstName, lastName, phoneNumber, role, login, password);
    }

    @Override
    public User checkAuth(String login, String password) {
        return userDaoFactory.getUserDao().checkAuth(login, password);
    }
}
