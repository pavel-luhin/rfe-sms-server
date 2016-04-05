package by.bsu.rfe.smsservice.common.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by pluhin on 3/20/16.
 */
@Entity
@Table(name = "email_template")
public class EmailEntity extends AbstractPersistable<Integer> {
    private String subject;
    private String content;
    @OneToOne
    @JoinColumn(name = "sms_type")
    private SmsTypeEntity smsType;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SmsTypeEntity getSmsType() {
        return smsType;
    }

    public void setSmsType(SmsTypeEntity smsType) {
        this.smsType = smsType;
    }
}