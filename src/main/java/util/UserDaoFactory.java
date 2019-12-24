package util;

import dao.UserDao;
import dao.UserDaoImplHib;
import dao.UserDaoImplJDBC;

public class UserDaoFactory {
    private static UserDaoFactory userDaoFactory;

    private UserDaoFactory() {}

    public static UserDaoFactory getInstance() {
        if (userDaoFactory == null) {
            userDaoFactory = new UserDaoFactory();
        }
        return userDaoFactory;
    }

    public UserDao getUserDao() {
        DBHelper dbHelper = DBHelper.getInstance();

        String str = PropertyReader.getProperty("db.type");
        if (str.equals("Hibernate")) {
            return new UserDaoImplHib(dbHelper.getSessionFactory());
        }else {
            return new UserDaoImplJDBC();
        }
    }
}
