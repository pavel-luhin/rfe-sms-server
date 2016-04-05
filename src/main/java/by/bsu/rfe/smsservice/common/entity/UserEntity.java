package by.bsu.rfe.smsservice.common.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by pluhin on 3/20/16.
 */
@Entity
@Table(name = "user")
public class UserEntity extends AbstractPersistable<Integer> {
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<AuthenticationTokenEntity> tokens;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AuthenticationTokenEntity> getTokens() {
        return tokens;
    }

    public void setTokens(List<AuthenticationTokenEntity> tokens) {
        this.tokens = tokens;
    }
}