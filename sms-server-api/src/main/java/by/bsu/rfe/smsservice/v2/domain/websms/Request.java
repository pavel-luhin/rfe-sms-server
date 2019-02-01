package by.bsu.rfe.smsservice.v2.domain.websms;

import java.util.List;
import org.apache.http.NameValuePair;

public interface Request {

  String getApiEndpoint();

  List<NameValuePair> getParameters();

  void addParameter(String key, String value);
}
