package util;

import dao.UserDao;
import dao.UserDaoImplHib;
import dao.UserDaoImplJDBC;
import org.hibernate.SessionFactory;

import java.sql.Connection;

public class UserDaoFactory {
    private static UserDaoFactory userDaoFactory;
    private static SessionFactory sessionFactory;
    private static Connection connection;
    private static UserDao userDaoTypeHib = new UserDaoImplHib(sessionFactory.openSession());
    private static UserDao userDaoTypeJDBC = new UserDaoImplJDBC(connection);
    private PropertyReader propReader = new PropertyReader();

    private UserDaoFactory() {}

    public static UserDaoFactory getInstance(SessionFactory sessionFactory, Connection connection) {
        if (userDaoFactory == null) {
            userDaoFactory = new UserDaoFactory(sessionFactory, connection);
        }
        return userDaoFactory;
    }

    private UserDaoFactory(SessionFactory sessionFactory, Connection connection) {
        this.sessionFactory = sessionFactory;
        this.connection = connection;
    }

    public UserDao getUserDao(String property) {
        String str = propReader.getProperty(property, "DAO.property");
        if (str.equals("Hibernate")) {
            return userDaoTypeHib;
        }
        if (str.equals("JDBC")) {
            return userDaoTypeJDBC;
        }
        return new UserDaoImplHib(sessionFactory.openSession());
    }
}
