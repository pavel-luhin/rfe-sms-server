package by.bsu.rfe.smsservice.common.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Created by PLugin on 14.11.2015.
 */
@Entity
@Table(name = "credentials")
public class CredentialsEntity extends CreationDetails {
    @Column(name = "username")
    private String username;
    @Column(name = "sender")
    private String sender;
    @Column(name = "api_key")
    private String apiKey;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_credentials"
    )
    private Set<UserEntity> users = new HashSet<>();

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

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }
}
