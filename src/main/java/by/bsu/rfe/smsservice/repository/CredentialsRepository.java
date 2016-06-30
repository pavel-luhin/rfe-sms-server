package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by pluhin on 3/20/16.
 */
public interface CredentialsRepository extends JpaRepository<CredentialsEntity, Integer> {
}
