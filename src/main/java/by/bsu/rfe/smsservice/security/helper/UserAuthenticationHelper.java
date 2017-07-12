package by.bsu.rfe.smsservice.security.helper;

import by.bsu.rfe.smsservice.common.entity.UserEntity;
import by.bsu.rfe.smsservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by pluhin on 7/12/17.
 */
@Component
public class UserAuthenticationHelper implements AuthenticationHelper {

    @Autowired
    private UserService userService;

    @Override
    public Authentication tryWith(String token) {
        UserEntity userEntity = userService.getUserByToken(token);

        if (userEntity == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(
                userEntity.getUsername(),
                userEntity.getPassword(),
                new ArrayList<>());
    }
}
