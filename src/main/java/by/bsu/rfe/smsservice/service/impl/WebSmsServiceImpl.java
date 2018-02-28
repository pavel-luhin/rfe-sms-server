package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.response.BalanceResponse;
import by.bsu.rfe.smsservice.common.response.SendSmsResponse;
import by.bsu.rfe.smsservice.service.WebSmsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WebSmsServiceImpl implements WebSmsService {

  private static final HttpClient HTTP_CLIENT = HttpClientBuilder.create().build();

  private ObjectMapper objectMapper;

  @Autowired
  public WebSmsServiceImpl(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public SendSmsResponse sendSms(Request request) {
    HttpResponse response = execute(request);
    try {
      String content = getContent(response);
      return objectMapper.readValue(content, SendSmsResponse.class);
    } catch (IOException e) {
      log.error("Error while mapping send sms response to object");
      log.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  @Override
  public BalanceResponse getBalance(Request request) {
    HttpResponse response = execute(request);
    try {
      String content = getContent(response);
      return objectMapper.readValue(content, BalanceResponse.class);
    } catch (IOException e) {
      log.error("Error while mapping balance response to object");
      log.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private HttpResponse execute(Request request) {
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
      log.error("Error while trying to sendSms request");
      log.error(e.getMessage());
      throw new RuntimeException(e);
    }

    log.debug("Web sms request executed successfully");
    return response;
  }

  private String getContent(HttpResponse httpResponse) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
    String unicodeLine = "";
    StringBuilder unicodeContent = new StringBuilder();

    while ((unicodeLine = reader.readLine()) != null) {
      unicodeContent.append(unicodeLine);
    }

    return StringEscapeUtils.unescapeJava(unicodeContent.toString());
  }
}
