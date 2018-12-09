package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.converter.RegisteredUserConverter;
import construction_and_testing.public_transport_system.domain.DTO.AuthenticationRequestDTO;
import construction_and_testing.public_transport_system.domain.DTO.AuthenticationResponseDTO;
import construction_and_testing.public_transport_system.domain.DTO.RegisteringUserDTO;
import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.domain.enums.AuthorityType;
import construction_and_testing.public_transport_system.security.TokenUtils;
import construction_and_testing.public_transport_system.service.definition.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //@Autowired
    private final AuthenticationManager authenticationManager;

    //@Autowired
    private final UserDetailsService userDetailsService;

    //@Autowired
    private final UserService userService;

    //@Autowired
    private final TokenUtils tokenUtils;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
                                    UserService userService, TokenUtils tokenUtils) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.tokenUtils = tokenUtils;
    }

    /**
     * GET /api/user
     * <p>
     * Gets all users.
     *
     * @return all users
     */
    @GetMapping
    public void getAll() {
        //add implementation later
    }

    /**
     * POST /api/user/auth
     * <p>
     * Authenticates a user in the system.
     *
     * @param authenticationRequest DTO with user's login credentials
     * @return ResponseEntity with a AuthenticationResponseDTO, containing user data and his JSON Web Token
     */
    @PostMapping("/auth")
    public ResponseEntity<Object> login(@Valid @RequestBody AuthenticationRequestDTO authenticationRequest) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        User user = userService.findByUsername(userDetails.getUsername());
        String token = tokenUtils.generateToken(userDetails);

        logger.info("Successfully logged in.");
        return new ResponseEntity<>(new AuthenticationResponseDTO(user, token), HttpStatus.OK);
        //}
        /*logger.info("Failed to login, incorrect combination od username and password");
        return new ResponseEntity<Object>("Incorrect username or password, or user is not activated.",
                HttpStatus.BAD_REQUEST);*/
    }

    /**
     * GET /api/user/currentUser
     * <p>
     * Gets User object of user that's sending the request.
     *
     * @return User
     */
    @GetMapping("/currentUser")
    public ResponseEntity findCurrentUser() {
        return new ResponseEntity<>(userService.findCurrentUser(), HttpStatus.OK);
    }

    /**
     * POST /api/user
     * <p>
     * Registering new user
     *
     * @param regUser new user which is trying to register
     * @return response with success flag, true and 201(CREATED) if registered, false and 409(CONFLICT) if false
     */
    @PostMapping(path = "/add")
    public ResponseEntity<Boolean> create(@RequestBody RegisteringUserDTO regUser) {
        logger.info("Trying to register new user...");
        System.out.println(regUser.getTelephone());
        Boolean registered = userService.addUser(RegisteredUserConverter.fromRegisteringUserDTO(regUser));
        if (registered) {
            logger.info("Successfully registered user.");
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        }
        logger.info("Failed to register user, user with given username already exists!");
        return new ResponseEntity<>(false, HttpStatus.CONFLICT);
    }
}
