package by.bsu.rfe.smsservice.v2.domain.websms;

public class DefaultResponse implements Response {

  private final String responseBody;

  public DefaultResponse(String responseBody) {
    this.responseBody = responseBody;
  }

  public String getResponseBody() {
    return responseBody;
  }
}
