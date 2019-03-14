package by.bsu.rfe.smsservice.v2.domain.websms;

import org.apache.http.HttpStatus;

public class DefaultResponse implements Response {

  private final int status;
  private final String responseBody;

  public DefaultResponse(int status, String responseBody) {
    this.status = status;
    this.responseBody = responseBody;
  }

  public String getResponseBody() {
    return responseBody;
  }

  public int getStatus() {
    return status;
  }
}
