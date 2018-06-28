package by.bsu.rfe.smsservice.cache.credentials;

import static by.bsu.rfe.smsservice.security.util.SecurityUtil.getCurrentUsername;

import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.ExternalApplicationEntity;
import by.bsu.rfe.smsservice.exception.CacheAlreadyLockedException;
import by.bsu.rfe.smsservice.exception.CredentialsNotFoundException;
import by.bsu.rfe.smsservice.repository.CredentialsRepository;
import by.bsu.rfe.smsservice.repository.ExternalApplicationRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CredentialsCacheImpl implements CredentialsCache {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(CredentialsCacheImpl.class);
  @Autowired
  private CredentialsRepository credentialsRepository;

  @Autowired
  private ExternalApplicationRepository applicationRepository;

  private volatile Boolean locked = false;

  private Map<String, List<CredentialsEntity>> cache;

  @PostConstruct
  public void startCache() {
    log.info("INITIALIZING CREDENTIALS CACHE");

    if (locked) {
      throw new CacheAlreadyLockedException();
    }

    lockCache();

    long startTime = System.currentTimeMillis();

    cache = new HashMap<>();

    List<CredentialsEntity> userCredentials = credentialsRepository.findAll();
    log.info("LOADED {} credentials ", userCredentials.size());

    userCredentials
        .forEach(credentialsEntity -> {
          credentialsEntity.getUsers()
              .forEach(user -> {
                if (!cache.containsKey(user.getUsername())) {
                  cache.put(user.getUsername(), new ArrayList<>());
                }

                cache.get(user.getUsername()).add(credentialsEntity);
              });
        });

    List<ExternalApplicationEntity> applications = applicationRepository.findAll();
    log.info("LOADED {} applications", applications.size());

    applications
        .forEach(application -> {
          cache.put(application.getApplicationName(),
              Collections.singletonList(application.getDefaultCredentials()));
        });

    log.info("CREDENTIALS CACHE INITIALIZED IN {} ms", System.currentTimeMillis() - startTime);

    unlockCache();
  }

  @Override
  public List<CredentialsEntity> getAllUserCredentals(String username) {
    checkForLock();
    return cache.get(username);
  }

  @Override
  public Set<String> getSenderNamesForCurrentUser() {
    checkForLock();
    return getAllUserCredentals(getCurrentUsername())
        .stream()
        .map(CredentialsEntity::getSender)
        .collect(Collectors.toSet());
  }

  @Override
  public CredentialsEntity getCredentialsBySenderNameForCurrentUser(String senderName) {
    checkForLock();
    return getAllUserCredentals(getCurrentUsername())
        .stream()
        .filter(credentialsEntity -> credentialsEntity.getSender().equals(senderName))
        .findFirst()
        .orElseThrow(() -> new CredentialsNotFoundException(
            "Credentials with " + senderName + " not found"));
  }

  @Async
  @Override
  public void reloadCache() {
    startCache();
  }

  private void lockCache() {
    locked = true;
    log.info("Successfully acquired cache lock");
  }

  private void unlockCache() {
    locked = false;
    log.info("Cache lock released");
    synchronized (locked) {
      locked.notify();
    }
  }

  @SneakyThrows
  private void checkForLock() {
    synchronized (locked) {
      if (locked) {
        locked.wait();
      }
    }
  }
}
