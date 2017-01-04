package by.bsu.rfe.smsservice.security.filter;

import by.bsu.rfe.smsservice.common.entity.ExternalApplicationEntity;
import by.bsu.rfe.smsservice.common.entity.UserEntity;
import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import by.bsu.rfe.smsservice.service.ExternalApplicationService;
import by.bsu.rfe.smsservice.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by pluhin on 3/21/16.
 */
public class AuthenticationTokenFilter extends GenericFilterBean {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ExternalApplicationService externalApplicationService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

            String token = SecurityUtil.getUserAuthToken(httpServletRequest);

            if (StringUtils.isNotEmpty(token)) {
                UserEntity userEntity = userService.getUserByToken(token);
                if (userEntity != null) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userEntity.getUsername(),
                            userEntity.getPassword(),
                            new ArrayList<GrantedAuthority>());
                    authenticationManager.authenticate(authentication);
                }
            } else {
                token = SecurityUtil.getApplicationAuthToken(httpServletRequest);

                if (StringUtils.isNotEmpty(token)) {
                    ExternalApplicationEntity applicationEntity = externalApplicationService.getByToken(token);

                    if (applicationEntity != null) {
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                applicationEntity.getApplicationName(),
                                applicationEntity.getAuthenticationToken(),
                                new ArrayList<>()
                        );
                        authenticationManager.authenticate(authentication);
                    }
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
