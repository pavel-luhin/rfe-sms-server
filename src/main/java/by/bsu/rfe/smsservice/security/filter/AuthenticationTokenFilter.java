package by.bsu.rfe.smsservice.security.filter;

import by.bsu.rfe.smsservice.security.helper.AuthenticationHelper;
import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by pluhin on 3/21/16.
 */
public class AuthenticationTokenFilter extends GenericFilterBean {

    @Autowired
    private List<AuthenticationHelper> authenticationHelpers;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

            String token = SecurityUtil.getUserAuthToken(httpServletRequest);

            Authentication authentication = null;

            for (AuthenticationHelper helper : authenticationHelpers) {
                authentication = helper.tryWith(token);

                if (authentication != null) {
                    break;
                }
            }

            if (authentication == null) {
                throw new BadCredentialsException("Invalid access token");
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
