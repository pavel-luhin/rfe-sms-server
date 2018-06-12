package by.bsu.rfe.smsservice.common.entity;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "recipient_group")
public class GroupEntity extends CreationDetails {

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @ManyToMany(fetch = FetchType.EAGER, cascade = {DETACH, MERGE, PERSIST, REFRESH})
  @JoinTable(
      name = "persons_have_groups",
      inverseJoinColumns = @JoinColumn(name = "person_id"),
      joinColumns = @JoinColumn(name = "group_id")
  )
  private List<PersonEntity> persons = new ArrayList<>();

  @Column(name = "temporary")
  private boolean temporary;

}
