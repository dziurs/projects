package app.security;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import java.io.Serializable;


@Stateful
@LocalBean
public class SessionAccount implements Serializable {

    @Resource
    private SessionContext sessionContext;

    private String login;

    @PostConstruct
    public void init(){
        this.login = sessionContext.getCallerPrincipal().getName();
    }
    public String getLogin() {
        return login;
    }


}
