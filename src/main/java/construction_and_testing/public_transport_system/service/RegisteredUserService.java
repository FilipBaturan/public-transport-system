package construction_and_testing.public_transport_system.service;

import construction_and_testing.public_transport_system.domain.RegisteredUser;

import java.util.List;

public interface RegisteredUserService {

    /**
     * Method for getting all registered users
     * @return list of registered users
     */
    List<RegisteredUser> getAll();

    /**
     * Method for getting one registered user, found by its id
     * @param id - id from database
     * @return registered user with given id
     */
    RegisteredUser getById(Long id);

    /**
     * Method for adding new registered user
     * @param user - new user
     * @return success flag (true if added, false if not)
     */
    boolean addNew(RegisteredUser user);

    /**
     * Method for changing user's information
     * @param user - user with modified information
     * @return success flag (true if added, false if not)
     */
    boolean modify(RegisteredUser user);

    /**
     * Method for removing existing user
     * @param id - id of user for deleting
     */
    void remove(Long id);
}
