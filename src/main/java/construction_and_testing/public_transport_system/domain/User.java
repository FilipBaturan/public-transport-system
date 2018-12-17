package construction_and_testing.public_transport_system.domain;

import construction_and_testing.public_transport_system.domain.DTO.UserDTO;
import construction_and_testing.public_transport_system.domain.enums.AuthorityType;
import construction_and_testing.public_transport_system.domain.enums.UsersDocumentsStatus;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Add later
 */
@Entity
@Where(clause = "active =1")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(nullable = false)
    protected String name;

    @Column(nullable = false)
    protected String lastName;

    @Column(nullable = false)
    protected String username;

    @Column(nullable = false)
    protected String password;

    @Column()
    protected String email;

    @Column()
    protected String telephone;

    @Column()
    protected UsersDocumentsStatus confirmation;

    @Column(nullable = false, name = "active")
    private boolean active;

    //    @ElementCollection(targetClass = AuthorityType.class, fetch = FetchType.EAGER)
    @Column(nullable = false, name = "authority")
    private AuthorityType authorityType;

    public User() {
        this.confirmation = UsersDocumentsStatus.UNCHECKED;
        this.active = true;
    }

    public User(Long id, String name, String lastName, String username, String password, String email,
                String telephone, boolean active) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.active = active;
    }

    public User(Long id, String name, String lastName, String username, String password, String email, String telephone,
                UsersDocumentsStatus confirmation) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.confirmation = confirmation;
    }

    public User(Long id, String name, String lastName, String username, String password, String email, String telephone,
                UsersDocumentsStatus confirmation, AuthorityType authorityType) {

        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.confirmation = confirmation;
        this.authorityType = authorityType;
    }

    public User(UserDTO uDTO) {
        this.id = uDTO.getId();
        this.name = uDTO.getFirstName();
        this.lastName = uDTO.getLast();
        this.email = uDTO.getEmail();
        this.username = uDTO.getUsername();
        this.password = uDTO.getPassword();
        this.active = uDTO.isActive();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public UsersDocumentsStatus getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(UsersDocumentsStatus confirmation) {
        this.confirmation = confirmation;
    }

    public AuthorityType getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(AuthorityType authorityType) {
        this.authorityType = authorityType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
