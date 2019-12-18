package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImplHib implements UserDao {
    private Session sess;
    private SessionFactory sessFact;

    public UserDaoImplHib(SessionFactory sessionFactory) {
        this.sessFact = sessionFactory;
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();

        if (sessFact == null) {
            return result;
        }

        try {
            sess = sessFact.openSession();
            sess.beginTransaction();
            result = sess.createQuery("FROM User", User.class).list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                sess.close();
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public boolean addUser(User u) {
        if (sessFact == null) {
            return false;
        }

        try {
            sess = sessFact.openSession();
            sess.beginTransaction();
            if (u != null) {
                sess.save(u);
            }
            sess.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            sess.getTransaction().rollback();
        } finally {
            try {
                sess.close();
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean delUser(String id) {
        if (sessFact == null) {
            return false;
        }

        try {
            sess = sessFact.openSession();
            sess.beginTransaction();
            sess.createQuery("DELETE FROM User WHERE id=:id")
                .setParameter("id", Long.parseLong(id))
                .executeUpdate();
            sess.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            sess.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            try {
                sess.close();
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean updateUser(String id, String firstName, String lastName, String phoneNumber, String role, String login, String password) {
        if (sessFact == null) {
            return false;
        }

        try {
            sess = sessFact.openSession();
            sess.beginTransaction();
            sess.createQuery("UPDATE User SET first_name = :firstName, last_name = :lastName, phone_number = :phoneNumber, role = :role, login = :login, password = :password where id=:id")
                    .setParameter("id", Long.parseLong(id))
                    .setParameter("firstName", firstName)
                    .setParameter("lastName", lastName)
                    .setParameter("phoneNumber", Long.parseLong(phoneNumber))
                    .setParameter("role", role)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .executeUpdate();
            sess.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            sess.getTransaction().rollback();
        } finally {
            try {
                sess.close();
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean checkAuth(String login, String password) {
        if (sessFact == null) {
            return false;
        }

        try {
            sess = sessFact.openSession();
            sess.beginTransaction();
//            sess.createQuery("FROM User WHERE ", User.class).uniqueResult();

        } catch(RuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                sess.close();
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
        }

        return ;
    }

        public void createTable() {
        if (sessFact != null) {
        try {
                sess = sessFact.openSession();
                sess.beginTransaction();
                sess.createQuery("CREATE TABLE if NOT EXISTS user (id bigint auto_increment, first_name varchar(256), last_name varchar(256), phone_number bigint, role varchar(128), login varchar(128), password varchar(128) primary key (id))")
                        .executeUpdate();
                sess.getTransaction().commit();
            } catch (RuntimeException e) {
                sess.getTransaction().rollback();
                e.printStackTrace();
            } finally {
                try {
                    sess.close();
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void dropTable() {
        if (sessFact != null) {
            try {
                sess = sessFact.openSession();
                sess.beginTransaction();
                sess.createQuery("DROP TABLE if EXISTS user")
                        .executeUpdate();
                sess.getTransaction().commit();
            } catch (RuntimeException e) {
                sess.getTransaction().rollback();
                e.printStackTrace();
            } finally {
                try {
                    sess.close();
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
