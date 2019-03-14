package by.bsu.rfe.smsservice.controller;

import static org.springframework.http.ResponseEntity.noContent;

import by.bsu.rfe.smsservice.common.dto.sms.ReceiveSmsDTO;
import by.bsu.rfe.smsservice.service.ReceiveSmsService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/sms")
public class ReceiveSmsController {

  private final ReceiveSmsService receiveSmsService;

  public ReceiveSmsController(ReceiveSmsService receiveSmsService) {
    this.receiveSmsService = receiveSmsService;
  }

  @PostMapping("/receive")
  public ResponseEntity receiveSms(@RequestBody List<ReceiveSmsDTO> receivedSms) {
    receiveSmsService.process(receivedSms);
    return noContent().build();
  }
}
