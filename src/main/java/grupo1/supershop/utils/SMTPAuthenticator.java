package grupo1.supershop.utils;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator{
    
    private final String smtpUsername;
    private final String smtpPassword;

    public SMTPAuthenticator(String smtpUsername, String smtpPassword) {
        this.smtpUsername = smtpUsername;
        this.smtpPassword = smtpPassword;
    }
    
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(smtpUsername, smtpPassword);
     }
}
