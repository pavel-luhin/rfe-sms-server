package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by pluhin on 3/20/16.
 */
public interface SmsTemplateRepository extends JpaRepository<SmsTemplateEntity, Integer> {
    @Query("FROM SmsTemplateEntity WHERE uriPath=?1")
    SmsTemplateEntity getByURIPath(String uriPath);
}
