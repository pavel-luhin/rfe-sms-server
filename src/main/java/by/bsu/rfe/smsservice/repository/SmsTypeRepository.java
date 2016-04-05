package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.SmsTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by pluhin on 3/20/16.
 */
public interface SmsTypeRepository extends JpaRepository<SmsTypeEntity, Integer> {
    @Query("FROM SmsTypeEntity WHERE smsType=?1")
    SmsTypeEntity getSmsType(String name);
}
