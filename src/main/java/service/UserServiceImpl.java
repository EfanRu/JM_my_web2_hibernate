package service;

import dao.UserDaoImplHib;
import model.User;
import org.hibernate.SessionFactory;
import util.DBHelper;
import util.UserDaoFactory;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl userService;
    private SessionFactory sessionFactory;
    private static UserDaoFactory userDaoFactory;
    private static String userDaoType;

    private UserServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static UserServiceImpl getInstance() {
        if (userService == null) {
            userService = new UserServiceImpl(DBHelper.getSessionFactory());
            userDaoFactory = new UserDaoFactory(DBHelper.getSessionFactory(), DBHelper.getConnection());
            userDaoType = "Hibernate";
//            userDaoType = "JDBC";
        }
        return userService;
    }

    private UserServiceImpl() {}

    public List<User> getAllUsers() {
        return userDaoFactory.getUserDao(userDaoType).getAllUsers();
    }

    public boolean addUser(User u) {
        return userDaoFactory.getUserDao(userDaoType).addUser(u);
    }

    public boolean delUser(String id) {
        return userDaoFactory.getUserDao(userDaoType).delUser(id);
    }

    public boolean updateUser(String id, String firstName, String lastName, String phoneNumber) {
        return userDaoFactory.getUserDao(userDaoType).updateUser(id, firstName, lastName, phoneNumber);
    }
}
