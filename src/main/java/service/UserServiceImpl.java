package service;

import dao.UserDao;
import model.User;
import util.UserDaoFactory;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl userService;
    private UserDaoFactory userDaoFactory = UserDaoFactory.getInstance();
    private UserDao userDao = userDaoFactory.getUserDao();

    private UserServiceImpl() {}

    public static UserServiceImpl getInstance() {
        if (userService == null) {
            userService = new UserServiceImpl();
      }
        return userService;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public boolean addUser(User u) {
        return userDao.addUser(u);
    }

    @Override
    public boolean delUser(String id) {
        return userDao.delUser(id);
    }

    @Override
    public boolean updateUser(String id, String firstName, String lastName, String phoneNumber, String role, String login, String password) {
        return userDao.updateUser(id, firstName, lastName, phoneNumber, role, login, password);
    }

    @Override
    public User checkAuth(String login, String password) {
        return userDao.checkAuth(login, password);
    }
}
