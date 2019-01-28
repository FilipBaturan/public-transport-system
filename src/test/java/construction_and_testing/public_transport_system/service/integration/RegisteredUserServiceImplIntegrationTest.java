package construction_and_testing.public_transport_system.service.integration;

import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.service.definition.RegisteredUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static construction_and_testing.public_transport_system.constants.RegisteredUserConstants.DB_INTEGR_LASTNAME;
import static construction_and_testing.public_transport_system.constants.RegisteredUserConstants.DB_INTEGR_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import static construction_and_testing.public_transport_system.constants.RegisteredUserConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisteredUserServiceImplIntegrationTest {

    @Autowired
    private RegisteredUserService registeredUserService;

    @Test
    public void getAll() {
        List<RegisteredUser> all = registeredUserService.getAll();
        assertThat(all).hasSize(3);
    }

    @Test
    public void getById(){
        RegisteredUser registeredUser = registeredUserService.getById(DB_INTEGR_ID);
        assertThat(registeredUser).isNotNull();
        assertEquals(registeredUser.getId(), DB_INTEGR_ID);
        assertEquals(registeredUser.getUsername(), DB_INTEGR_USERNAME);
        assertEquals(registeredUser.getFirstName(), DB_INTEGR_NAME);
        assertEquals(registeredUser.getLastName(), DB_INTEGR_LASTNAME);
        assertTrue(registeredUser.isActive());
    }

    @Test
    public void getByInvalidId(){
        RegisteredUser regUser = registeredUserService.getById(DB_INVALID_ID);
        assertThat(regUser).isNull();
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void getByNullId(){
        registeredUserService.getById(null);
    }

    @Test
    public void addNew(){
        boolean added = registeredUserService.addNew(DB_USER_3);
        assertThat(added).isTrue();
    }

    @Test
    public void modify(){
        boolean saved = registeredUserService.modify(DB_INTEGR_MODIFIED_USER_1);
        assertTrue(saved);
    }

    @Test
    public void modifyWithInvalidId(){
        boolean saved = registeredUserService.modify(DB_MODIFIED_USER_INVALID_ID);
        assertFalse(saved);
    }

    @Test
    public void remove(){
        registeredUserService.remove(DB_INTEGR_ID_2);
        RegisteredUser user = registeredUserService.getById(DB_INTEGR_ID_2);
        assertThat(user).isNull();
    }

    @Test(expected = EntityNotFoundException.class)
    public void removeWithInvalidId(){
        registeredUserService.remove(DB_INVALID_ID);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void removeWithNullId(){
        registeredUserService.remove(null);
    }

}
