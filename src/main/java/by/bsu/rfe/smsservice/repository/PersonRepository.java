package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by pluhin on 3/20/16.
 */
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {
    @Query("FROM PersonEntity WHERE firstName=?1 AND lastName=?2")
    PersonEntity getPersonByFirstNameAndLastName(String firstName, String lastName);

    @Query("FROM PersonEntity WHERE firstName LIKE %?1% OR lastName LIKE %?1%")
    List<PersonEntity> getPersonsByQuery(String query);
}
