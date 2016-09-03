package by.bsu.rfe.smsservice.controller;

import by.bsu.rfe.smsservice.common.dto.ExternalApplicationDTO;
import by.bsu.rfe.smsservice.common.dto.CredentialsDTO;
import by.bsu.rfe.smsservice.common.dto.UserDTO;
import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.ExternalApplicationService;
import by.bsu.rfe.smsservice.service.SmsTemplateService;
import by.bsu.rfe.smsservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by pluhin on 9/3/16.
 */
@Controller
@RequestMapping("/setup")
public class SetupController {

    @Autowired
    private UserService userService;
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private SmsTemplateService smsTemplateService;
    @Autowired
    private ExternalApplicationService externalApplicationService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/credentials", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addNewCredentials(@RequestBody CredentialsDTO credentialsDTO) {
        userService.addNewCredentials(credentialsDTO);
    }

    @ResponseBody
    @RequestMapping(value = "/credentials", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CredentialsDTO> getUserCredentials() {
        String username = SecurityUtil.getCurrentUsername();
        return credentialsService.getUserCredentials(username);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/credentials/{id}", method = RequestMethod.DELETE)
    public void removeCredentials(@PathVariable Integer id) {
        credentialsService.removeCredentials(id);
    }

    @ResponseBody
    @RequestMapping(value = "/smsTemplate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SmsTemplateEntity> getSmsTemplates() {
        return smsTemplateService.getSmsTemplates();
    }

    @ResponseBody
    @RequestMapping(value = "/smsTemplate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public SmsTemplateEntity addSmsTemplates(@RequestBody SmsTemplateEntity smsTemplateEntity) {
        return smsTemplateService.addSMSTemplate(smsTemplateEntity);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/smsTemplate/{id}", method = RequestMethod.DELETE)
    public void removeSMSTemplate(@PathVariable Integer id) {
        smsTemplateService.removeSMSTemplate(id);
    }

    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO.getUsername());
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(@PathVariable Integer id) {
        userService.removeUser(id);
    }

    @ResponseBody
    @RequestMapping(value = "/application", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExternalApplicationDTO> getAllApplications() {
        return externalApplicationService.getAllExternalApplications();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/application", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createApplication(@RequestBody ExternalApplicationDTO applicationDTO) {
        externalApplicationService.createExternalApplication(applicationDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/application/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteApplication(@PathVariable Integer id) {
        externalApplicationService.removeExternalApplication(id);
    }
}
