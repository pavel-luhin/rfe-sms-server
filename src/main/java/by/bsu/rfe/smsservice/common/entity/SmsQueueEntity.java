package by.bsu.rfe.smsservice.common.entity;

import javax.persistence.*;

import by.bsu.rfe.smsservice.common.enums.RecipientType;
import lombok.Data;

/**
 * Created by pluhin on 1/4/17.
 */
@Data
@Entity
@Table(name = "sms_queue")
public class SmsQueueEntity extends CreationDetails {

  @Column(name = "numbers")
  private String numbers;

  @Column(name = "message")
  private String message;

  @OneToOne
  @JoinColumn(name = "credentials_id")
  private CredentialsEntity credentials;
}
