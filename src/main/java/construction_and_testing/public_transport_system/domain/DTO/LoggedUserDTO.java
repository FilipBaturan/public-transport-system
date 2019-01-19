package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.User;

public class LoggedUserDTO {

    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private boolean active;
    private String telephone;

    public LoggedUserDTO() {
    }

    public LoggedUserDTO(Long id, String firstName, String lastName, String username, String password, String email, boolean active, String telephone) {
        this.id = id;
        this.name = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.active = active;
        this.telephone = telephone;
    }

    public LoggedUserDTO(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.lastName = entity.getLastName();
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.email = entity.getEmail();
        this.active = entity.isActive();
        this.telephone = entity.getTelephone();
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
