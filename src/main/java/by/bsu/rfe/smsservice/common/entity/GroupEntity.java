package by.bsu.rfe.smsservice.common.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by pluhin on 3/20/16.
 */
@Entity
@Table(name = "recipient_group")
public class GroupEntity extends AbstractPersistable<Integer> {
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER)
    private List<PersonEntity> persons;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PersonEntity> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonEntity> persons) {
        this.persons = persons;
    }
}
