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
    private LoggedUserDTO user;

    /**
     * User's token.
     */
    private String token;

    public AuthenticationResponseDTO() {
    }

    public AuthenticationResponseDTO(LoggedUserDTO user, String token) {
        this.user = user;
        this.token = token;
    }

    public LoggedUserDTO getUser() {
        return user;
    }

    public void setUser(LoggedUserDTO user) {
        this.user = user;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

