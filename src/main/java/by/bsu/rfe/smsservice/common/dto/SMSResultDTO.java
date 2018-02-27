package by.bsu.rfe.smsservice.common.dto;

import lombok.Data;

/**
 * Created by pluhin on 6/1/16.
 */
@Data
public class SMSResultDTO {

    private int count;
    private int errorCount;
    private String lastError;

    public static SMSResultDTO success(int count) {
        SMSResultDTO smsResultDTO = new SMSResultDTO();
        smsResultDTO.setCount(count);
        return smsResultDTO;
    }
}
