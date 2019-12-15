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
    private static PropertyReader propertyReader = new PropertyReader();
    private static SessionFactory sessionFactory;
    private static DBHelper instance;

    private DBHelper() {}

    public DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }
        return instance;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = createSessionFactory();
        }
        return sessionFactory;
    }

    private static SessionFactory createSessionFactory() {
        Configuration conf = getConfiguration();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(conf.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return conf.buildSessionFactory(serviceRegistry);
    }

    public static Configuration getConfiguration() {
        String host = propertyReader.getProperty("db.host");
        String port = propertyReader.getProperty("db.port");
        String name = propertyReader.getProperty("db.name");
        String login = propertyReader.getProperty("db.login");
        String password = propertyReader.getProperty("db.password");
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

    public static Connection getMysqlConnection() {
        String host = propertyReader.getProperty("db.host");
        String port = propertyReader.getProperty("db.port");
        String name = propertyReader.getProperty("db.name");
        String login = propertyReader.getProperty("db.login");
        String password = propertyReader.getProperty("db.password");

        try {
            DriverManager.registerDriver(new Driver());
            StringBuilder url = new StringBuilder();
            url.append("jdbc:mysql://")
                    .append("localhost:")
                    .append("3306/")
                    .append("test?")
                    .append("user=root&")
                    .append("password=root");
            return DriverManager.getConnection(url.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }
}
