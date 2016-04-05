package by.bsu.rfe.smsservice.common.request;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pluhin on 12/27/15.
 */
public class Request {

    private String apiEndpoint;
    private List<NameValuePair> parameters;

    public Request() {
        ;parameters = new ArrayList<NameValuePair>();
    }

    public String apiEndpoint() {
        return apiEndpoint;
    }

    public void addParameter(NameValuePair parameter) {
        parameters.add(parameter);
    }

    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public List<NameValuePair> getParameters() {
        return parameters;
    }

    public void setParameters(List<NameValuePair> parameters) {
        this.parameters = parameters;
    }
}
