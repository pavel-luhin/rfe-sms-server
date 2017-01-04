package by.bsu.rfe.smsservice.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Created by pluhin on 3/20/16.
 */
@Entity
@Table(name = "sms_template")
public class SmsTemplateEntity extends CreationDetails {
    @Lob
    @Column(name = "template")
    private String template;
    @Column(name = "sms_type", nullable = false)
    private String smsType;
    @Column(name = "uri_path", nullable = false, unique = true)
    private String uriPath;
    @Column(name = "enabled")
    private Boolean enabled;

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getUriPath() {
        return uriPath;
    }

    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
