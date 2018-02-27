package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.WebSmsResponse;
import by.bsu.rfe.smsservice.common.request.Request;

public interface WebSmsService {

  WebSmsResponse execute(Request request);
}
