package construction_and_testing.public_transport_system.service.security;

import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.domain.security.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Factory for creating instance of {@link UserDetailsImpl}.
 */
public class UserDetailsFactory {

    private UserDetailsFactory() {
    }

    /**
     * Creates UserDetailsImpl from a user.
     *
     * @param user user model
     * @return UserDetailsImpl
     */
    public static UserDetailsImpl create(User user) {
        List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(user.getAuthorityType());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getTelephone(),
                auth
        );
    }
}

