package by.bsu.rfe.smsservice.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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

  @Column(name = "duplicate_email")
  private boolean duplicateEmail;
}
