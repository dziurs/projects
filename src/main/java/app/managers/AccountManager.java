package app.managers;

import app.dao.AccountDAO;
import app.dao.DeveloperDAO;
import app.dao.UserDAO;
import app.exception.AccountException;
import app.exception.BuildingSalesAppException;
import app.exception.EmailSendingException;
import app.model.entity.Developer;
import app.model.entity.User;
import app.model.enums.UserType;
import app.security.Account;
import app.security.Crypter;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Stateless
@Local
@Transactional(value = Transactional.TxType.MANDATORY)
public class AccountManager {

    @Inject
    private DeveloperDAO developerDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private AccountDAO accountDAO;

    public Account findAccountByLogin(String login) throws BuildingSalesAppException{
        List<Account> list = accountDAO.findByEmail(login);
        if(list.size()==0)throw new AccountException(AccountException.LOGIN);
        return list.get(0);
    }

    public void registerDeveloper(Developer developer , String password) throws BuildingSalesAppException {
        Account account = new Account();
        account.setLogin(developer.getEmail().getEmail());
        account.setActivate(false);
        account.setPid(new Random().nextInt(100000));
        account.setPassword(Crypter.crypt(password));
        account.setRole(UserType.DEVELOPER);
        developerDAO.create(developer);
        accountDAO.create(account);
    }
    public int registerUser(User user, String password) throws BuildingSalesAppException {
        Account account = new Account();
        account.setLogin(user.getEmail().getEmail());
        account.setActivate(false);
        int pid = new Random().nextInt(100000);
        account.setPid(pid);
        account.setPassword(Crypter.crypt(password));
        account.setRole(UserType.USER);
        userDAO.create(user);
        accountDAO.create(account);
        return pid;
    }
    public void activateUserAccount(String login, String pid) throws EmailSendingException {
        List<Account> list = accountDAO.findByEmail(login);
        if(list.size()==1){
            Account account = list.get(0);
            int pidFromDatabase = account.getPid();
            if(pid.equals(String.valueOf(pidFromDatabase))){
                account.setActivate(true);
            }
            else {
                throw new EmailSendingException(EmailSendingException.EMAIL_OR_PID_ERROR);
            }
        }else {
            throw new EmailSendingException(EmailSendingException.EMAIL_OR_PID_ERROR);
        }
    }

    public void saveNewPassword(Account account) throws BuildingSalesAppException {
        accountDAO.update(account);
    }
}
