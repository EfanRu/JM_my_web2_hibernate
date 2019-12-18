package service;

import model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    boolean addUser(User u);
    boolean delUser(String id);
    boolean updateUser(String id, String firstName, String lastName, String phoneNumber, String role, String login, String password);
    User checkAuth(String login, String password);
}
