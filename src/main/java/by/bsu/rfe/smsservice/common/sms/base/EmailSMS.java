package by.bsu.rfe.smsservice.common.sms.base;

/**
 * Class to duplicate sms to email
 */
public abstract class EmailSMS extends BaseSMS {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
