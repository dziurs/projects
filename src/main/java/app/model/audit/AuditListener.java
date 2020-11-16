package app.model.audit;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

public class AuditListener {

    @Inject
    FacesContext facesContext;

    @PrePersist
    public void beforePersist (Object audit){
        if (audit instanceof Audit) {
            Audit a = (Audit) audit;
            a.setCreationDate(new Date());
            String remoteUser = facesContext.getExternalContext().getRemoteUser();
            if ( null != remoteUser)a.setCreationUserLogin(remoteUser);
            else a.setCreationUserLogin("GUEST");
            beforeUpdate(audit);
        }

    }
    @PreUpdate
    public void beforeUpdate(Object audit){
        if (audit instanceof Audit) {
            Audit a = (Audit) audit;
            a.setModificationDate(new Date());
            String remoteUser = facesContext.getExternalContext().getRemoteUser();
            if ( null != remoteUser)a.setModificationUserLogin(remoteUser);
            else a.setModificationUserLogin("GUEST");
        }
    }

}
