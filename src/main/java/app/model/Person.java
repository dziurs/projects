package app.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@NamedQueries({@NamedQuery(name = "Person.findByEmail", query = "SELECT p FROM Person p WHERE p.email.email = :email")})
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private long version;

    @Size(min = 2, max = 50)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Size(min = 2, max = 250)
    @Column(name = "surname", nullable = false)
    private String surname;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "email_id", nullable = false, unique = true)
    private Email email;

    public Long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return getFirstName().equals(person.getFirstName()) &&
                getSurname().equals(person.getSurname()) &&
                getEmail().equals(person.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getSurname(), getEmail());
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", email=" + email +
                '}';
    }
}
