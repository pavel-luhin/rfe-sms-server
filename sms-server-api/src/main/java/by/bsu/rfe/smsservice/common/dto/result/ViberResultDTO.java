package by.bsu.rfe.smsservice.common.dto.result;

import lombok.Data;

@Data
public class ViberResultDTO implements ResultDTO {

  @Override
  public boolean isError() {
    return false;
  }

  @Override
  public int getCount() {
    return 0;
  }

  @Override
  public String getLastError() {
    return null;
  }

  @Override
  public boolean isInQueue() {
    return false;
  }
}
