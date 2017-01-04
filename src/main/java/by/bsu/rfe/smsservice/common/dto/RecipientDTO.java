package by.bsu.rfe.smsservice.common.dto;

import by.bsu.rfe.smsservice.common.enums.RecipientType;

/**
 * Created by pluhin on 7/11/16.
 */
public class RecipientDTO {
    private Integer id;
    private String name;
    private RecipientType recipientType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecipientType getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(RecipientType recipientType) {
        this.recipientType = recipientType;
    }
}
