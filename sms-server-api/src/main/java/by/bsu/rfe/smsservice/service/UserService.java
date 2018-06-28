package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.dto.ChangePasswordDTO;
import by.bsu.rfe.smsservice.common.dto.UserDTO;
import by.bsu.rfe.smsservice.common.entity.UserEntity;
import java.util.List;
import java.util.Set;

public interface UserService {

  UserEntity findByUsername(String username);

  UserEntity findById(Integer id);

  Set<String> getSenderNames();

  List<UserDTO> getAllUsers(Integer credentialsId);

  void createUser(String username);

  void removeUser(Integer id);

  void changePassword(ChangePasswordDTO passwordDTO);

  UserDTO getAccountInfo();
}
