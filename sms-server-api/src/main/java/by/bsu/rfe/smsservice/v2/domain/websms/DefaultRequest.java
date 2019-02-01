package by.bsu.rfe.smsservice.v2.domain.websms;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class DefaultRequest implements Request {

  private final String apiEndpoint;
  private final List<NameValuePair> parameters = new ArrayList<>();

  public DefaultRequest(String apiEndpoint) {
    this.apiEndpoint = apiEndpoint;
  }

  @Override
  public String getApiEndpoint() {
    return apiEndpoint;
  }

  @Override
  public List<NameValuePair> getParameters() {
    return parameters;
  }

  @Override
  public void addParameter(String key, String value) {
    this.parameters.add(new BasicNameValuePair(key, value));
  }
}
