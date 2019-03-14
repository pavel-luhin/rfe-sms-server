package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.dto.sms.ReceiveSmsDTO;
import java.util.List;

public interface ReceiveSmsService {

  void process(List<ReceiveSmsDTO> receivedSms);
}
