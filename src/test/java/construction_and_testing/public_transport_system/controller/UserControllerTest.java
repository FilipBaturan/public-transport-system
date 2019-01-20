package construction_and_testing.public_transport_system.controller;


import construction_and_testing.public_transport_system.domain.DTO.UserDTO;
import construction_and_testing.public_transport_system.domain.DTO.ValidatorDTO;
import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.domain.enums.UsersDocumentsStatus;
import construction_and_testing.public_transport_system.service.definition.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static construction_and_testing.public_transport_system.constants.TicketConstants.DB_ID;
import static construction_and_testing.public_transport_system.constants.UserConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {
    private final String URL = "/api/user";

    @Autowired
    UserService userService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getUnvalidatedUsersTest() {
        ResponseEntity<UserDTO[]> result = testRestTemplate
                .getForEntity(this.URL + "/unvalidatedUsers/", UserDTO[].class);

        UserDTO[] body = result.getBody();
        assertThat(body).isNotNull();
        assertEquals(body[0].getId(), DB_ID);
        assertEquals(body[0].getFirstName(), DB_FIRST_NAME);
        assertEquals(body[0].getLast(), DB_LAST_NAME);
        assertEquals(body[0].isActive(), true);
        assertEquals(body[0].getEmail(), DB_EMAIL);
        assertEquals(body[0].getPassword(), DB_PASSWORD);
        assertEquals(result.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void approveUserValid() {
        UserDTO userDTO = new UserDTO(DB_USER);

        ResponseEntity<Boolean> result = testRestTemplate
                .exchange(this.URL + "/approveUser/", HttpMethod.PUT,
                        new HttpEntity<UserDTO>(userDTO), Boolean.class);

        Boolean body = result.getBody();

        assertTrue(body);
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        User approvedUser = userService.findById(DB_USER_ID);
        assertEquals(approvedUser.getConfirmation(), UsersDocumentsStatus.APPROVED);

    }

    @Test
    public void approveUserInvalid() {
        Long randomId = 124523L;
        UserDTO userDTO = new UserDTO(randomId, "name", "lastName", "email", "username", "pass");

        ResponseEntity<Boolean> result = testRestTemplate
                .exchange(this.URL + "/approveUser/", HttpMethod.PUT,
                        new HttpEntity<UserDTO>(userDTO), Boolean.class);

        Boolean body = result.getBody();

        assertNotNull(body);
        assertFalse(body);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
        User approvedUser = userService.findById(DB_USER_ID);
        assertNotEquals(approvedUser.getConfirmation(), UsersDocumentsStatus.APPROVED);

    }

    @Test
    public void approveUserValidator() {
        // Id of validator from data.sql file
        UserDTO userDTO = new UserDTO(2L, "name", "lastName", "email", "username", "pass");

        ResponseEntity<Boolean> result = testRestTemplate
                .exchange(this.URL + "/approveUser/", HttpMethod.PUT,
                        new HttpEntity<UserDTO>(userDTO), Boolean.class);

        Boolean body = result.getBody();

        assertNotNull(body);
        assertFalse(body);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);

    }

    @Test
    public void denyUserValid() {
        UserDTO userDTO = new UserDTO(DB_USER);

        ResponseEntity<Boolean> result = testRestTemplate
                .exchange(this.URL + "/denyUser/", HttpMethod.PUT,
                        new HttpEntity<UserDTO>(userDTO), Boolean.class);

        Boolean body = result.getBody();

        assertNotNull(body);
        assertTrue(body);
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        User approvedUser = userService.findById(DB_USER_ID);
        assertEquals(approvedUser.getConfirmation(), UsersDocumentsStatus.DENIED);

    }

    @Test
    public void denyUserInvalid() {
        Long randomId = 124523L;
        UserDTO userDTO = new UserDTO(randomId, "name", "lastName", "email", "username", "pass");

        ResponseEntity<Boolean> result = testRestTemplate
                .exchange(this.URL + "/denyUser/", HttpMethod.PUT,
                        new HttpEntity<UserDTO>(userDTO), Boolean.class);

        Boolean body = result.getBody();

        assertNotNull(body);
        assertFalse(body);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
        User approvedUser = userService.findById(DB_USER_ID);
        assertNotEquals(approvedUser.getConfirmation(), UsersDocumentsStatus.DENIED);

    }

    @Test
    public void denyUserValidator() {
        // Id of validator from data.sql file
        UserDTO userDTO = new UserDTO(2L, "name", "lastName", "email", "username", "pass");

        ResponseEntity<Boolean> result = testRestTemplate
                .exchange(this.URL + "/denyUser/", HttpMethod.PUT,
                        new HttpEntity<UserDTO>(userDTO), Boolean.class);

        Boolean body = result.getBody();

        assertNotNull(body);
        assertFalse(body);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);

    }

    @Test
    public void updateValidAtor() {
        ValidatorDTO userDTO = new ValidatorDTO(2L, "name", "lastName", "email", "username", "pass");
        userDTO.setEmail("newEmail");

        ResponseEntity<Boolean> result = testRestTemplate
                .exchange(this.URL + "/updateValidator/", HttpMethod.PUT,
                        new HttpEntity<ValidatorDTO>(userDTO), Boolean.class);

        Boolean body = result.getBody();

        assertNotNull(body);
        assertTrue(body);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        User approvedUser = userService.findById(2L);
        assertEquals(approvedUser.getEmail(), "newEmail");

    }

    @Test
    public void updateValidatorInvalid() {
        Long randomId = 124523L;
        UserDTO userDTO = new UserDTO(randomId, "name", "lastName", "email", "username", "pass");
        userDTO.setEmail("newEmail");

        ResponseEntity<Boolean> result = testRestTemplate
                .exchange(this.URL + "/updateValidator/", HttpMethod.PUT,
                        new HttpEntity<UserDTO>(userDTO), Boolean.class);

        Boolean body = result.getBody();

        assertNotNull(body);
        assertFalse(body);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    public void updateValidatorRegUser() {
        // Id of a registerd user
        UserDTO userDTO = new UserDTO(1L, "name", "lastName", "email", "username", "pass");

        ResponseEntity<Boolean> result = testRestTemplate
                .exchange(this.URL + "/updateValidator/", HttpMethod.PUT,
                        new HttpEntity<UserDTO>(userDTO), Boolean.class);

        Boolean body = result.getBody();

        assertNotNull(body);
        assertFalse(body);
        assertEquals(result.getStatusCode(), HttpStatus.I_AM_A_TEAPOT);

    }

    @Test
    public void updateValidatorNullParameter() {
        // parameter password missing
        UserDTO userDTO = new UserDTO(2L, "name", "lastName", "email", null, "pass");

        ResponseEntity<Boolean> result = testRestTemplate
                .exchange(this.URL + "/updateValidator/", HttpMethod.PUT,
                        new HttpEntity<UserDTO>(userDTO), Boolean.class);

        Boolean body = result.getBody();

        assertNotNull(body);
        assertFalse(body);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);

    }

    @Test
    public void getValidAtors() {
        ResponseEntity<ValidatorDTO[]> result = testRestTemplate
                .getForEntity(this.URL + "/getValidators/", ValidatorDTO[].class);

        ValidatorDTO[] body = result.getBody();
        assertThat(body).isNotNull();
        Long valId = 2L;
        assertEquals(body[0].getId(), valId);
        assertEquals(body[0].getFirstName(), DB_VAL_FIRST_NAME);
        assertEquals(body[0].getLastName(), DB_VAL_LAST_NAME);
        assertEquals(body[0].isActive(), true);
        assertEquals(body[0].getEmail(), DB_VAL_EMAIL);
        assertEquals(body[0].getPassword(), DB_VAL_PASSWORD);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void addValidAtor() {
        ValidatorDTO newValidator = new ValidatorDTO(null, "newFirstName", "newLastName",
                "newEmail", "newPass", "newUser");

        int size = userService.getValidators().size();

        ResponseEntity<Boolean> result = testRestTemplate
                .postForEntity(this.URL + "/addValidator/", newValidator, Boolean.class);

        Boolean response = result.getBody();
        assertTrue(response);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        User savedValidator = userService.findByUsername(newValidator.getUsername());
        assertEquals(savedValidator.getEmail(), "newEmail");
        assertEquals(savedValidator.getFirstName(), "newFirstName");
        assertEquals(savedValidator.getLastName(), "newLastName");

        int sizeAfter = userService.getValidators().size();
        assertEquals(size, sizeAfter - 1);
    }


    @Test
    public void addInvalidator() {
        Long randomId = 12343L;
        ValidatorDTO newValidator = new ValidatorDTO(randomId, "newFirstName", "newLastName",
                "newEmail", "newUser", "newPass");

        int size = userService.getValidators().size();

        ResponseEntity<Boolean> result = testRestTemplate
                .postForEntity(this.URL + "/addValidator/", newValidator, Boolean.class);

        Boolean response = result.getBody();
        assertFalse(response);
        assertEquals(result.getStatusCode(), HttpStatus.CONFLICT);

        // size should remain the same
        int sizeAfter = userService.getValidators().size();
        assertEquals(size, sizeAfter);
    }

    /**
     * Parameter pass is missing
     */
    @Test
    public void addValidatorNullParameter() {
        ValidatorDTO newValidator = new ValidatorDTO(null, null, "newLastName",
                "newEmail", "asdasd", "userName");

        int size = userService.getValidators().size();

        ResponseEntity<Boolean> result = testRestTemplate
                .postForEntity(this.URL + "/addValidator/", newValidator, Boolean.class);

        Boolean response = result.getBody();
        assertFalse(response);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);

        // size should remain the same
        int sizeAfter = userService.getValidators().size();
        assertEquals(size, sizeAfter);
    }

    @Test
    public void getRegUsersValid() {
        ResponseEntity<UserDTO[]> result = testRestTemplate
                .getForEntity(this.URL + "/registeredUsers/", UserDTO[].class);

        UserDTO[] body = result.getBody();
        assertThat(body).isNotNull();
        Long valId = 1L;
        assertEquals(body[0].getId(), valId);
        assertEquals(body[0].getFirstName(), DB_FIRST_NAME);
        assertEquals(body[0].getLast(), DB_LAST_NAME);
        assertEquals(body[0].isActive(), true);
        assertEquals(body[0].getEmail(), DB_EMAIL);
        assertEquals(body[0].getPassword(), DB_PASSWORD);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

}
