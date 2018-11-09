package construction_and_testing.public_transport_system.service;

import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.domain.enums.AuthorityType;
import construction_and_testing.public_transport_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
