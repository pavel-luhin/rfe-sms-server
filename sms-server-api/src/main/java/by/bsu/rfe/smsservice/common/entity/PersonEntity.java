package by.bsu.rfe.smsservice.common.entity;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "person")
public class PersonEntity extends CreationDetails {

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Pattern(regexp = "^\\+?(\\d){12}$")
  @Column(name = "phone_number", nullable = false, unique = true)
  private String phoneNumber;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @ManyToMany(
      mappedBy = "persons",
      fetch = FetchType.EAGER,
      cascade = {DETACH, MERGE, PERSIST, REFRESH})
  private List<GroupEntity> groups;

  @Column(name = "temporary")
  private boolean temporary;
}
