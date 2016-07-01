package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by pluhin on 3/20/16.
 */
public interface CredentialsRepository extends JpaRepository<CredentialsEntity, Integer> {

    @Query("FROM CredentialsEntity ce JOIN ce.users ue WHERE ue.username=?1 AND ce.smsType=?2")
    CredentialsEntity getCredentials(String username, String smsType);

    @Query("FROM CredentialsEntity ce JOIN ce.users ue WHERE ue.username=?1")
    CredentialsEntity getDefaultCredentials(String username);
}
