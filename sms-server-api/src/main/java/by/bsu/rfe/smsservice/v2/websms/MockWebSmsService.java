package by.bsu.rfe.smsservice.v2.websms;

import static by.bsu.rfe.smsservice.common.constants.ProfileConstants.PROFILE_LOCAL;

import by.bsu.rfe.smsservice.v2.domain.websms.Request;
import by.bsu.rfe.smsservice.v2.domain.websms.Response;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(PROFILE_LOCAL)
public class MockWebSmsService implements WebSmsService {

  @Override
  public Response execute(Request request) {
    return null;
  }
}
