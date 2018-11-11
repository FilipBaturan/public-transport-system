package construction_and_testing.public_transport_system.service.definition;

import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.domain.enums.AuthorityType;

/**
 * Service which includes all required methods for users and their data
 */
public interface UserService {

    /**
     * Service method for getting user by given username
     * @param username - given username for expected user
     * @return - user with given username
     */
    User findByUsername(String username);

    /**
     * Method for getting user authority by given username
     * @param username - given username for expected user's authority
     * @return authority of user with given username
     */
    AuthorityType getAuthority(String username);

    /**
     * Method for saving new registered user
     * @param user - user which is just registered
     * @return - indicator of registration success, true for successful, false for unsuccessful
     */
    Boolean addUser(RegisteredUser user);



}
