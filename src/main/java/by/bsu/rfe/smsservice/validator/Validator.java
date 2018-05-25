package by.bsu.rfe.smsservice.validator;

/**
 * Validator interface used to validate data.
 */
public interface Validator<T> {

  /**
   * Validates data. Corrects string according to some rules.
   *
   * @param t input string
   * @return corrected string
   */
  T validate(T t);

  /**
   * Checks if string is valid based on some rules.
   *
   * @param t input string
   * @return true if valid, false otherwise
   */
  Boolean isValid(T t);

  /**
   * Error string to be displayed if data is incorrect.
   *
   * @return error string
   */
  String errorString();
}
