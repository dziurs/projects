package app.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity(name="Email")
@Table(name = "buildingsales_emails", schema = "buildingsales")
@NamedQueries({
        @NamedQuery(name = "Email.findAll", query = "SELECT e FROM Email e")
        , @NamedQuery(name = "Email.findById", query = "SELECT e FROM Email e WHERE e.id = :id")
        , @NamedQuery(name = "Email.findByEmail", query = "SELECT e FROM Email e WHERE e.email = :email")})

public class Email implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ver", nullable = false)
    @Version
    private long version;

    @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    @Size(min = 3, max = 250)
    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    public Email(final String email){
        setEmail(email);
    }
    public Email(){
        this("");
    }
    public Integer getId() {
        return id;
    }
    public long getVersion() {
        return version;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(final String email){
      this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email other = (Email) o;

        //if (!id.equals(other.id)) return false;
        return email.equals(other.email);
    }

    @Override
    public int hashCode() {
        int result = 0;//id.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
