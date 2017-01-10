package by.bsu.rfe.smsservice.scheduler.smsqueue;

import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.MUTE_ENABLED;
import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.MUTE_END_TIME;
import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.MUTE_START_TIME;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import by.bsu.rfe.smsservice.common.SpringContextHolder;
import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.entity.SmsQueueEntity;
import by.bsu.rfe.smsservice.service.SmsQueueService;
import by.bsu.rfe.smsservice.service.SmsServerPropertyService;
import by.bsu.rfe.smsservice.service.WebSMSService;

/**
 * Created by pluhin on 1/6/17.
 */
public class SmsQueueSenderJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsQueueSenderJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SmsQueueService smsQueueService = SpringContextHolder.getBean(SmsQueueService.class);
        WebSMSService webSMSService = SpringContextHolder.getBean(WebSMSService.class);
        LOGGER.info("Executing sms queue sender job");

        if (!isMuted()) {
            List<SmsQueueEntity> smsFromQueue = smsQueueService.getAllSmsFromQueue();

            SMSResultDTO smsResultDTO = webSMSService.sendSmsFromQueue(smsFromQueue);

            if (smsResultDTO.getCount() == 0) {
                LOGGER.info("There is no sms's in queue");
                return;
            }

            if (smsResultDTO.getErrorCount() == 0) {
                LOGGER.info("All sms's successfully sent");
                LOGGER.info("Total count: {}");
            } else {
                LOGGER.error("There is errors while sending sms's from queue");
                LOGGER.error("Last error is: ");
                LOGGER.error("{}", smsResultDTO.getLastError());
                LOGGER.info("Total count: {}, error count: {}", smsResultDTO.getCount(), smsResultDTO.getErrorCount());
            }

            List<Integer> smsQueueIds = smsFromQueue.stream().map(SmsQueueEntity::getId).collect(Collectors.toList());

            for (Integer smsQueueId : smsQueueIds) {
                smsQueueService.removeFromQueue(smsQueueId);
            }
        } else {
            LOGGER.info("Mute mode is enabled.");
        }
    }

    private Boolean isMuted() {
        SmsServerPropertyService smsServerPropertyService = SpringContextHolder.getBean(SmsServerPropertyService.class);
        Boolean isMuteEnabled = Boolean.valueOf(smsServerPropertyService.findPropertyValue(MUTE_ENABLED));

        if (isMuteEnabled) {
            LocalTime muteStartTime = LocalTime.parse(smsServerPropertyService.findPropertyValue(MUTE_START_TIME));
            LocalTime muteEndTime = LocalTime.parse(smsServerPropertyService.findPropertyValue(MUTE_END_TIME));

            LocalTime localTime = LocalTime.now();

            if (localTime.isAfter(muteStartTime) && localTime.isBefore(muteEndTime)) {
                return true;
            }
        }

        return false;
    }
}
