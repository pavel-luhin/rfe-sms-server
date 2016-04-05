package by.bsu.rfe.smsservice.controller;

import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Created by pluhin on 3/5/2016.
 */
@Controller
@RequestMapping(value = "/recipient")
public class RecipientController {

    @Autowired
    private RecipientService recipientService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/create/group/{groupName}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createGroup(@PathVariable String groupName) {
        recipientService.addGroup(groupName);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/add/recipients", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addRecipients(@RequestBody List<PersonEntity> persons) {
        recipientService.addPersons(persons);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/add/{recipientId}/toGroup/{groupId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addRecipientToGroup(@PathVariable Integer recipientId, @PathVariable Integer groupId) {
        recipientService.assignPersonToGroup(recipientId, groupId);
    }

    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonEntity> getAllRecipients() {
        return recipientService.getAllPersons();
    }
}
