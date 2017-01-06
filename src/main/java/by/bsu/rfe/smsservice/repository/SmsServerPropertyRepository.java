package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.enums.SmsServerProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import by.bsu.rfe.smsservice.common.entity.SmsServerPropertyEntity;

/**
 * Created by pluhin on 1/4/17.
 */
public interface SmsServerPropertyRepository extends JpaRepository<SmsServerPropertyEntity, SmsServerProperty> {
}
