package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.User;

/**
 * Represents authentication response.
 * After user successfully logs this response is sent.
 */
public class AuthenticationResponseDTO {

    /**
     * User that requested authentication.
     */
    private User user;

    /**
     * User's token.
     */
    private String token;

    public AuthenticationResponseDTO() {
    }

    public AuthenticationResponseDTO(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

