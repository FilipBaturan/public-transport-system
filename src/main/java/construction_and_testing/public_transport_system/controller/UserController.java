package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.converter.RegisteredUserConverter;
import construction_and_testing.public_transport_system.domain.DTO.LoginDTO;
import construction_and_testing.public_transport_system.domain.DTO.RegisteringUserDTO;
import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.domain.enums.AuthorityType;
import construction_and_testing.public_transport_system.service.definition.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public void getAll() {
        //add implementation later
    }

    /**
     * Trying to login
     * @param loginDTO - data required for login
     * @param session - session of user
     * @return user from system if exists, message if not
     */
    @RequestMapping(method = RequestMethod.POST, value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> loginUser(@RequestBody LoginDTO loginDTO, HttpSession session) {
        String username = loginDTO.getUsername();
        logger.info("Fetching user with username: " + username);
        User user = userService.findByUsername(username);
        AuthorityType type = userService.getAuthority(username);
        if (user.getPassword().equals(loginDTO.getPassword())) {
            if (type == AuthorityType.REGISTERED_USER && user.isActive()) {
                logger.info("Successfully logged in.");
                session.setAttribute("user", user);
                session.setAttribute("authority", type);
                return new ResponseEntity<Object>(user, HttpStatus.OK);
            }
        }
        logger.info("Failed to login, incorrect combination od username and password");
        return new ResponseEntity<Object>("Incorrect username or password, or user is not activated.",
                                            HttpStatus.BAD_REQUEST);
    }

    /**
     * Registering new user
     * @param regUser new user which is trying to register
     * @return response with success flag, true and 201(CREATED) if registered, false and 409(CONFLICT) if false
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addUser", produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> add(@RequestBody RegisteringUserDTO regUser){
        logger.info("Trying to register new user...");
        Boolean registered = userService.addUser(RegisteredUserConverter.fromRegisteringUserDTO(regUser));
        if(registered){
            logger.info("Successfully registered user.");
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        }
        logger.info("Failed to register user, user with given username already exists!");
        return new ResponseEntity<>(false, HttpStatus.CONFLICT);
    }
 
    /**
     * Logging out
     * @param session - session to invalidate
     * @return http status ok
     */
    @RequestMapping(method = RequestMethod.GET, value="/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> logout(HttpSession session){
        session.invalidate();
        return new ResponseEntity<Object>("Successfully logged out", HttpStatus.OK);
    }
}
