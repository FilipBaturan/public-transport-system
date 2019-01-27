package construction_and_testing.public_transport_system.service.integration;

import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.repository.UserRepository;
import construction_and_testing.public_transport_system.service.security.UserDetailsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static construction_and_testing.public_transport_system.constants.UserConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Tests load by username when user exists.
     * Should return user details.
     */
    @Test
    public void loadUserByUsernameShouldReturnUserDetailsWhenExists() {
        final User user = userRepository.findById(DB_USER.getId()).orElse(null);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        assertThat(userDetails).isNotNull();
        assertThat(user.getUsername()).isEqualTo(userDetails.getUsername());
    }

    /**
     * Tests load by username when user does not exist.
     * Should throw UsernameNotFoundException.
     */
    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameShouldThrowExceptionWhenUserDoesNotExist() {
        //final User user = userRepository.findByUsername(DB_INVALID_USER_NAME);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(DB_INVALID_USER_NAME);

        assertThat(userDetails).isNotNull();
        assertThat(DB_INVALID_USER_NAME).isEqualTo(userDetails.getUsername());
    }
}