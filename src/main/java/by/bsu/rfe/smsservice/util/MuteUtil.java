package by.bsu.rfe.smsservice.util;

import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.MUTE_ENABLED;
import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.MUTE_END_TIME;
import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.MUTE_START_TIME;

import by.bsu.rfe.smsservice.common.SpringContextHolder;
import by.bsu.rfe.smsservice.service.SmsServerPropertyService;
import java.time.LocalTime;

/**
 * Util class provides helper methods to work with mute mode.
 */
public final class MuteUtil {

  private MuteUtil() {
  }

  /**
   * Checks if mute mode is active now based on sms server properties.
   *
   * @return true if mute mode is active, false otherwise.
   */
  public static Boolean isMuted() {
    SmsServerPropertyService smsServerPropertyService = SpringContextHolder
        .getBean(SmsServerPropertyService.class);
    Boolean isMuteEnabled = Boolean
        .valueOf(smsServerPropertyService.findPropertyValue(MUTE_ENABLED));

    if (isMuteEnabled) {
      LocalTime muteStartTime = LocalTime
          .parse(smsServerPropertyService.findPropertyValue(MUTE_START_TIME));
      LocalTime muteEndTime = LocalTime
          .parse(smsServerPropertyService.findPropertyValue(MUTE_END_TIME));

      LocalTime localTime = LocalTime.now();

      return localTime.isAfter(muteStartTime) && localTime.isBefore(muteEndTime);
    }

    return false;
  }
}
