package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.Operator;
import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.domain.Validator;
import construction_and_testing.public_transport_system.domain.enums.AuthorityType;
import construction_and_testing.public_transport_system.repository.UserRepository;
import construction_and_testing.public_transport_system.service.definition.UserService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        if (userRepository.findByUsername(user.getUsername()) == null) {
            userRepository.saveAndFlush(user);
            return true;
        }
        return false;

    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
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
        return this.userRepository.getUnvalidatedUsers();
    }

    @Override
    public User findById(Long id) {

        try {
            return userRepository.findById(id).get();
        } catch (Exception e) {
            throw new GeneralException("Requested user does not exist!", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public User save(User u) {
        try {
            return userRepository.save(u);
        } catch (DataIntegrityViolationException e) {
            throw new GeneralException("User does not have valid attributes!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<Validator> getValidators() {
        return userRepository.getValidators();
    }

    @Override
    public List<Operator> getOperators() {
        return userRepository.getOperators();
    }

    @Override
    public List<RegisteredUser> getRegisteredUsers() {
        return userRepository.getRegisteredUsers();
    }
}
