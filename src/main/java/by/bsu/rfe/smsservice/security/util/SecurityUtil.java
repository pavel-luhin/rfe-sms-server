package by.bsu.rfe.smsservice.security.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;

/**
 * Created by pluhin on 3/21/16.
 */
public class SecurityUtil {

    public static final String AUTH_TOKEN_KEY = "auth_token";
    public static final String ANONYMOUS_USERNAME = "Anonymous";

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

    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null) {
            username = authentication.getName();
        }
        return StringUtils.isNotEmpty(username) ? username : ANONYMOUS_USERNAME;
    }

    public static List<CredentialsEntity> getCurrentUserCredentials() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (List<CredentialsEntity>) authentication.getDetails();
    }
}
