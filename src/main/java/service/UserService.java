package service;

import model.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();
    public boolean addUser(User u);
    public boolean delUser(String id);
    public boolean updateUser(String id, String firstName, String lastName, String phoneNumber);
}
