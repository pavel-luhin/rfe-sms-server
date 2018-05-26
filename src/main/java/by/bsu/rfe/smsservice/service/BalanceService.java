package by.bsu.rfe.smsservice.service;

/**
 * Service used to manage balance on sms gateway.
 */
public interface BalanceService {

  /**
   * Retrieves user balance.
   *
   * @param username username
   * @param apiKey api key to make api calls
   * @return actual balance for specified username
   */
  Double retrieveBalance(String username, String apiKey);
}
