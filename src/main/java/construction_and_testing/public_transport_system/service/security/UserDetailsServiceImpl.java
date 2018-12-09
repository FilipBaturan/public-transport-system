package construction_and_testing.public_transport_system.service.security;

import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link UserDetailsService}.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        final User user = userRepository.findByUsername(username);
        // .orElseThrow(() -> new UsernameNotFoundException(String.format("No user with username %s found!", username)));
        return UserDetailsFactory.create(user);
    }
}