package util;

import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DBHelper {
    private static PropertyReader propertyReader = new PropertyReader();
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = createSessionFactory();
        }
        return sessionFactory;
    }

    private static SessionFactory createSessionFactory() {
        Configuration conf = getMysqlConnection();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(conf.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return conf.buildSessionFactory(serviceRegistry);
    }

    public static Configuration getMysqlConnection() {
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
//        conf.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3307/test");
        conf.setProperty("hibernate.connection.url", sb.toString());
        conf.setProperty("hibernate.connection.username", login);
        conf.setProperty("hibernate.connection.password", password);
        conf.setProperty("hibernate.show_sql", "true");
        conf.setProperty("hibernate.hbm2ddl.auto", "create");
        return conf;
    }
}
