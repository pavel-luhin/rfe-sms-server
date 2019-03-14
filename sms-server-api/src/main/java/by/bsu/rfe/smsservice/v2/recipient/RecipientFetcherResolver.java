package by.bsu.rfe.smsservice.v2.recipient;

import by.bsu.rfe.smsservice.v2.domain.recipient.RecipientType;
import java.util.HashMap;
import java.util.Map;

public class RecipientFetcherResolver {

  private Map<RecipientType, RecipientFetcher> dictionary = new HashMap<>();

  public RecipientFetcherResolver(
      RecipientFetcher groupFetcher,
      RecipientFetcher personFetcher,
      RecipientFetcher numberFetcher) {
    dictionary.put(RecipientType.GROUP, groupFetcher);
    dictionary.put(RecipientType.NUMBER, numberFetcher);
    dictionary.put(RecipientType.PERSON, personFetcher);
  }

  public RecipientFetcher resolve(RecipientType type) {
    return dictionary.get(type);
  }
}
