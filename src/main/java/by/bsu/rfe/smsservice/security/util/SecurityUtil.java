package by.bsu.rfe.smsservice.security.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by pluhin on 3/21/16.
 */
public class SecurityUtil {

    public static final String USER_AUTH_TOKEN_KEY = "auth_token";
    public static final String APPLICATION_AUTH_TOKEN_KEY = "application_token";

    public static final String ANONYMOUS_USERNAME = "Anonymous";

    public static String getUserAuthToken(HttpServletRequest request) {
        return getAuthenticationTokenFromRequest(request, USER_AUTH_TOKEN_KEY);
    }

    public static String getApplicationAuthToken(HttpServletRequest request) {
        return getAuthenticationTokenFromRequest(request, APPLICATION_AUTH_TOKEN_KEY);
    }

    private static String getAuthenticationTokenFromRequest(HttpServletRequest request, String tokenKey) {
        String token = request.getHeader(tokenKey);
        if (StringUtils.isEmpty(token)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(tokenKey)) {
                        token = cookie.getValue();
                    }
                }
            }
        }
        return token;
    }

    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null) {
            username = authentication.getName();
        }
        return StringUtils.isNotEmpty(username) ? username : ANONYMOUS_USERNAME;
    }
}
