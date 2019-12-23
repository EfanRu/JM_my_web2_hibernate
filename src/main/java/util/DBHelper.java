package util;

import com.mysql.cj.jdbc.Driver;
import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    private static SessionFactory sessionFactory;
    private static DBHelper instance;
    private static String host = PropertyReader.getProperty("db.host");
    private static String port = PropertyReader.getProperty("db.port");
    private static String name = PropertyReader.getProperty("db.name");
    private static String login = PropertyReader.getProperty("db.login");
    private static String password = PropertyReader.getProperty("db.password");


    private DBHelper() {}

    public static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }
        return instance;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = createSessionFactory();
        }
        return sessionFactory;
    }

    private SessionFactory createSessionFactory() {
        Configuration conf = getConfiguration();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(conf.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return conf.buildSessionFactory(serviceRegistry);
    }

    private Configuration getConfiguration() {
        Configuration conf = new Configuration();
        StringBuilder sb = new StringBuilder();

        conf.addAnnotatedClass(User.class);

        conf.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        conf.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        sb.append("jdbc:mysql://")
            .append(host)
            .append(":")
            .append(port)
            .append("/")
            .append(name);
        conf.setProperty("hibernate.connection.url", sb.toString());
        conf.setProperty("hibernate.connection.username", login);
        conf.setProperty("hibernate.connection.password", password);
        conf.setProperty("hibernate.show_sql", "true");
        conf.setProperty("hibernate.hbm2ddl.auto", "create");
        return conf;
    }

    public Connection getConnection() {
        try {
            DriverManager.registerDriver(new Driver());
            StringBuilder url = new StringBuilder();
            url.append("jdbc:mysql://")
                    .append(host)
                    .append(":")
                    .append(port)
                    .append("/")
                    .append(name);
            return DriverManager.getConnection(url.toString(), login, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }
}
