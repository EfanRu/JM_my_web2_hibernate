package dao;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public interface UserDao {
    public List<User> getAllUsers();
    public boolean addUser(User u);
    public boolean delUser(String id);
    public boolean updateUser(String id, String firstName, String lastName, String phoneNumber);
    public void createTable();
    public void dropTable();
}
