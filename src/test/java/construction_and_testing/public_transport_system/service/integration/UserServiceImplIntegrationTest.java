package construction_and_testing.public_transport_system.service.integration;

import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.domain.Reservation;
import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.domain.Validator;
import construction_and_testing.public_transport_system.domain.enums.AuthorityType;
import construction_and_testing.public_transport_system.domain.enums.UsersDocumentsStatus;
import construction_and_testing.public_transport_system.service.definition.UserService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static construction_and_testing.public_transport_system.constants.UserConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceImplIntegrationTest {

    @Autowired
    UserService userService;

    @Test
    public void findByIdValid() {
        User user = userService.findById(DB_USER_ID);
        assertThat(user).isNotNull();
        assertEquals(user.getId(), DB_USER_ID);
        assertEquals(user.getName(), DB_FIRST_NAME);
        assertEquals(user.getLastName(), DB_LAST_NAME);
        assertEquals(user.getUsername(), DB_USERNAME);
        assertEquals(user.getPassword(), DB_PASSWORD);
        assertEquals(user.getTelephone(), DB_TELEPHONE);
    }

    @Test(expected = GeneralException.class)
    public void findByIdInvalid() {
        User user = userService.findById(DB_USER_ID_INVALID);
        assertThat(user).isNull();
    }

    @Test(expected = GeneralException.class)
    public void findByIdNull() {
        User user = userService.findById(null);
        assertThat(user).isNull();
    }

    @Transactional
    @Test
    public void saveValidUser() {
        int size = userService.findAll().size();

        User userToSave = userService.findById(DB_USER_ID);
        userToSave.setEmail("newEmail@gmail.com");
        userService.save(userToSave);
        User savedUser = userService.findById(DB_USER_ID);
        assertEquals(savedUser.getName(), DB_FIRST_NAME);
        assertEquals(savedUser.getLastName(), DB_LAST_NAME);
        assertEquals(savedUser.getUsername(), DB_USERNAME);
        assertEquals(savedUser.getPassword(), DB_PASSWORD);
        assertEquals(savedUser.getTelephone(), DB_TELEPHONE);

        int sizeAfer = userService.findAll().size();
        assertEquals(size, sizeAfer);

    }

    @Transactional
    @Test
    public void saveNewUser() {
        Set<Reservation> reservations = new HashSet<Reservation>();

        RegisteredUser registeredUser = new RegisteredUser(null, DB_NEW_FIRST_NAME, DB_NEW_LAST_NAME, DB_NEW_USERNAME, DB_NEW_PASSWORD
                , DB_NEW_EMAIL, DB_NEW_TELEPHONE, UsersDocumentsStatus.UNCHECKED, reservations, 100.0, "document");
        registeredUser.setAuthorityType(AuthorityType.REGISTERED_USER);

        User savedUser = userService.save(registeredUser);
        assertThat(savedUser).isNotNull();
        assertEquals(savedUser.getName(), DB_NEW_FIRST_NAME);
        assertEquals(savedUser.getLastName(), DB_NEW_LAST_NAME);
        assertEquals(savedUser.getUsername(), DB_NEW_USERNAME);
        assertEquals(savedUser.getPassword(), DB_NEW_PASSWORD);
        assertEquals(savedUser.getTelephone(), DB_NEW_TELEPHONE);
        assertEquals(savedUser.getAuthorityType(), AuthorityType.REGISTERED_USER);

        List<RegisteredUser> regUsers = userService.getRegisteredUsers();
        assertThat(regUsers).isNotNull();
        assertThat(regUsers.size()).isEqualTo(DB_REG_USERS_COUNT + 1);

    }

    /**
     * User has invalid last name
     */
    @Test(expected = GeneralException.class)
    public void saveInvalidUser() {
        Set<Reservation> reservations = new HashSet<Reservation>();

        User registeredUser = new RegisteredUser(null, DB_NEW_FIRST_NAME, null, DB_NEW_USERNAME, DB_NEW_PASSWORD
                , DB_NEW_EMAIL, DB_NEW_TELEPHONE, UsersDocumentsStatus.UNCHECKED, reservations, 100.0, "document");
        registeredUser.setAuthorityType(AuthorityType.REGISTERED_USER);

        User savedUser = userService.save(registeredUser);
        assertThat(savedUser).isNull();

    }

    @Test
    public void getValidatorsTest() {
        List<Validator> validators = userService.getValidators();
        assertThat(validators).isNotNull();
        for (Validator val : validators) {
            assertThat(val.getAuthorityType()).isEqualTo(AuthorityType.VALIDATOR);
        }
        assertThat(validators.size()).isEqualTo(DB_VAL_COUNT);
    }


    @Test
    public void getRegisteredUsersTest() {
        List<RegisteredUser> regUsers = userService.getRegisteredUsers();
        assertThat(regUsers).isNotNull();
        for (RegisteredUser regUser : regUsers) {
            assertThat(regUser.getAuthorityType()).isEqualTo(AuthorityType.REGISTERED_USER);
        }
        assertThat(regUsers.size()).isEqualTo(DB_REG_USERS_COUNT);

    }

    @Test
    public void getUnvalidatedUsers() {
        List<User> users = userService.getUnvalidatedUsers();
        assertThat(users).isNotNull();
        for (User user : users) {
            assertThat(user.getConfirmation()).isEqualTo(UsersDocumentsStatus.UNCHECKED);
        }
        assertThat(users.size()).isEqualTo(DB_UNCHECKED_COUNT);
    }


}
