package by.bsu.rfe.smsservice.common.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import by.bsu.rfe.smsservice.security.util.SecurityUtil;

/**
 * Created by pluhin on 9/3/16.
 */
@MappedSuperclass
public abstract class CreationDetails extends AbstractPersistable<Integer> {
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @PrePersist
    public void fillDetails() {
        createdBy = SecurityUtil.getCurrentUsername();
        createdDate = new Date();
    }
}
