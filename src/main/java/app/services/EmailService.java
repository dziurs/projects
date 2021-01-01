package app.services;

import app.exception.EmailSendingException;
import javax.enterprise.context.ApplicationScoped;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.ResourceBundle;

@ApplicationScoped
public class EmailService {

    private ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages");
    final private String title = bundle.getString("email.message.title");


    public void sendEmail(String mainEmail, String param, String server, String port, String url, String clientEmail) throws EmailSendingException {
        String text = "<!doctype html> <html lang=\"pl\"><head> <meta charset=\"utf-8\"> " +
                "</head> <body> <p>"+bundle.getString("email.message")+"</p><br />" +
                "<p><a href=\""+url+"\">"+bundle.getString("email.message.click")+"</a></p></body>";
        prepareEmail(text,mainEmail,param,server,port,clientEmail);
    }
    public void sendPasswordReset(String mainEmail, String param, String server, String port, String url, String clientEmail) throws EmailSendingException {
        String text = "<!doctype html> <html lang=\"pl\"><head> <meta charset=\"utf-8\"> " +
                "</head> <body> <p>"+bundle.getString("reset.password.email.message")+"</p><br />" +
                "<p><a href=\""+url+"\">"+bundle.getString("email.message.click")+"</a></p></body>";
        prepareEmail(text,mainEmail,param,server,port,clientEmail);
    }
    private void prepareEmail(String text, String mainEmail, String param, String server, String port, String clientEmail) throws EmailSendingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", server);
        properties.put("mail.smtp.port", port);
        EmailSupport support = new EmailSupport(Long.parseLong(param));
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mainEmail, support.getCall());
            }
        });
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(mainEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(clientEmail));
            message.setSubject(title);
            message.setText(text);
            message.setContent(text, "text/html; charset=\"utf-8\"");
            Transport.send(message);

        } catch (MessagingException ex) {
            throw new EmailSendingException(EmailSendingException.EMAIL_ERROR);
        }

    }


}
