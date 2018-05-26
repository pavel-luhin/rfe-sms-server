package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.dto.CredentialsDTO;
import by.bsu.rfe.smsservice.common.dto.ShareCredentialsDTO;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import java.util.List;

/**
 * Service used to manage sms server credentials.
 */
public interface CredentialsService {

  /**
   * Gets all server credentials.
   *
   * @return all credentials
   */
  List<CredentialsEntity> getAllCredentials();

  /**
   * Gets default credentials for current user.
   *
   * @return default credentials for current user.
   */
  CredentialsEntity getDefaultCredentialsForCurrentUser();

  /**
   * Gets credentials for specified sender name.
   *
   * @param senderName sender name to filter credentials.
   * @return founded credentials
   */
  CredentialsEntity getCredentialsForSenderName(String senderName);

  /**
   * Gets credentials for sender name and filters them by current user.
   *
   * @param senderName sender name to filter credentials.
   * @return credentials of found, null otherwise
   */
  CredentialsEntity getUserCredentialsForSenderName(String senderName);

  /**
   * Saves credentials.
   *
   * @param credentialsEntity new credentials entity.
   */
  void saveCredentials(CredentialsEntity credentialsEntity);

  /**
   * Gets all user credentials.
   *
   * @param username user credentials.
   * @return all user credentials
   */
  List<CredentialsDTO> getUserCredentials(String username);

  /**
   * Removes credentials by id.
   *
   * @param id credentials identifier
   */
  void removeCredentials(Integer id);

  /**
   * Share credentials with other user to let him use them to send sms.
   *
   * @param shareCredentialsDTO object with credentials id and user id to share credentials with
   */
  void shareCredentials(ShareCredentialsDTO shareCredentialsDTO);

  /**
   * Gets credentials by some identifier.
   *
   * @param id credentials id
   * @return credentials
   */
  CredentialsEntity getCredentialsById(Integer id);
}
