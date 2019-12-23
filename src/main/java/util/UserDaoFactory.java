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
//        PropertyReader propReader = new PropertyReader("DB.property");
        UserDaoImplHib userDaoImplHib = new UserDaoImplHib(dbHelper.getSessionFactory());
        UserDaoImplJDBC userDaoImplJDBC = new UserDaoImplJDBC();

        String str = PropertyReader.getProperty("db.type", "DB.property");
        if (str.equals("Hibernate")) {
            return userDaoImplHib;
        }else {
            return userDaoImplJDBC;
        }
    }
}
