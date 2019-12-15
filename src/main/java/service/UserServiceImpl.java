package service;

import dao.UserDaoImpl;
import model.User;
import org.hibernate.SessionFactory;
import util.DBHelper;
import util.UserDaoFactory;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl userService;
    private SessionFactory sessionFactory;
    private UserDaoFactory userDaoFactory = new UserDaoFactory();

    private UserServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static UserServiceImpl getInstance() {
        if (userService == null) {
            userService = new UserServiceImpl(DBHelper.getSessionFactory());
        }
        return userService;
    }

    private UserServiceImpl() {}

    public List<User> getAllUsers() {
        return userDaoFactory.getUserDao("Type1").getAllUsers();
    }

    public boolean addUser(User u) {
        return new UserDaoImpl(sessionFactory.openSession()).addUser(u);
    }

    public boolean delUser(String id) {
        return new UserDaoImpl(sessionFactory.openSession()).delUser(id);
    }

    public boolean updateUser(String id, String firstName, String lastName, String phoneNumber) {
        return new UserDaoImpl(sessionFactory.openSession()).updateUser(id, firstName, lastName, phoneNumber);
    }
}
