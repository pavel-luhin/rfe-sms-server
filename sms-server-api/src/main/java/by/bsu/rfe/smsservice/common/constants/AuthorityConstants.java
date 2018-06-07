package by.bsu.rfe.smsservice.common.constants;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class AuthorityConstants {

  public static final String ROLE_USER = "ROLE_USER";
  public static final String ROLE_APPLICATION = "ROLE_APPLICATION";
  public static final String ROLE_ACTUATOR = "ROLE_ACTUATOR";

  public static final GrantedAuthority USER_AUTHORITY = new SimpleGrantedAuthority(ROLE_USER);
  public static final GrantedAuthority APPLICATION_AUTHORITY = new SimpleGrantedAuthority(
      ROLE_APPLICATION);
  public static final GrantedAuthority ACTUATOR_AUTHORITY = new SimpleGrantedAuthority(
      ROLE_ACTUATOR);

  private AuthorityConstants() {
  }
}
