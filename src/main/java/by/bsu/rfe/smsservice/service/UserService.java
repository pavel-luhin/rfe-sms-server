package by.bsu.rfe.smsservice.service;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.rfe.smsservice.common.dto.AuthenticationDTO;
import by.bsu.rfe.smsservice.common.dto.CredentialsDTO;
import by.bsu.rfe.smsservice.common.dto.UserDTO;
import by.bsu.rfe.smsservice.common.entity.UserEntity;

/**
 * Created by pluhin on 3/20/16.
 */
public interface UserService {
    UserDTO authenticate(AuthenticationDTO authenticationDTO);

    UserDTO validateToken(String token);

    UserEntity findByUsername(String username);

    UserEntity findById(Integer id);

    UserEntity getUserByToken(String token);

    void logout(HttpServletRequest req, HttpServletResponse resp, String token);

    Set<String> getSenderNames();

    void addNewCredentials(CredentialsDTO credentialsDTO);

    List<UserDTO> getAllUsers();

    void createUser(String username);

    void removeUser(Integer id);
}
