package by.bsu.rfe.smsservice.common.entity;

import by.bsu.rfe.smsservice.common.enums.SmsServerProperty;

import javax.persistence.*;

/**
 * Created by pluhin on 1/4/17.
 */
@Entity
@Table(name = "sms_server_property")
public class SmsServerPropertyEntity {

    @Id
    @Column(name = "property_key")
    @Enumerated(EnumType.STRING)
    private SmsServerProperty propertyKey;

    @Column(name = "value")
    private String value;

    public SmsServerPropertyEntity(SmsServerProperty propertyKey, String value) {
        this.propertyKey = propertyKey;
        this.value = value;
    }

    public SmsServerPropertyEntity() {
    }

    public SmsServerProperty getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(SmsServerProperty propertyKey) {
        this.propertyKey = propertyKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
