package by.bsu.rfe.smsservice.v2.websms;

import static by.bsu.rfe.smsservice.common.constants.ProfileConstants.PROFILE_PROD;
import static org.apache.http.client.methods.RequestBuilder.post;

import by.bsu.rfe.smsservice.v2.domain.websms.DefaultResponse;
import by.bsu.rfe.smsservice.v2.domain.websms.Request;
import by.bsu.rfe.smsservice.v2.domain.websms.Response;
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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile(PROFILE_PROD)
public class DefaultWebSmsService implements WebSmsService {

  private static final HttpClient HTTP_CLIENT = HttpClientBuilder.create().build();

  @Override
  public Response execute(Request request) {
    log.info("Executing {}", request);
    String apiEndpoint = request.getApiEndpoint();
    log.info("Preparing websms request to {}", apiEndpoint);

    RequestBuilder requestBuilder =
        post(apiEndpoint)
            .setEntity(new UrlEncodedFormEntity(request.getParameters(), Consts.UTF_8));

    HttpUriRequest httpRequest = requestBuilder.build();
    HttpResponse httpResponse = null;
    try {
      log.debug("Executing web sms request...");
      httpResponse = HTTP_CLIENT.execute(httpRequest);
    } catch (IOException e) {
      log.error("Error while trying to execute websms request");
      log.error("", e);
      throw new RuntimeException(e);
    }

    log.info("Web sms request executed successfully with result {}", httpResponse);
    return new DefaultResponse(httpResponse.getStatusLine().getStatusCode(), getContent(httpResponse));
  }

  private String getContent(HttpResponse httpResponse) {
    String unicodeLine = "";
    StringBuilder unicodeContent = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))) {
      while (true) {
        if ((unicodeLine = reader.readLine()) == null) {
          break;
        }
      }
      unicodeContent.append(unicodeLine);
    } catch (IOException e) {
      log.error("Error while parsing response {}", e);
    }

    return StringEscapeUtils.unescapeJava(unicodeContent.toString());
  }
}
