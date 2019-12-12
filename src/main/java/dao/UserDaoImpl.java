package dao;

import model.User;
import org.hibernate.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private Session sess;

    public UserDaoImpl(Session session) {
        this.sess = session;
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();

        if (sess == null) {
            return result;
        }

        try {
            sess.beginTransaction();
            result = sess.createQuery("FROM users").list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            sess.close();
        }
        return result;
    }

    public boolean addUser(User u) {
        if (sess == null) {
            return false;
        }

        try {
            sess.beginTransaction();
            if (u != null) {
                sess.save(u);
            }
            sess.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            sess.getTransaction().rollback();
        } finally {
            sess.close();
        }
        return false;
    }

    public boolean delUser(String id) {
        if (sess == null) {
            return false;
        }

        try {
            sess.beginTransaction();
            sess.createQuery("DELETE FROM users WHERE id=:id")
                .setParameter("id", Long.parseLong(id))
                .executeUpdate();
            sess.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            sess.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            sess.getTransaction().rollback();
        }
        return false;
    }

    public boolean updateUser(String id, String firstName, String lastName, String phoneNumber) {
        if (sess == null) {
            return false;
        }

        try {
            sess.beginTransaction();
            sess.createQuery("UPDATE users SET first_name = :firstName, last_name = lastName, phone_number = phoneNumber where id=:id")
                    .setParameter("id", Long.parseLong(id))
                    .setParameter("firstName", firstName)
                    .setParameter("lastName", lastName)
                    .setParameter("phoneNumber", Long.parseLong(phoneNumber))
                    .executeUpdate();
            sess.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            sess.getTransaction().rollback();
        } finally {
            sess.close();
        }
        return false;
    }

    public void createTable() {
        if (sess != null) {
            try {
                sess.beginTransaction();
                sess.createQuery("CREATE TABLE if NOT EXISTS users (id bigint auto_increment, first_name varchar(256), last_name varchar(256), phone_number bigint, primary key (id))")
                        .executeUpdate();
                sess.getTransaction().commit();
            } catch (RuntimeException e) {
                sess.getTransaction().rollback();
                e.printStackTrace();
            } finally {
                sess.close();
            }
        }
    }

    public void dropTable() {
        if (sess != null) {
            try {
                sess.beginTransaction();
                sess.createQuery("DROP TABLE if EXISTS user")
                        .executeUpdate();
                sess.getTransaction().commit();
            } catch (RuntimeException e) {
                sess.getTransaction().rollback();
                e.printStackTrace();
            } finally {
                sess.close();
            }
        }
    }
}
