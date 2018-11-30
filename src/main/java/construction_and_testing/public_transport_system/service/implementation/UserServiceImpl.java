package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.domain.Validator;
import construction_and_testing.public_transport_system.domain.enums.AuthorityType;
import construction_and_testing.public_transport_system.domain.util.GeneralException;
import construction_and_testing.public_transport_system.repository.UserRepository;
import construction_and_testing.public_transport_system.service.definition.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public AuthorityType getAuthority(String username) {
        return userRepository.getAuthority(username);
    }

    @Override
    public Boolean addUser(RegisteredUser user) {
        if(userRepository.findByUsername(user.getUsername()) == null){
            userRepository.saveAndFlush(user);
            return true;
        }
        return false;

    }

    @Override
    public User findCurrentUser() {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return findByUsername(userDetails.getUsername());
    }

    @Override
    public List<User> getUnvalidatedUsers() {
        return  this.userRepository.getUnvalidatedUsers();
    }

    @Override
    public User findById(long id) {

        return userRepository.findById(id).orElseThrow(() ->
                new GeneralException("Requested user does not exist!", HttpStatus.BAD_REQUEST));

    }

    @Override
    public User save(User u)
    {
        return userRepository.save(u);
    }

    @Override
    public List<Validator> getValidators() {
        return userRepository.getValidators();
    }
}
