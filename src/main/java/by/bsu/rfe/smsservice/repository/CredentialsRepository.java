package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by pluhin on 3/20/16.
 */
public interface CredentialsRepository extends JpaRepository<CredentialsEntity, Integer> {
    @Query("FROM CredentialsEntity ce JOIN ce.smsType st WHERE st.smsType=?1")
    CredentialsEntity getCredentials(String smsType);
}
