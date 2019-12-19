package security;


import model.User;
import service.UserServiceImpl;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private static AccountManager accMng;
    private static Map<User, HttpSession> accMap = new HashMap<>();
    private static User admin = new User("Root admin", "Root admin", "admin", "admin", 0L, "admin");

    private AccountManager() {}

    public static AccountManager getInstance() {
        if (accMng == null) {
            accMng = new AccountManager();
        }
        accMap.put(admin, null);
        UserServiceImpl.getInstance().addUser(admin);
        return accMng;
    }
}
