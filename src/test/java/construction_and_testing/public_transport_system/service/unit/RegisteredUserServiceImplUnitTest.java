package construction_and_testing.public_transport_system.service.unit;

import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.repository.RegisteredUserRepository;
import construction_and_testing.public_transport_system.service.implementation.RegisteredUserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static construction_and_testing.public_transport_system.constants.RegisteredUserConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisteredUserServiceImplUnitTest {

    @MockBean
    private RegisteredUserRepository registeredUserRepository;

    @Autowired
    private RegisteredUserServiceImpl registeredUserService;

    @Before
    public void setUp() {
        when(registeredUserRepository.findById(DB_VALID_ID)).thenReturn(Optional.of(DB_USER_1));
        when(registeredUserRepository.findById(DB_INVALID_ID)).thenReturn(Optional.empty());
        when(registeredUserRepository.saveAndFlush(DB_MODIFIED_USER_1)).thenReturn(DB_MODIFIED_USER_1);
        when(registeredUserRepository.save(DB_DEL_USER_1)).thenReturn(DB_DEL_USER_1);

    }

    /**
     * Valid test for getting registered user by existing ID
     */
    @Test
    public void getByIdTest() {
        RegisteredUser registeredUser = registeredUserService.getById(DB_VALID_ID);
        assertThat(registeredUser).isNotNull();
        assertEquals(registeredUser.getId(), DB_VALID_ID);
        assertEquals(registeredUser.getUsername(), DB_USERNAME);
        assertEquals(registeredUser.getPassword(), DB_PASSWORD);
        assertEquals(registeredUser.getFirstName(), DB_NAME);
        assertEquals(registeredUser.getLastName(), DB_LASTNAME);
        assertTrue(registeredUser.isActive());
        verify(registeredUserRepository, times(2)).findById(DB_VALID_ID);
    }

    /**
     * Test when invalid ID is given
     */
    @Test
    public void getByInvalidIdTest() {
        RegisteredUser regUser = registeredUserService.getById(DB_INVALID_ID);
        assertThat(regUser).isNull();
        verify(registeredUserRepository, times(1)).findById(DB_INVALID_ID);
    }

    /**
     * Test when given ID is null
     */
    @Test
    public void getByNullIdTest() {
        RegisteredUser regUser = registeredUserService.getById(null);
        assertThat(regUser).isNull();
        verify(registeredUserRepository, times(1)).findById(null);
    }

    /**
     * Valid test for modifying registered user
     */
    @Test
    public void modifyRegisteredUserTest() {
        boolean saved = registeredUserService.modify(DB_MODIFIED_USER_1);
        assertTrue(saved);
        verify(registeredUserRepository, times(2)).findById(DB_VALID_ID);
        verify(registeredUserRepository, times(1)).saveAndFlush(DB_MODIFIED_USER_1);
    }

    /**
     * Test when registered user contains invalid ID
     */
    @Test
    public void modifyInvalidUser() {
        boolean saved = registeredUserService.modify(DB_MODIFIED_USER_INVALID_ID);
        assertFalse(saved);
        verify(registeredUserRepository, times(1)).findById(DB_INVALID_ID);
    }

    /**
     * Valid test for deleting registered user
     */
    @Test
    public void deleteUserTest() {
        registeredUserService.remove(DB_VALID_ID);
        verify(registeredUserRepository, times(1)).findById(DB_VALID_ID);
        verify(registeredUserRepository, times(1)).save(DB_DEL_USER_1);
    }

    /**
     * Test for trying to delete registered user with invalid ID
     */
    @Test(expected = EntityNotFoundException.class)
    public void deleteInvalidUser() {
        registeredUserService.remove(DB_INVALID_ID);
        verify(registeredUserRepository, times(1)).findById(DB_INVALID_ID);

    }

}
