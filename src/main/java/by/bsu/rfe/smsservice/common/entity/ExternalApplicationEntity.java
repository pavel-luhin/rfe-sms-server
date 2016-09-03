package by.bsu.rfe.smsservice.common.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by pluhin on 9/3/16.
 */
@Entity
@Table(name = "external_application")
public class ExternalApplicationEntity extends AbstractPersistable<Integer> {

    @Column(name = "application_name", nullable = false)
    private String applicationName;

    @Column(name = "authentication", nullable = false)
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
