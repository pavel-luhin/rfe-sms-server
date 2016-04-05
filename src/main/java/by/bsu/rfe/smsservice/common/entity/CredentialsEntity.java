package by.bsu.rfe.smsservice.common.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by PLugin on 14.11.2015.
 */
@Entity
@Table(name = "credentials")
public class CredentialsEntity extends AbstractPersistable<Integer> {
    @Column(name = "username")
    private String username;
    @Column(name = "sender")
    private String sender;
    @Column(name = "api_key")
    private String apiKey;
    @OneToMany
    @JoinColumn(name = "credentials_id")
    private List<SmsTypeEntity> smsType;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<SmsTypeEntity> getSmsType() {
        return smsType;
    }

    public void setSmsType(List<SmsTypeEntity> smsType) {
        this.smsType = smsType;
    }
}
