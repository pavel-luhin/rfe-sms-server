package by.bsu.rfe.smsservice.common.sms;

import by.bsu.rfe.smsservice.common.annotation.SMSProperty;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.common.sms.base.EmailSMS;

import java.util.Date;

/**
 * Created by pluhin on 12/27/15.
 */
public class InterviewSMS extends EmailSMS {
    @SMSProperty(templateField = "COMPANY_NAME")
    private String companyName;
    @SMSProperty(templateField = "STUDENT_FIRST_NAME")
    private String studentFirstName;
    @SMSProperty(templateField = "STUDENT_LAST_NAME")
    private String studentLastName;
    @SMSProperty(templateField = "COMPANY_ADDRESS")
    private String companyAddress;
    @SMSProperty(templateField = "INTERVIEW_DATE")
    private Date interviewDate;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(Date interviewDate) {
        this.interviewDate = interviewDate;
    }

    @Override
    public Boolean useTemplate() {
        return true;
    }

    @Override
    public RecipientType recipientType() {
        return RecipientType.PERSON;
    }
}
