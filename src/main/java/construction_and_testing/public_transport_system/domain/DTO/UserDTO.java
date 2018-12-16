package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.domain.enums.AuthorityType;
import construction_and_testing.public_transport_system.domain.enums.UsersDocumentsStatus;

public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private boolean active;

//    private String telephone;
//
//    private UsersDocumentsStatus confirmation;
//
//    private AuthorityType authorityType;
//
//    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO()
    {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
        this.active = false;
    }

    public UserDTO(Long id, String name, String lastName, String email, String password, String username)
    {
        this.id = id;
        this.firstName = name;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.active = true;
    }

    public UserDTO(User u)
    {
        this.id = u.getId();
        this.firstName = u.getName();
        this.lastName = u.getLastName();
        this.email = u.getEmail();
        this.username = u.getUsername();
        this.password = u.getPassword();
        this.active = u.isActive();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLast() {
        return lastName;
    }

    public void setLast(String lastName) {
        this.lastName = lastName;
    }
}
