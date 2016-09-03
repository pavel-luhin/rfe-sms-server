package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.ExternalApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by pluhin on 9/3/16.
 */
public interface ExternalApplicationRepository extends JpaRepository<ExternalApplicationEntity, Integer> {
}
