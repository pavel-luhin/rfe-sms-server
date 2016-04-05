package by.bsu.rfe.smsservice.security.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by pluhin on 3/21/16.
 */
public class SecurityUtil {
    public static final String AUTH_TOKEN_KEY = "auth_token";

    public static String getAuthToken(HttpServletRequest request) {
        String token = request.getHeader(AUTH_TOKEN_KEY);
        if (StringUtils.isEmpty(token)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(AUTH_TOKEN_KEY)) {
                        token = cookie.getValue();
                    }
                }
            }
        }
        return token;
    }
}
