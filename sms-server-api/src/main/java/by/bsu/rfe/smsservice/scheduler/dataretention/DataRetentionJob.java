package by.bsu.rfe.smsservice.scheduler.dataretention;

import by.bsu.rfe.smsservice.common.SpringContextHolder;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.service.GroupService;
import by.bsu.rfe.smsservice.service.PersonService;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataRetentionJob implements Job {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataRetentionJob.class);

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    LOGGER.info("Starting data retention...");
    dropTemporaryRecipients();
    LOGGER.info("Finished data retention");
  }

  private void dropTemporaryRecipients() {
    PersonService personService = SpringContextHolder.getBean(PersonService.class);
    GroupService groupService = SpringContextHolder.getBean(GroupService.class);

    List<GroupEntity> groupsToRemove = groupService.findTemporaryGroups();
    LOGGER.info("Found {} groups to remove", groupsToRemove.size());

    groupsToRemove.stream().forEach(group -> {
      LOGGER.info("Removing {} group", group.getName());
      groupService.removeGroup(group.getId());
    });

    List<PersonEntity> personsToRemove = personService.findTemporaryPersons();
    LOGGER.info("Found {} persons to remove", personsToRemove.size());

    personsToRemove.stream().forEach(person -> {
      LOGGER.info("Removing {} person", person.getFirstName() + " " + person.getLastName());
      personService.removePerson(person.getId());
    });
  }
}
