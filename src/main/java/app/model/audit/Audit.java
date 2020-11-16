package app.model.audit;

import java.util.Date;

public interface Audit {
    Date getCreationDate();

    void setCreationDate(Date date);

    Date getModificationDate();

    void setModificationDate(Date date);

    String getCreationUserLogin();

    void setCreationUserLogin(String user);

    String getModificationUserLogin();

    void setModificationUserLogin(String user);

}
