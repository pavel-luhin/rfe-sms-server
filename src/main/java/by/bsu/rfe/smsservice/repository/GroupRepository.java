package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by pluhin on 3/20/16.
 */
public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {
    @Query("FROM GroupEntity WHERE name LIKE %?1%")
    List<GroupEntity> getGroupsByQuery(String query);

    @Query("SELECT ge FROM GroupEntity ge WHERE ge.name LIKE %?1%")
    Page<GroupEntity> findPageByQuery(String query, Pageable pageable);
}
