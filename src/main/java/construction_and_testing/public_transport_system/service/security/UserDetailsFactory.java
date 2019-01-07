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
        Collection<? extends GrantedAuthority> authorities;
        List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(user.getAuthorityType());
        try {
            //auth.add(new SimpleGrantedAuthority(user.getAuthorityType().toString()));
            //authorities = auth; //.map(a -> new SimpleGrantedAuthority(a.toString())).collect(Collectors.toList());
            //authorities = user.getAuthorityType().stream().map(a -> new SimpleGrantedAuthority(a.toString())).collect(Collectors.toList());
            authorities = null;
        } catch (Exception e) {
            authorities = null;
        }

        //AuthorityType auth = AuthorityType.REGISTERED_USER;

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getTelephone(),
                //user.getLastPasswordReset(),
                auth
        );
    }
}

