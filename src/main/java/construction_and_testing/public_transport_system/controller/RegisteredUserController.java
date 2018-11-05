package construction_and_testing.public_transport_system.controller;


import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.service.RegisteredUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/registeredUser")
public class RegisteredUserController {


    private static final Logger logger = LoggerFactory.getLogger(RegisteredUserController.class);

    @Autowired
    private RegisteredUserService registeredUserService;


    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RegisteredUser>> allRegisteredUsers() {
        logger.info("Fetching all users...");
        List<RegisteredUser> allUsers = registeredUserService.getAll();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisteredUser> getById(@PathVariable Long id){
        logger.info("Fetching user by id " + id + ".");
        RegisteredUser user = registeredUserService.getById(id);
        if(user != null){
            logger.info("Successfully fetched user with id " + id + ".");
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else {
            logger.warn("Cannot find user with id " + id + "!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addNew", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisteredUser> addNewUser(@RequestBody RegisteredUser newUser){
        boolean succeeded = registeredUserService.addNew(newUser);
        if(succeeded){
            logger.info("New user added.");
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        }
        else{
            logger.warn("Cannot save new user, probably some unique information are already exist!");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/modify/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisteredUser> modify(@PathVariable Long id, @RequestBody RegisteredUser user){
        user.setId(id);
        boolean succeeded = registeredUserService.modify(user);
        if(succeeded){
            logger.info("User successfully modified.");
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else{
            logger.warn("Cannot modify user, probably user with given id doesn't exists!");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }




}
