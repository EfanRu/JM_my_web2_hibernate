package service;

import dao.UserDaoImpl;
import model.User;

import java.util.List;

import static util.DBHelper.getMysqlConnection;

public interface UserService {
    public List<User> getAllUsers();
    public boolean addUser(User u);
    public boolean delUser(String id);
    public boolean updateUser(String id, String firstName, String lastName, String phoneNumber);
}
