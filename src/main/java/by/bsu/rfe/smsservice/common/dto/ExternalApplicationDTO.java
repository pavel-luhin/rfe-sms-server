package by.bsu.rfe.smsservice.common.dto;

/**
 * Created by pluhin on 9/3/16.
 */
public class ExternalApplicationDTO {
    private String applicationName;
    private String authenticationToken;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }
}
