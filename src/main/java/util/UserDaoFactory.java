package util;

import dao.UserDao;
import dao.UserDaoImplHib;
import dao.UserDaoImplJDBC;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.util.Properties;

public class UserDaoFactory {
    private static UserDaoFactory userDaoFactory;
    private DBHelper dbHelper = DBHelper.getInstance();
    private PropertyReader propReader = new PropertyReader("DB.property");

    private UserDaoFactory() {}

    public static UserDaoFactory getInstance() {
        if (userDaoFactory == null) {
            userDaoFactory = new UserDaoFactory();
        }
        return userDaoFactory;
    }

    public UserDao getUserDao() {
        String str = propReader.getProperty("db.type");
        if (str.equals("Hibernate")) {
            return new UserDaoImplHib(dbHelper.getSessionFactory());
        }else {
            return new UserDaoImplJDBC(dbHelper.getConnection());
        }
    }
}
