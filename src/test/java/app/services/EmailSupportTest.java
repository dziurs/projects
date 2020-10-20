package app.services;

import org.junit.jupiter.api.Test;

import javax.faces.context.FacesContext;

import static org.junit.jupiter.api.Assertions.*;

class EmailSupportTest {

    @Test
    public void getCall() {
        EmailSupport emailSupport = new EmailSupport(-3616805516797638514L);
        String decrypt = emailSupport.getCall();
        assertArrayEquals(decrypt.getBytes(),"qwerty12#".getBytes());
        emailSupport.setNum(-4621072911351240530L);
        decrypt= emailSupport.getCall();
        assertArrayEquals(decrypt.getBytes(),"QWERTY!@#".getBytes());
        EmailSupport es = new EmailSupport();
        es.setNum(-8237701856283935842L);
        String call = es.getCall();
        assertArrayEquals(call.getBytes(), "aDam12Rr#".getBytes());
        String applicationContextPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("activateUser.xhtml");
        System.out.println(applicationContextPath);
    }

}