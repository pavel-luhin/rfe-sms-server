package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.ExternalApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by pluhin on 9/3/16.
 */
public interface ExternalApplicationRepository extends JpaRepository<ExternalApplicationEntity, Integer> {

    @Query("FROM ExternalApplicationEntity WHERE token=?1")
    ExternalApplicationEntity getByToken(String token);
}
