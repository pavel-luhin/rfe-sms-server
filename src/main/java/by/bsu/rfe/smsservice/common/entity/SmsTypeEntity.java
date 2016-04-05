package by.bsu.rfe.smsservice.common.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by pluhin on 3/20/16.
 */
@Entity
@Table(name = "sms_type")
public class SmsTypeEntity extends AbstractPersistable<Integer> {
    @Column(name = "sms_type", nullable = false)
    private String smsType;

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }
}
