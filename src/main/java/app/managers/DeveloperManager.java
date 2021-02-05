package app.managers;

import app.dao.DeveloperDAO;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralApplicationException;
import app.interceptor.Log;
import app.model.entity.Developer;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
@LocalBean
@RolesAllowed("Developer")
@Transactional(value = Transactional.TxType.MANDATORY)
public class DeveloperManager {

    @Inject
    private DeveloperDAO developerDAO;

    @Log
    public Developer findDeveloperByEmail(String email) throws BuildingSalesAppException {
        List<Developer> list = developerDAO.findByEmail(email);
        if(list.size()==0) throw new GeneralApplicationException(GeneralApplicationException.KEY_OPTIMISTIC_LOCK);
        Developer developer = list.get(0);
        return developer;
    }

    @Log
    public void saveEditedDeveloper(Developer developer) throws BuildingSalesAppException {
        developerDAO.update(developer);
    }
}
