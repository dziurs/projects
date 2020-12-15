package app.security;

import app.dao.AccountDAO;
import app.exception.BuildingSalesAppException;
import app.model.enums.UserType;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Startup
@Singleton
@Transactional(value = Transactional.TxType.REQUIRED)
public class AdminAccount {

    @Inject
    private AccountDAO accountDAO;

    @PostConstruct
    public void init(){
        Account account = new Account();
        account.setLogin("admin");
        account.setPassword("240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9");
        account.setActivate(true);
        account.setPid(1);
        account.setRole(UserType.ADMIN);
        try {
            accountDAO.create(account);
        }catch (BuildingSalesAppException ex){
            try{
                accountDAO.create(account);
            }catch (BuildingSalesAppException e){
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Cant create Administrator account");
            logger.log(Level.SEVERE, ex.getMessage());}
        }

    }
}
