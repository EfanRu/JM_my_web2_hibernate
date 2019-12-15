package util;

import dao.UserDao;
import dao.UserDaoImpl;
import org.hibernate.SessionFactory;

public class UserDaoFactory {
    private static SessionFactory sessionFactory;
    private static UserDao userDaoType1 = new UserDaoImpl(sessionFactory.openSession());
    private static UserDao userDaoType2 = new UserDaoImpl(sessionFactory.openSession());
    private PropertyReader propReader = new PropertyReader();

    public UserDaoFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UserDao getUserDao(String property) {
        String str = propReader.getProperty(property, "DAO.property");
        if (str.equals("Type1")) {
            return userDaoType1;
        }
        if (str.equals("Type2")) {
            return userDaoType2;
        }
        return new UserDaoImpl(sessionFactory.openSession());
    }
}
