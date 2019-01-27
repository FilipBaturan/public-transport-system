package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.Operator;

public class OperatorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String telephone;
    private boolean active;

    public OperatorDTO() {
    }

    public OperatorDTO(Long id, String firstName, String lastName, String username, String password, String email, String telephone, boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.active = active;
    }


    public OperatorDTO(String firstName, String lastName, String username, String password, String email, String telephone, boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.active = active;
    }

    public OperatorDTO(Operator operator) {
        this.id = operator.getId();
        this.firstName = operator.getFirstName();
        this.lastName = operator.getLastName();
        this.email = operator.getEmail();
        this.username = operator.getUsername();
        this.password = operator.getPassword();
        this.telephone = operator.getTelephone();
        this.active = operator.isActive();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    @Override
    public String toString() {
        return "OperatorDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", active=" + active +
                '}';
    }
}
