package by.bsu.rfe.smsservice.common.dto;

/**
 * Created by pluhin on 11/26/16.
 */
public class BaseExceptionDTO {
    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
