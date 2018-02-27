package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.common.WebSmsResponse;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.service.WebSmsService;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WebSmsServiceImpl implements WebSmsService {

  private static final HttpClient HTTP_CLIENT = HttpClientBuilder.create().build();

  @Override
  public WebSmsResponse execute(Request request) {
    String apiEndpoint = request.apiEndpoint();
    log.debug("Preparing websms request to {}", apiEndpoint);

    RequestBuilder requestBuilder = RequestBuilder.create("POST");
    requestBuilder.setUri(apiEndpoint);
    requestBuilder.setEntity(new UrlEncodedFormEntity(request.getParameters(), Consts.UTF_8));

    HttpUriRequest httpRequest = requestBuilder.build();
    HttpResponse response = null;
    try {
      log.debug("Executing web sms request...");
      response = HTTP_CLIENT.execute(httpRequest);
    } catch (IOException e) {
      log.error("Error while trying to execute request");
      log.error(e.getMessage());
      throw new RuntimeException(e);
    }

    log.debug("Web sms request executed successfully");
    return mapToResponse(response);

  }

  private WebSmsResponse mapToResponse(HttpResponse response) {
    return new WebSmsResponse();
  }
}
