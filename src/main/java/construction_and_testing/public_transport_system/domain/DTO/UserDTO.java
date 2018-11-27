package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.User;

public class UserDTO {

    private String firstName;
    private String lastName;

    public UserDTO()
    {
        this.firstName = "";
        this.lastName = "";
    }

    public UserDTO(User u)
    {
        this.firstName = u.getName();
        this.lastName = u.getLastName();
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
}
