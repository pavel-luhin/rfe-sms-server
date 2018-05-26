package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.entity.AuthenticationTokenEntity;
import java.util.List;

/**
 * Service used to manage authentication tokens.
 */
public interface AuthenticationTokenService {

  /**
   * Returns all expired tokens. This means that this method will return all tokens, where expired
   * date less or equals now.
   *
   * @return expired tokens
   */
  List<AuthenticationTokenEntity> getExpiredTokens();

  /**
   * Removes token by identifier.
   *
   * @param id token identifier to remove
   */
  void removeToken(Integer id);
}
