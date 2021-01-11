package app.endpoints;

import app.converter.ConverterDTOToEntity;
import app.converter.ConverterEntityToDTO;
import app.dao.AccountDAO;
import app.dao.DeveloperDAO;
import app.dao.UserDAO;
import app.dto.AccountDTO;
import app.exception.AccountException;
import app.exception.BuildingSalesAppException;
import app.interceptor.Log;
import app.managers.AccountManager;
import app.model.entity.Developer;
import app.model.entity.User;
import app.security.Account;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Stateless
@LocalBean
@RolesAllowed("Administrator")
@Transactional(value = Transactional.TxType.REQUIRES_NEW)
public class AdministratorEndpoint implements Serializable {

    @Inject
    private AccountDAO accountDAO;

    @Inject
    private DeveloperDAO developerDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private AccountManager accountManager;

    @Log
    public List<AccountDTO> getUserAccounts(){
        List<Account> accounts = accountDAO.findByRole("USER");
        List<AccountDTO> accountDTOList = new ArrayList<>();
        for (Account a: accounts) {
            AccountDTO accountDTO = ConverterEntityToDTO.convertAccountToAccountDTO(a);
            accountDTOList.add(accountDTO);

        }
        return accountDTOList;
    }

    @Log
    public List<AccountDTO> getDeveloperAccounts(){
        List<Account> accounts = accountDAO.findByRole("DEVELOPER");
        List<AccountDTO> accountDTOList = new ArrayList<>();
        Iterator<Account> iterator = accounts.iterator();
        while (iterator.hasNext()){
            Account next = iterator.next();
            AccountDTO accountDTO = ConverterEntityToDTO.convertAccountToAccountDTO(next);
            accountDTOList.add(accountDTO);
        }
        return accountDTOList;
    }

    @Log
    public void activateAccount(AccountDTO accountDTO) throws BuildingSalesAppException {
        Account account = ConverterDTOToEntity.convertAccountDTOToAccount(accountDTO, accountDAO);
        account.setActivate(true);
        accountDAO.update(account);
    }

    @Log
    public void deactivateAccount(AccountDTO accountDTO) throws BuildingSalesAppException {
        Account account = ConverterDTOToEntity.convertAccountDTOToAccount(accountDTO, accountDAO);
        account.setActivate(false);
        accountDAO.update(account);
    }

    @Log
    public void deleteUserAccount(AccountDTO accountDTO) throws BuildingSalesAppException {
        Account account = ConverterDTOToEntity.convertAccountDTOToAccount(accountDTO, accountDAO);
        String email = deleteAccount(account);
        List<User> users = userDAO.findByEmail(email);
        if (users.size()==0) throw new AccountException(AccountException.KEY_OPTIMISTIC_LOCK);
        User user = users.get(0);
        userDAO.delete(user);
    }

    @Log
    public void deleteDeveloperAccount(AccountDTO accountDTO) throws BuildingSalesAppException {
        Account account = ConverterDTOToEntity.convertAccountDTOToAccount(accountDTO, accountDAO);
        String login = deleteAccount(account);
        List<Developer> developers = developerDAO.findByEmail(login);
        if(developers.size()==0)  throw new AccountException(AccountException.KEY_OPTIMISTIC_LOCK);
        Developer developer = developers.get(0);
        developerDAO.delete(developer);
    }

    @PermitAll
    @Log
    public String findAccountPassByLogin(String login) throws BuildingSalesAppException {
        Account account = accountManager.findAccountByLogin(login);
        return account.getPassword();
    }
    @PermitAll
    @Log
    public void setNewPasswordForAccount(String login, String newPassword) throws BuildingSalesAppException {
        Account account = accountManager.findAccountByLogin(login);
        accountManager.saveNewPassword(account, newPassword);
    }

    @Log
    private String deleteAccount(Account account) throws BuildingSalesAppException{
        String login = account.getLogin();
        accountDAO.delete(account);
        return login;
    }

}
