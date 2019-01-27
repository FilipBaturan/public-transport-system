package construction_and_testing.public_transport_system.service.unit;


import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.domain.Reservation;
import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.domain.Validator;
import construction_and_testing.public_transport_system.domain.enums.AuthorityType;
import construction_and_testing.public_transport_system.domain.enums.UsersDocumentsStatus;
import construction_and_testing.public_transport_system.repository.UserRepository;
import construction_and_testing.public_transport_system.service.definition.UserService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.*;

import static construction_and_testing.public_transport_system.constants.UserConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceImplUnitTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Before
    public void setUp() {

        RegisteredUser newRegUser = DB_NEW_USER;
        newRegUser.setAuthorityType(AuthorityType.REGISTERED_USER);

        RegisteredUser savedRegUser = newRegUser;
        savedRegUser.setId(3L);

        List<RegisteredUser> regUsers = new ArrayList<>();
        regUsers.add(savedRegUser);

        RegisteredUser invalidRegUser = DB_INVALID_USER;
        invalidRegUser.setAuthorityType(AuthorityType.REGISTERED_USER);
        invalidRegUser.setLastName(null);

        List<Validator> validators = new ArrayList<>();
        Validator validator = DB_VALIDATOR;
        validator.setAuthorityType(AuthorityType.VALIDATOR);
        validators.add(validator);

        List<User> unvalidatedUsers = new ArrayList<>();
        unvalidatedUsers.add(savedRegUser);

        when(userRepository.findById(DB_USER_ID)).thenReturn(Optional.of(DB_USER));
        when(userRepository.findById(DB_USER_ID_INVALID)).thenThrow(GeneralException.class);
        when(userRepository.findById(null)).thenThrow(GeneralException.class);
        when(userRepository.save(DB_NEW_USER)).thenReturn(savedRegUser);
        when(userRepository.save(newRegUser)).thenReturn(newRegUser);
        when(userRepository.getRegisteredUsers()).thenReturn(regUsers);
        when(userRepository.save(invalidRegUser)).thenThrow(GeneralException.class);
        when(userRepository.getValidators()).thenReturn(validators);
        when(userRepository.getUnvalidatedUsers()).thenReturn(unvalidatedUsers);
    }

    @Test
    public void findByIdValid() {
        User user = userService.findById(DB_USER_ID);
        assertThat(user).isNotNull();
        assertEquals(user.getId(), DB_USER_ID);
        assertEquals(user.getFirstName(), DB_FIRST_NAME);
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
        User userToSave = userService.findById(DB_USER_ID);
        userToSave.setEmail("newEmail@gmail.com");
        userService.save(userToSave);
        User savedUser = userService.findById(DB_USER_ID);
        assertEquals(savedUser.getFirstName(), DB_FIRST_NAME);
        assertEquals(savedUser.getLastName(), DB_LAST_NAME);
        assertEquals(savedUser.getUsername(), DB_USERNAME);
        assertEquals(savedUser.getPassword(), DB_PASSWORD);
        assertEquals(savedUser.getTelephone(), DB_TELEPHONE);
    }

    @Transactional
    @Test
    public void saveNewUser() {
        // Making reg user mock again, because two different methods need to return two different reg users lists
        List<RegisteredUser> regUsersMock = new ArrayList<>();
        RegisteredUser r1 = DB_REG_USER;
        r1.setAuthorityType(AuthorityType.REGISTERED_USER);
        RegisteredUser r2 = DB_REG_USER;
        r1.setAuthorityType(AuthorityType.REGISTERED_USER);
        regUsersMock.add(r1);
        regUsersMock.add(r2);
        when(userRepository.getRegisteredUsers()).thenReturn(regUsersMock);

        User savedUser = userService.save(DB_NEW_USER);
        assertThat(savedUser).isNotNull();
        assertEquals(savedUser.getFirstName(), DB_NEW_FIRST_NAME);
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
     * User has invalid last firstName
     */
    @Transactional
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
            assertThat(users.size()).isEqualTo(DB_UNCHECKED_COUNT);
        }
    }
}
