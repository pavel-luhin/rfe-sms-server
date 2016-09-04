package by.bsu.rfe.smsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;

/**
 * Created by pluhin on 3/20/16.
 */
public interface SmsTemplateRepository extends JpaRepository<SmsTemplateEntity, Integer> {
    @Query("FROM SmsTemplateEntity WHERE uriPath=?1 AND enabled=true")
    SmsTemplateEntity getByURIPath(String uriPath);

    @Query("FROM SmsTemplateEntity WHERE smsType LIKE %?1%")
    SmsTemplateEntity findSMSTemplate(String query);
}
