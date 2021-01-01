package app.endpoints;

import app.exception.EmailSendingException;
import app.services.EmailService;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@ApplicationScoped
public class EmailSendingEndpoint {

    @Inject
    private EmailService emailService;

    private String appContext ;

    private String domain;

    private String email;

    private String param;

    private String serveSMTP;

    private String port;

    public EmailSendingEndpoint(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        domain = externalContext.getInitParameter("domain");
        email = externalContext.getInitParameter("appEmail");
        param = externalContext.getInitParameter("param");
        serveSMTP = externalContext.getInitParameter("serveSMTP");
        port = externalContext.getInitParameter("port");
        appContext = domain.concat(externalContext.getApplicationContextPath());
    }

    public void sendEmail(String toUser, String link) throws EmailSendingException {
        emailService.sendEmail(email,param,serveSMTP,port,link,toUser);
    }
    public void sendResetPasswordEmail(String toUser, String link) throws EmailSendingException {
        emailService.sendPasswordReset(email,param,serveSMTP,port,link,toUser);
    }

    public String getAppContext() {
        return appContext;
    }
}
