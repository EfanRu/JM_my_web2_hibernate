package dao;

import com.sun.istack.Nullable;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImplHib implements UserDao {
    private Session session;
    private SessionFactory sessionFactory;
    private Transaction transaction = session.getTransaction();

    public UserDaoImplHib(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();

        if (sessionFactory == null) {
            return result;
        }

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            result = session.createQuery("FROM User", User.class).list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return result;
    }

    public boolean addUser(User u) {
        if (sessionFactory == null) {
            return false;
        }

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            if (u != null) {
                session.save(u);
            }
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean delUser(String id) {
        if (sessionFactory == null) {
            return false;
        }

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.createQuery("DELETE FROM User WHERE id=:id")
                .setParameter("id", Long.parseLong(id))
                .executeUpdate();
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean updateUser(String id, String firstName, String lastName, String phoneNumber, String role, String login, String password) {
        if (sessionFactory == null) {
            return false;
        }

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.createQuery("UPDATE User SET first_name = :firstName, last_name = :lastName, phone_number = :phoneNumber, role = :role, login = :login, password = :password where id=:id")
                    .setParameter("id", Long.parseLong(id))
                    .setParameter("firstName", firstName)
                    .setParameter("lastName", lastName)
                    .setParameter("phoneNumber", Long.parseLong(phoneNumber))
                    .setParameter("role", role)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .executeUpdate();
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return false;
    }

    @Nullable
    public User checkAuth(String login, String password) {
        User user = null;

        if (sessionFactory == null) {
            return user;
        }

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            user = (User) session.createQuery("FROM User WHERE login = :login and password = :password")
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .uniqueResult();
            transaction.commit();
        } catch(RuntimeException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return user;
    }

    public void dropTable() {
        if (sessionFactory != null) {
            try {
                session = sessionFactory.openSession();
                session.beginTransaction();
                session.createQuery("DROP TABLE if EXISTS user")
                        .executeUpdate();
                transaction.commit();
            } catch (RuntimeException e) {
                transaction.rollback();
                e.printStackTrace();
            } finally {
                if (session != null) {
                    try {
                        session.close();
                    } catch (RuntimeException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}
