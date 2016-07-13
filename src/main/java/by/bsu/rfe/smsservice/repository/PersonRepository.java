package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by pluhin on 3/20/16.
 */
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {
    @Query("FROM PersonEntity WHERE firstName=?1")
    PersonEntity getPersonByFirstName(String firstName);


}
