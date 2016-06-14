package by.bsu.rfe.smsservice.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.rfe.smsservice.security.util.SecurityUtil;

/**
 * Created by pluhin on 6/14/16.
 */
public class RequestLoggerInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLoggerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startMillis = System.currentTimeMillis();
        request.setAttribute("startMillis", startMillis);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startMillis = (long) request.getAttribute("startMillis");
        long endMillis = System.currentTimeMillis();
        LOGGER.info("Request for user " + SecurityUtil.getCurrentUsername() + " to " + request.getRequestURI() + " took " + (endMillis - startMillis));
    }
}
