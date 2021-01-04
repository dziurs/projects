package app.dto;

public class AccountDTO {

    private String login;
    private boolean activate;
    private int pid;
    private String role;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "login='" + login + '\'' +
                ", activate=" + activate +
                ", pid=" + pid +
                ", role='" + role + '\'' +
                '}';
    }
}
