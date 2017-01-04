package by.bsu.rfe.smsservice.controller;

import by.bsu.rfe.smsservice.common.dto.AuthenticationDTO;
import by.bsu.rfe.smsservice.common.dto.UserDTO;
import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import by.bsu.rfe.smsservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by pluhin on 3/21/16.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO authenticate(@RequestBody AuthenticationDTO authenticationDTO) {
        return userService.authenticate(authenticationDTO);
    }

    @ResponseBody
    @RequestMapping(value = "/validateToken", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO validateToken(@RequestBody String token) {
        return userService.validateToken(token);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String token = SecurityUtil.getUserAuthToken(request);
        userService.logout(request, response, token);
    }

    @ResponseBody
    @RequestMapping(value = "/senderNames", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<String> getSenderNamesForUser() {
        return userService.getSenderNames();
    }
}
