package construction_and_testing.public_transport_system.domain.DTO;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Represents user's authentication request.
 * Used when user tries to log in to the system.
 */
public class AuthenticationRequestDTO {

    /**
     * User's username.
     */
    @NotEmpty
    private String username;

    /**
     * User's password.
     */
    @NotEmpty
    private String password;

    public AuthenticationRequestDTO() {
    }

    public AuthenticationRequestDTO(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

