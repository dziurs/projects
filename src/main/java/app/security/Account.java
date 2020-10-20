package app.security;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NamedQueries({@NamedQuery(name = "Account.findById", query = "SELECT a FROM Account a WHERE a.id = :id"),
        @NamedQuery(name = "Account.findByEmail", query = "SELECT a FROM Account a WHERE a.login = :login")})

@Table(name = "buildingsales_accounts", schema = "buildingsales")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ver", nullable = false)
    @Version
    private long version;

    @Column(nullable = false, length = 100, unique = true)
    private String login;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false, length = 5)
    private boolean activate = false;

    @Column(nullable = false, length = 10)
    private int pid;

    @Column (nullable = false, updatable = false, length = 20)
    private String role;

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return getLogin().equals(account.getLogin()) &&
                getPassword().equals(account.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getPassword());
    }
}