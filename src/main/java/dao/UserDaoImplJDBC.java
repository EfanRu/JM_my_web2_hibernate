package dao;

import com.sun.istack.Nullable;
import model.User;
import org.hibernate.Session;
import util.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImplJDBC implements UserDao {
    private Connection con;

    public UserDaoImplJDBC() {}

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        con = DBHelper.getInstance().getConnection();

        if (con == null) {
            return result;
        }

        try (Statement stmt = con.createStatement()) {
            con.setAutoCommit(false);
            stmt.execute("SELECT * FROM user");
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                result.add(new User(rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getLong(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public boolean addUser(User u) {
        String sql = "INSERT INTO user VALUES(null, ?, ?, ?, ?, ?, ?)";
        con = DBHelper.getInstance().getConnection();

        if (con == null) {
            return false;
        }

        try (PreparedStatement pstmt = con.prepareStatement(sql)){
            con.setAutoCommit(false);
            pstmt.setString(1, u.getFirstName());
            pstmt.setString(2, u.getLastName());
            pstmt.setLong(3, u.getPhoneNumber());
            pstmt.setString(4, u.getRole());
            pstmt.setString(4, u.getLogin());
            pstmt.setString(4, u.getPassword());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean delUser(String id) {
        String sql = "DELETE FROM user WHERE id=?";
        con = DBHelper.getInstance().getConnection();

        if (con == null) {
            return false;
        }

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            pstmt.setLong(1, Long.parseLong(id));
            pstmt.execute();
            return true;
        } catch (SQLException | NumberFormatException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean updateUser(String id, String firstName, String lastName, String phoneNumber, String role, String login, String password) {
        String sql = "UPDATE user SET first_name = ?, last_name = ?, phone_number = ?, role = ?, login = ?, password = ? where id=?";
        con = DBHelper.getInstance().getConnection();

        if (con == null) {
            return false;
        }

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setLong(3, Long.parseLong(phoneNumber));
            pstmt.setLong(4, Long.parseLong(id));
            pstmt.setString(5, role);
            pstmt.setString(6, login);
            pstmt.setString(7, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            try {
                con.rollback();
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public User checkAuth(String login, String password) {
        User user = null;
        String sql = "SELECT * FROM User WHERE login=? and password=?";
        con = DBHelper.getInstance().getConnection();

        if (con == null) {
            return user;
        }

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, login);
            pstmt.setString(2, login);
            ResultSet rs = pstmt.getResultSet();
            if (rs.next()) {
                user = new User(rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getLong(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void createTable() {
        con = DBHelper.getInstance().getConnection();
        try (Statement stmt = con.createStatement()) {
            stmt.execute("CREATE TABLE if NOT EXISTS user (id bigint auto_increment, first_name varchar(256), last_name varchar(256), phone_number bigint, role varchar(128), login varchar(128), password varchar(128) primary key (id))");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTable() {
        con = DBHelper.getInstance().getConnection();
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate("DROP TABLE if EXISTS user");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}