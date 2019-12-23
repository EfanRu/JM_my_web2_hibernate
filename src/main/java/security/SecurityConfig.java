package security;

import java.util.*;

public class SecurityConfig {
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_USER = "user";

    private static final Map<String, List<String>> mapConfig = new HashMap<>();

    static {
        init();
    }

    private static void init() {
        List<String> urlPatternsAdmin = new ArrayList<String>();

        urlPatternsAdmin.add("/admin/add");
        urlPatternsAdmin.add("/admin/edit");
        urlPatternsAdmin.add("/admin/all");
        urlPatternsAdmin.add("/admin/delete");

        mapConfig.put(ROLE_ADMIN, urlPatternsAdmin);

        List<String> urlPatternsUser = new ArrayList<String>();

        urlPatternsUser.add("/user.jsp");

        mapConfig.put(ROLE_USER, urlPatternsUser);
    }

    public static Set<String> getAllAppRoles() {
        return mapConfig.keySet();
    }

    public static List<String> getUrlPatternsForRole(String role) {
        return mapConfig.get(role);
    }
}
