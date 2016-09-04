package by.bsu.rfe.smsservice.common.dto;

/**
 * Created by pluhin on 9/5/16.
 */
public class ShareCredentialsDTO {
    private Integer userId;
    private Integer credentialsId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(Integer credentialsId) {
        this.credentialsId = credentialsId;
    }
}
