package by.bsu.rfe.smsservice.v2.domain.websms;

public class DefaultRequest implements Request {

  private final String apiEndpoint;
  private final String body;

  public DefaultRequest(String apiEndpoint, String body) {
    this.apiEndpoint = apiEndpoint;
    this.body = body;
  }

  @Override
  public String getApiEndpoint() {
    return null;
  }

  @Override
  public String getBody() {
    return null;
  }
}
