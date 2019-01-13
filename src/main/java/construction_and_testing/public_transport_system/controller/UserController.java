package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.converter.RegisteredUserConverter;
import construction_and_testing.public_transport_system.converter.UserConverter;
import construction_and_testing.public_transport_system.domain.DTO.*;
import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.domain.Validator;
import construction_and_testing.public_transport_system.domain.enums.AuthorityType;
import construction_and_testing.public_transport_system.domain.enums.UsersDocumentsStatus;
import construction_and_testing.public_transport_system.security.TokenUtils;
import construction_and_testing.public_transport_system.service.definition.RegisteredUserService;
import construction_and_testing.public_transport_system.service.definition.UserService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RegisteredUserService registeredUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    /*@Autowired
    public UserController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
                                    UserService userService, TokenUtils tokenUtils) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.tokenUtils = tokenUtils;
    }*/

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * GET /api/user
     * <p>
     * Gets all users.
     *
     * @return all users
     */
    @GetMapping
    public void getAll() {
        //add integration later
    }

    @GetMapping("/getByUsername/{username}")
    public ValidatorDTO getByUsername(@PathVariable String username){
        return UserConverter.fromEntity( (Validator) userService.findByUsername(username) );
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

        //SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        LoggedUserDTO user = UserConverter.fromLoggedEntity(userService.findByUsername(userDetails.getUsername()));
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
        return new ResponseEntity<>(UserConverter.fromLoggedEntity(userService.findCurrentUser()), HttpStatus.OK);
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
        regUser.setPassword(passwordEncoder.encode(regUser.getPassword()));
        Boolean registered = userService.addUser(RegisteredUserConverter.fromRegisteringUserDTO(regUser));
        if (registered) {
            logger.info("Successfully registered user.");
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        }
        logger.info("Failed to register user, user with given username already exists!");
        return new ResponseEntity<>(false, HttpStatus.CONFLICT);
    }

    /**
     * PUT /api/user/modifyRegistered
     * <p>
     * Modifiyng existing registered user
     *
     * @param user - new information
     * @return modified user
     */
    @PutMapping("/modifyRegistered/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RegisteredUser> update(@PathVariable Long id, @RequestBody RegisteringUserDTO user) {
        RegisteredUser changed = RegisteredUserConverter.fromRegisteringUserDTO(user);
        changed.setId(id);
        if(!changed.getPassword().startsWith("$")){
            changed.setPassword(passwordEncoder.encode(changed.getPassword()));
        }
        boolean succeeded = registeredUserService.modify(changed);
        if (succeeded) {
            logger.info("User successfully modified.");
            return new ResponseEntity<>(changed, HttpStatus.OK);
        } else {
            logger.warn("Cannot modify user, probably user with given id doesn't exists!");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    /**
     * GET /api/user/unvalidatedUsers
     * <p>
     * Gets users that are not yet validated in the system.
     *
     * @return list if users
     */
    @GetMapping("/unvalidatedUsers")
    public ResponseEntity<List<UserDTO>> getUnvalidatedUsers() {
        List<User> listOfUsers = userService.getUnvalidatedUsers();
        List<UserDTO> listOfDTOUsers = new ArrayList<>();
        for (User user : listOfUsers) {
            listOfDTOUsers.add(UserConverter.fromEntity(user));
        }

        return new ResponseEntity<>(listOfDTOUsers, HttpStatus.OK);

    }

    @PutMapping("/approveUser")
    public ResponseEntity<Boolean> approveUser(@RequestBody UserDTO user) {

        try {
            User u = this.userService.findById(user.getId());

            u.setConfirmation(UsersDocumentsStatus.APPROVED);

            User savedUser = this.userService.save(u);

            if (savedUser.getAuthorityType() == AuthorityType.REGISTERED_USER) {
                logger.info("Successfully approved user.");
                return new ResponseEntity<>(true, HttpStatus.CREATED);
            }

            logger.info("User found, but his documents could not be accepted!");
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);

        } catch (GeneralException ge) {
            logger.info("Failed to approved user, user with given id does not exists!");
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/denyUser")
    public ResponseEntity<Boolean> denyUser(@RequestBody UserDTO user) {


        try {
            User u = this.userService.findById(user.getId());
            u.setConfirmation(UsersDocumentsStatus.DENIED);

            User savedUser = this.userService.save(u);

            if (savedUser.getAuthorityType() == AuthorityType.REGISTERED_USER) {
                logger.info("Successfully denied users documents!");
                return new ResponseEntity<>(true, HttpStatus.CREATED);
            }
            logger.info("User found, but his documents could not be denied!");
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
        } catch (GeneralException ge) {
            logger.info("Failed to deny user, user with given id does not exists!");
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getValidators")
    public ResponseEntity<List<ValidatorDTO>> getValidators() {
        List<Validator> listOfValidators = userService.getValidators();
        List<ValidatorDTO> listOfDTOValidators = new ArrayList<>();
        for (Validator user : listOfValidators) {
            listOfDTOValidators.add(UserConverter.fromEntity(user));
        }

        return new ResponseEntity<>(listOfDTOValidators, HttpStatus.OK);

    }

    @PutMapping("/updateValidator")
    public ResponseEntity<Boolean> updateValidator(@RequestBody ValidatorDTO userDTO) {

        User validator = null;

        try {
            validator = this.userService.findById(userDTO.getId());
        } catch (GeneralException ge) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }


        if (validator.getAuthorityType() != AuthorityType.VALIDATOR)
            return new ResponseEntity<>(false, HttpStatus.I_AM_A_TEAPOT);

        ModelMapper mapper = new ModelMapper();
        mapper.map(userDTO, validator);
        try {
            this.userService.save(validator);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (GeneralException e) {
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PostMapping("/addValidator")
    ResponseEntity<Boolean> addValidator(@RequestBody ValidatorDTO userDTO) {

        if (userDTO.getId() != null)
            return new ResponseEntity<>(false, HttpStatus.CONFLICT);
        else {
            try{
                userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                Validator newValidator = new Validator( UserConverter.toEntity(userDTO) );
                this.userService.save(newValidator);
            } catch (GeneralException ge) {
                return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
            }

            return new ResponseEntity<>(true, HttpStatus.OK);
        }
    }

    @GetMapping("/registeredUsers")
    public ResponseEntity<List<UserDTO>> getRegisteredUsers() {
        List<RegisteredUser> listOfUsers = userService.getRegisteredUsers();
        List<UserDTO> listOfDTOUsers = new ArrayList<>();
        for (RegisteredUser user : listOfUsers)
            listOfDTOUsers.add(UserConverter.fromEntity(user));

        return new ResponseEntity<>(listOfDTOUsers, HttpStatus.OK);

    }


}
