package util;

import com.sun.istack.Nullable;
import model.User;
import service.UserServiceImpl;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class AppUtil {
    private static int REDIRECT_ID = 0;
    private static final Map<Integer, String> idUriMap = new HashMap<>();
    private static final Map<String, Integer> uriIdMap = new HashMap<>();

    public static void storeLoggedUser(HttpSession session, User user) {
        session.setAttribute("loginedUser", user);
    }

    public static void delLoggedUser(HttpSession session) {
        session.removeAttribute("loginedUser");
    }

    public static User getLoginedUser(HttpSession session) {
        return (User) session.getAttribute("loginedUser");
    }

    public static User checkAuth(String login, String password) {
        return UserServiceImpl.getInstance().checkAuth(login, password);
    }

    public static int storeRedirectAfterLoginUrl(HttpSession session, String reqUri) {
        Integer id = uriIdMap.get(reqUri);

        if (id == null) {
            id = REDIRECT_ID++;

            uriIdMap.put(reqUri, id);
            idUriMap.put(id, reqUri);
            return id;
        }
        return id;
    }

    @Nullable
    public static String getRedirectAfterLoginUrl(HttpSession session, int redirectId) {
        String url = idUriMap.get(redirectId);
        if (url != null) {
            return url;
        }
        return null;
    }
}
