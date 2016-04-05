package by.bsu.rfe.smsservice.builder;

import by.bsu.rfe.smsservice.common.request.Request;

/**
 * Created by pluhin on 12/27/15.
 */
public interface RequestBuilder<T> {
    Request buildRequest(T t);
}
