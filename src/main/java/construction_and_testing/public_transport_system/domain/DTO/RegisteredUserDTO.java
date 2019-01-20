package construction_and_testing.public_transport_system.domain.DTO;

public class RegisteredUserDTO {

    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String telephone;
    private boolean active;

    public RegisteredUserDTO() {

    }

    public RegisteredUserDTO(Long id, String name, String lastName, String username, String password, String email, String telephone, boolean active) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.active = active;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
