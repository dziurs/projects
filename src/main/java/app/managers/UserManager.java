package app.managers;

import app.dao.UserDAO;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralApplicationException;
import app.model.entity.User;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
@Local
@Transactional(value = Transactional.TxType.MANDATORY)
public class UserManager {

    @Inject
    private UserDAO userDAO;


    public User findUserByEmail(String email) throws BuildingSalesAppException {
        List<User> list = userDAO.findByEmail(email);
        if(list.size()==0) throw new GeneralApplicationException(GeneralApplicationException.KEY_OPTIMISTIC_LOCK);
        User user = list.get(0);
        return user;
    }
    public void saveEditedUser(User user) throws BuildingSalesAppException {
        userDAO.update(user);
    }
}
