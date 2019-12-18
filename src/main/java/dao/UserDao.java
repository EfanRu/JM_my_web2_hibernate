package dao;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public interface UserDao {
    List<User> getAllUsers();
    boolean addUser(User u);
    boolean delUser(String id);
    boolean updateUser(String id, String firstName, String lastName, String phoneNumber, String role, String login, String password);
    User checkAuth(String login, String password);
    void createTable();
    void dropTable();
}
