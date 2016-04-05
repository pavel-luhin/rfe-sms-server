package by.bsu.rfe.smsservice.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by pluhin on 3/20/16.
 */
@Entity
@Table(name = "sms_template")
public class SmsTemplateEntity {
    @Id
    @Column(name = "sms_id")
    private Integer id;
    @Column(name = "template", nullable = false)
    private String template;
    @OneToOne
    @JoinColumn(name = "sms_type", nullable = false)
    private SmsTypeEntity smsTypeEntity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SmsTypeEntity getSmsType() {
        return smsTypeEntity;
    }

    public void setSmsType(SmsTypeEntity smsTypeEntity) {
        this.smsTypeEntity = smsTypeEntity;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
