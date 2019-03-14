package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.common.dto.sms.ReceiveSmsDTO;
import by.bsu.rfe.smsservice.service.ReceiveSmsService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DefaultReceiveSmsService implements ReceiveSmsService {

  @Override
  public void process(List<ReceiveSmsDTO> receivedSms) {
    //TODO process received sms
  }
}
