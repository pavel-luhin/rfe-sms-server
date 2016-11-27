package by.bsu.rfe.smsservice.controller;

import by.bsu.rfe.smsservice.common.dto.*;
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

import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.EmailService;
import by.bsu.rfe.smsservice.service.ExternalApplicationService;
import by.bsu.rfe.smsservice.service.SmsTemplateService;
import by.bsu.rfe.smsservice.service.UserService;

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
    @Autowired
    private EmailService emailService;

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
    public List<UserDTO> getAllUsers(@RequestParam(required = false) Integer credentialsId) {
        return userService.getAllUsers(credentialsId);
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

    @ResponseBody
    @RequestMapping(value = "/emailTemplate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmailTemplateDTO> getAllEmailTemplates() {
        return emailService.getAllEmailTemplates();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/emailTemplate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createEmailTemplate(@RequestBody EmailTemplateDTO emailTemplateDTO) {
        emailService.saveEmailTemplate(emailTemplateDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/emailTemplate/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteEmailTemplate(@PathVariable Integer id) {
        emailService.removeEmailTemplate(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/shareCredentials", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void shareCredentialsBetweenUser(@RequestBody ShareCredentialsDTO shareCredentialsDTO) {
        credentialsService.shareCredentials(shareCredentialsDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void changePassword(@RequestBody ChangePasswordDTO passwordDTO) {
        userService.changePassword(passwordDTO);
    }

    @ResponseBody
    @RequestMapping(value = "/version", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public VersionDTO getApplicationVersion() {
        return VersionDTO.loadFromProperties("version.properties");
    }
}
