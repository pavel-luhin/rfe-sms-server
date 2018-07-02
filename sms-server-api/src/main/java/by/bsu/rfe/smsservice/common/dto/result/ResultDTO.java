package by.bsu.rfe.smsservice.common.dto.result;

public interface ResultDTO {

  boolean isError();

  int getCount();

  String getLastError();

  boolean isInQueue();
}
