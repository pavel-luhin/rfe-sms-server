package by.bsu.rfe.smsservice.controller;

import by.bsu.rfe.smsservice.common.dto.GroupDTO;
import by.bsu.rfe.smsservice.common.dto.PageResponseDTO;
import by.bsu.rfe.smsservice.common.dto.PersonDTO;
import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    @RequestMapping(value = "/group", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createGroup(@RequestBody GroupDTO groupDTO) {
        recipientService.addGroup(groupDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/group", method = RequestMethod.DELETE)
    public void removeGroup(@RequestParam Integer groupId) {
        recipientService.removeGroup(groupId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/persons", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addPersons(@RequestBody List<PersonEntity> persons) {
        recipientService.addPersons(persons);
    }

    @ResponseBody
    @RequestMapping(value = "/persons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseDTO<PersonDTO> getPersons(@RequestParam int skip, @RequestParam int offset,
                                      @RequestParam String sortField, @RequestParam String sortDirection) {
        return recipientService.getPersons(skip, offset, sortField, sortDirection);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/persons", method = RequestMethod.DELETE)
    public void removePerson(@RequestParam Integer personId) {
        recipientService.removePerson(personId);
    }

    @ResponseBody
    @RequestMapping(value = "/group", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseDTO<GroupDTO> getGroups(@RequestParam int skip, @RequestParam int offset,
            @RequestParam String sortField, @RequestParam String sortDirection) {
        return recipientService.getGroups(skip, offset, sortField, sortDirection);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/add/{recipientId}/toGroup/{groupId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addRecipientToGroup(@PathVariable Integer recipientId, @PathVariable Integer groupId) {
        recipientService.assignPersonToGroup(recipientId, groupId);
    }

    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RecipientDTO> getAllRecipients(@RequestParam(required = false) String query) {
        return recipientService.getRecipientByQuery(query);
    }
}
