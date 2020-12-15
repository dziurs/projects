package app.managers;

import app.dao.DeveloperDAO;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralApplicationException;
import app.model.entity.Developer;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
@Local
@Transactional(value = Transactional.TxType.MANDATORY)
public class DeveloperManager {

    @Inject
    private DeveloperDAO developerDAO;

    public Developer fingDeveloperByEmail(String email) throws BuildingSalesAppException {
        List<Developer> list = developerDAO.findByEmail(email);
        if(list.size()==0) throw new GeneralApplicationException(GeneralApplicationException.KEY_OPTIMISTIC_LOCK);
        Developer developer = list.get(0);
        return developer;
    }
    public void saveEditedDeveloper(Developer developer) throws BuildingSalesAppException {
        developerDAO.update(developer);
    }
}
