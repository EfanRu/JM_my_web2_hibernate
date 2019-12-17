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
    private SessionFactory sessionFactory;
    private UserDaoFactory userDaoFactory = UserDaoFactory.getInstance();
    private String userDaoType = "Hibernate";

    private UserServiceImpl() {}

    public static UserServiceImpl getInstance() {
        if (userService == null) {
            userService = new UserServiceImpl();
      }
        return userService;
    }

    public List<User> getAllUsers() {
        return userDaoFactory.getUserDao().getAllUsers();
    }

    public boolean addUser(User u) {
        return userDaoFactory.getUserDao().addUser(u);
    }

    public boolean delUser(String id) {
        return userDaoFactory.getUserDao().delUser(id);
    }

    public boolean updateUser(String id, String firstName, String lastName, String phoneNumber) {
        return userDaoFactory.getUserDao().updateUser(id, firstName, lastName, phoneNumber);
    }
}
