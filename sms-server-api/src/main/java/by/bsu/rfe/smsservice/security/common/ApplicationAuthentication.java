package by.bsu.rfe.smsservice.security.common;

import static by.bsu.rfe.smsservice.common.constants.AuthorityConstants.APPLICATION_AUTHORITY;
import static java.util.Collections.singletonList;

import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class ApplicationAuthentication implements Authentication {

  private String applicationName;

  private String token;

  private boolean authenticated;

  public ApplicationAuthentication(String applicationName, String token) {
    this.applicationName = applicationName;
    this.token = token;
    this.authenticated = true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return singletonList(APPLICATION_AUTHORITY);
  }

  @Override
  public Object getCredentials() {
    return token;
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return applicationName;
  }

  @Override
  public boolean isAuthenticated() {
    return authenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.authenticated = isAuthenticated;
  }

  @Override
  public String getName() {
    return applicationName;
  }
}
