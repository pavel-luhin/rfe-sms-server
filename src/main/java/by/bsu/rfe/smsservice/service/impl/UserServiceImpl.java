package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.common.dto.AuthenticationDTO;
import by.bsu.rfe.smsservice.common.dto.UserDTO;
import by.bsu.rfe.smsservice.common.entity.AuthenticationTokenEntity;
import by.bsu.rfe.smsservice.common.entity.UserEntity;
import by.bsu.rfe.smsservice.repository.UserRepository;
import by.bsu.rfe.smsservice.service.UserService;
import org.apache.commons.lang3.time.DateUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by pluhin on 3/20/16.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Mapper mapper;

    @Override
    @Transactional
    public UserDTO authenticate(AuthenticationDTO authenticationDTO) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(), authenticationDTO.getPassword());
        authenticationManager.authenticate(authentication);
        UserEntity userEntity = userRepository.findByUsername(authenticationDTO.getUsername());
        String token = generateToken();
        AuthenticationTokenEntity tokenEntity = new AuthenticationTokenEntity();
        tokenEntity.setToken(token);
        tokenEntity.setExpires(DateUtils.addWeeks(new Date(), 2));
        if (userEntity.getTokens() == null) {
            userEntity.setTokens(new ArrayList<AuthenticationTokenEntity>());
        }
        userEntity.getTokens().add(tokenEntity);
        userRepository.saveAndFlush(userEntity);

        UserDTO userDTO = mapper.map(userEntity, UserDTO.class);
        userDTO.setToken(token);
        return userDTO;
    }

    @Override
    public UserDTO validateToken(String token) {
        UserEntity userEntity = userRepository.findByToken(token);
        if (userEntity != null) {
            return mapper.map(userEntity, UserDTO.class);
        }
        return null;
    }

    @Override
    public UserEntity findByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            return userEntity;
        } else {
            throw new UsernameNotFoundException("Invalid username");
        }
    }

    @Override
    public UserEntity getUserByToken(String token) {
        return userRepository.findByToken(token);
    }

    @Override
    public void logout(HttpServletRequest req, HttpServletResponse resp, String token) {
        UserEntity userEntity = getUserByToken(token);

        findAndRemoveToken(token, userEntity.getTokens());
        userRepository.saveAndFlush(userEntity);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(req, resp, auth);
        }
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private void findAndRemoveToken(String token, List<AuthenticationTokenEntity> tokens) {
        Iterator<AuthenticationTokenEntity> tokenIterator = tokens.iterator();
        while (tokenIterator.hasNext()) {
            AuthenticationTokenEntity tokenEntity = tokenIterator.next();
            if (tokenEntity.getToken().equals(token)) {
                tokenIterator.remove();
            }
        }
    }
}
