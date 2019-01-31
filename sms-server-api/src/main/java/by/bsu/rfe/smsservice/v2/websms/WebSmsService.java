package by.bsu.rfe.smsservice.v2.websms;

import by.bsu.rfe.smsservice.v2.domain.websms.Request;
import by.bsu.rfe.smsservice.v2.domain.websms.Response;

public interface WebSmsService {

  Response execute(Request request);
}
