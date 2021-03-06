package construction_and_testing.public_transport_system.controller;


import construction_and_testing.public_transport_system.domain.DTO.*;
import construction_and_testing.public_transport_system.domain.Operator;
import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.domain.Validator;
import construction_and_testing.public_transport_system.domain.enums.AuthorityType;
import construction_and_testing.public_transport_system.domain.enums.UsersDocumentsStatus;
import construction_and_testing.public_transport_system.service.definition.UserService;
import construction_and_testing.public_transport_system.util.SecurityUtil;
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

import javax.ws.rs.ForbiddenException;

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
        assertEquals(body[0].getLastName(), DB_LAST_NAME);
        assertEquals(body[0].isActive(), true);
        assertEquals(body[0].getEmail(), DB_EMAIL);
        //assertEquals(body[0].getPassword(), DB_PASSWORD);
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
        assertEquals(result.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);

        // size should remain the same
        int sizeAfter = userService.getValidators().size();
        assertEquals(size, sizeAfter);
    }

    @Test
    public void addInvalidatorNotExisting() {
        Long randomId = 12343L;
        ValidatorDTO newValidator = new ValidatorDTO(null, "newFirstName", "newLastName",
                "newEmail", "newUser", "valUsername");

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
    public void updateOperator() {
        OperatorDTO operatorDTO = new OperatorDTO(4L, "Ime", "Prezime", "korime", "123123", "mail@mail.com", "123", true);
        operatorDTO.setEmail("email@mail.com");

        ResponseEntity<Boolean> result = testRestTemplate
                .exchange(this.URL + "/updateOperator", HttpMethod.PUT,
                        new HttpEntity<OperatorDTO>(operatorDTO), Boolean.class);

        Boolean body = result.getBody();
        System.out.println(body.toString());
        assertNotNull(body);
        assertTrue(body);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        User approvedUser = userService.findById(4L);
        assertEquals(approvedUser.getEmail(), "email@mail.com");

    }

    @Test
    public void updateOperatorInvalid() {
        Long randomId = 124523L;
        OperatorDTO operatorDTO = new OperatorDTO(randomId, "Ime", "Prezime", "korime", "123123", "mail@mail.com", "123", true);
        operatorDTO.setEmail("mail@mail.com");

        ResponseEntity<Boolean> result = testRestTemplate
                .exchange(this.URL + "/updateOperator", HttpMethod.PUT,
                        new HttpEntity<OperatorDTO>(operatorDTO), Boolean.class);

        Boolean body = result.getBody();

        assertNotNull(body);
        assertFalse(body);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    public void updateOperatorRegUserId() {
        // Id of a registerd user
        UserDTO userDTO = new UserDTO(1L, "name", "lastName", "email", "username", "pass");

        ResponseEntity<Boolean> result = testRestTemplate
                .exchange(this.URL + "/updateOperator/", HttpMethod.PUT,
                        new HttpEntity<UserDTO>(userDTO), Boolean.class);

        Boolean body = result.getBody();

        assertNotNull(body);
        assertFalse(body);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);

    }

    @Test
    public void updateOperatorNullParameter() {
        // parameter password missing
        OperatorDTO operatorDTO = new OperatorDTO(3L, "Ime", "Prezime", "korime", null, "mail@mail.com", "123", true);

        ResponseEntity<Boolean> result = testRestTemplate
                .exchange(this.URL + "/updateOperator/", HttpMethod.PUT,
                        new HttpEntity<OperatorDTO>(operatorDTO), Boolean.class);

        Boolean body = result.getBody();

        assertNotNull(body);
        assertFalse(body);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
    }

    @Test
    public void getOperators() {
        ResponseEntity<OperatorDTO[]> result = testRestTemplate
                .getForEntity(this.URL + "/getOperators", OperatorDTO[].class);

        OperatorDTO[] body = result.getBody();
        assertThat(body).isNotNull();
        Operator operator = (Operator) this.userService.findById(3L);
        assertEquals(body[0].getId(), operator.getId());
        assertEquals(body[0].getFirstName(), operator.getFirstName());
        assertEquals(body[0].getLastName(), operator.getLastName());
        assertTrue(body[0].isActive());
        assertEquals(body[0].getEmail(), operator.getEmail());
        assertEquals(body[0].getPassword(), operator.getPassword());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void addOperator() {
        OperatorDTO operatorDTO = new OperatorDTO(null, "Opera", "Tor", "operator", "321", "operator@mail.com", "353234", true);

        int size = userService.getOperators().size();

        ResponseEntity<Boolean> result = testRestTemplate
                .postForEntity(this.URL + "/addOperator", operatorDTO, Boolean.class);

        Boolean response = result.getBody();
        assertTrue(response);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        User savedValidator = userService.findByUsername(operatorDTO.getUsername());
        assertEquals(savedValidator.getEmail(), "operator@mail.com");
        assertEquals(savedValidator.getFirstName(), "Opera");
        assertEquals(savedValidator.getLastName(), "Tor");

        int sizeAfter = userService.getOperators().size();
        assertEquals(size + 1, sizeAfter);
    }

    @Test
    public void addInvalid() {
        OperatorDTO operatorDTO = new OperatorDTO(null, null, "Tor", "operator", "321", "operator@mail.com", "353234", true);

        int size = userService.getOperators().size();

        ResponseEntity<Boolean> result = testRestTemplate
                .postForEntity(this.URL + "/addOperator", operatorDTO, Boolean.class);

        Boolean response = result.getBody();
        assertFalse(response);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);

        int sizeAfter = userService.getOperators().size();
        assertEquals(size, sizeAfter);
    }

    @Test
    public void addOperatorExistingUsername() {
        OperatorDTO operatorDTO = new OperatorDTO(null, "Opera", "Tor", "username", "321", "operator@mail.com", "353234", true);

        int size = userService.getOperators().size();

        ResponseEntity<Boolean> result = testRestTemplate
                .postForEntity(this.URL + "/addOperator", operatorDTO, Boolean.class);

        Boolean response = result.getBody();
        assertFalse(response);
        assertEquals(result.getStatusCode(), HttpStatus.CONFLICT);

        int sizeAfter = userService.getOperators().size();
        assertEquals(size, sizeAfter);
    }


    /**
     * Parameter pass is missing
     */
    @Test
    public void addOperatorNullParameter() {
        OperatorDTO operatorDTO = new OperatorDTO(5L, "Opera", "Tor", "operator", "321", "operator@mail.com", "353234", true);

        int size = userService.getOperators().size();

        ResponseEntity<Boolean> result = testRestTemplate
                .postForEntity(this.URL + "/addOperator", operatorDTO, Boolean.class);

        Boolean response = result.getBody();
        assertFalse(response);
        assertEquals(result.getStatusCode(), HttpStatus.CONFLICT);

        int sizeAfter = userService.getOperators().size();
        assertEquals(size, sizeAfter);
    }


    @Test
    public void getOpByUsername() {
        ResponseEntity<OperatorDTO> result = testRestTemplate
                .getForEntity(this.URL + "/getOpByUsername/" + DB_VALID_OP_USER_NAME, OperatorDTO.class);

        OperatorDTO body = (OperatorDTO) result.getBody();
        assertNotNull(body);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertThat(body.getUsername()).isEqualTo(DB_VALID_OP_USER_NAME);
    }

    @Test
    public void getOpByUsernameNotExisting() {
        ResponseEntity<OperatorDTO> result = testRestTemplate
                .getForEntity(this.URL + "/getOpByUsername/" + DB_INVALID_OP_USER_NAME, OperatorDTO.class);

        OperatorDTO body = (OperatorDTO) result.getBody();

        assertNull(body.getId());
        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

//    @Test
//    public void getByUsernameRegUser() {
//        String username = "username";
//
//        ResponseEntity<Object> result = testRestTemplate
//                .getForEntity(this.URL + "/getByUsername/" + username, Object.class);
//
//        Object body = result.getBody();
//
//        assertNotNull(body);
//        assertEquals(result.getStatusCode(), HttpStatus.OK);
//        //assertThat(body.getUsername()).isEqualTo(username);
//        //assertThat(body.getAuthorityType()).isEqualTo(AuthorityType.REGISTERED_USER);
//    }
//
//    @Test
//    public void getByUsernameNotExisting() {
//        String username = "wrong_username";
//
//        ResponseEntity<Object> result = testRestTemplate
//                .getForEntity(this.URL + "/getByUsername/" + DB_INVALID_USER_NAME, Object.class);
//
//        RegisteredUser body = (RegisteredUser) result.getBody();
//
//        assertNull(body);
//        assertEquals(result.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
//    }

    @Test
    public void getRegUsersValid() {
        ResponseEntity<UserDTO[]> result = testRestTemplate
                .getForEntity(this.URL + "/registeredUsers/", UserDTO[].class);

        UserDTO[] body = result.getBody();
        assertThat(body).isNotNull();
        Long valId = 1L;
        assertEquals(body[0].getId(), valId);
        assertEquals(body[0].getFirstName(), DB_FIRST_NAME);
        assertEquals(body[0].getLastName(), DB_LAST_NAME);
        assertEquals(body[0].isActive(), true);
        assertEquals(body[0].getEmail(), DB_EMAIL);
        //assertEquals(body[0].getPassword(), DB_PASSWORD);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getByUsernameValid() {
        ResponseEntity<ValidatorDTO> result = testRestTemplate
                .getForEntity(this.URL + "/getByUsername/" + DB_VAL_USERNAME, ValidatorDTO.class);

        ValidatorDTO body = result.getBody();
        assertThat(body).isNotNull();
        Long valId = 2L;
        assertEquals(body.getId(), valId);
        assertEquals(body.getFirstName(), DB_VAL_FIRST_NAME);
        assertEquals(body.getLastName(), DB_VAL_LAST_NAME);
        assertEquals(body.isActive(), true);
        assertEquals(body.getEmail(), DB_VAL_EMAIL);
        assertEquals(body.getPassword(), DB_VAL_PASSWORD);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getByUsernameInvalid() {
        ResponseEntity<ValidatorDTO> result = testRestTemplate
                .getForEntity(this.URL + "/getByUsername/" + "randomUsername883", ValidatorDTO.class);

        ValidatorDTO body = result.getBody();
        assertEquals(body.getId(), null);
        assertEquals(body.getFirstName(), null);
        assertEquals(body.getLastName(), null);
        assertEquals(body.isActive(), false);
        assertEquals(body.getEmail(), null);
        assertEquals(body.getPassword(), null);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void getRegByUsernameValid() {
        ResponseEntity<UserDTO> result = testRestTemplate
                .getForEntity(this.URL + "/getRegByUsername/" + DB_USERNAME, UserDTO.class);

        UserDTO body = result.getBody();
        assertThat(body).isNotNull();
        Long valId = 1L;
        assertEquals(body.getId(), valId);
        assertEquals(body.getFirstName(), DB_FIRST_NAME);
        assertEquals(body.getLastName(), DB_LAST_NAME);
        assertEquals(body.isActive(), true);
        assertEquals(body.getEmail(), DB_EMAIL);
        //assertEquals(body.getPassword(), DB_PASSWORD);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getRegByUsernameInvalid() {
        ResponseEntity<UserDTO> result = testRestTemplate
                .getForEntity(this.URL + "/getRegByUsername/" + "randomUsername883", UserDTO.class);

        UserDTO body = result.getBody();
        assertEquals(body.getId(), null);
        assertEquals(body.getFirstName(), "");
        assertEquals(body.getLastName(), "");
        assertEquals(body.isActive(), false);
        assertEquals(body.getEmail(), "");
        assertEquals(body.getPassword(), "");
        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void login(){
        AuthenticationRequestDTO authDTO = new AuthenticationRequestDTO("null", "null");

        ResponseEntity<AuthenticationResponseDTO> authResult = testRestTemplate
                .postForEntity(this.URL + "/auth", authDTO, AuthenticationResponseDTO.class);

        AuthenticationResponseDTO bodyLogin = authResult.getBody();
        assertEquals(authResult.getStatusCode(), HttpStatus.OK);
        assertEquals(bodyLogin.getUser().getUsername(), "null");
    }

    @Test
    public void findCurrentUser(){
        ResponseEntity<UserDTO> result = testRestTemplate
                .getForEntity(this.URL + "/currentUser", UserDTO.class);

        UserDTO body = (UserDTO) result.getBody();

        assertNull(body);
        assertEquals(result.getStatusCode(), HttpStatus.FORBIDDEN);


//        ResponseEntity<String> res = testRestTemplate
//                .getForEntity(this.URL + "/currentUser", String.class);
//
//
//        System.out.println(res.toString());
//        String bod = res.getBody();
//        System.out.println(bod);
//        assertNotNull(bod);
//        assertEquals(res.getStatusCode(), HttpStatus.OK);
    }

}
