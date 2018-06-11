package by.bsu.rfe.smsservice.common.constants;

public final class ValidationConstants {

  public static final String PHONE_NUMBER_REGEX = "^\\+?(\\d){12}$";
  public static final int MIN_LENGTH = 1;
  public static final int PHONE_NUMBER_MAX_LENGTH = 13;

  public static final String NAME_REGEX = "^[a-zA-Z'.-]*$";
  public static final int NAME_MAX_LENGTH = 20;

  public static final String EMAIL_REGEX =
      "^[-A-Za-z0-9~!$%^&*_=+}{\'?]+(\\.[-A-Za-z0-9~!$%^&*_=+}"
          + "{\'?]+)*@([A-Za-z0-9_][-A-Za-z0-9_]*(\\.[-A-Za-z0-9_]+)*\\."
          + "(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])"
          + "|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$";
  public static final int EMAIL_MAX_LENGTH = 60;

  public static final String GROUP_NAME_REGEX = "^[a-zA-Z'.-]*$";
  public static final int GROUP_NAME_MAX_LENGTH = 60;

  private ValidationConstants() {
  }
}
