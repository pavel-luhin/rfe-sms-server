package by.bsu.rfe.smsservice.builder;

import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.websms.WebSMSRest;

/**
 * Created by pluhin on 9/11/16.
 */
@Component
public class BalanceRequestBuilder {
    public Request buildRequest(String username, String apiKey) {
        Request request = new Request();
        request.setApiEndpoint(WebSMSRest.BALANCE.getApiEndpoint());

        request.addParameter(new BasicNameValuePair("user", username));
        request.addParameter(new BasicNameValuePair("apikey", apiKey));
        return request;
    }
}
