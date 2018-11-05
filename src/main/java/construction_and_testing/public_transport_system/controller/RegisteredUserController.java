package construction_and_testing.public_transport_system.controller;


import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.domain.util.ValidationException;
import construction_and_testing.public_transport_system.service.RegisteredUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/registeredUser")
public class RegisteredUserController {


    private static final Logger logger = LoggerFactory.getLogger(RegisteredUserController.class);

    @Autowired
    private RegisteredUserService registeredUserService;

    /**
     * Getting all registered users
     * @return all users
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RegisteredUser>> allRegisteredUsers() {
        logger.info("Fetching all users...");
        List<RegisteredUser> allUsers = registeredUserService.getAll();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    /**
     * Getting registered user by id
     * @param id - id of registered user that we want to get
     * @return registered user with given id
     */
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

    /**
     * Adding new registered user
     * @param newUser - user that we want to add
     * @return added user
     */
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

    /**
     * Modifiyng existing registered user
     * @param id - id of user with old information
     * @param user - new information
     * @return modified user
     */
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

    /**
     * Deleting existing user
     * @param user for removing
     * @return feedback message
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> deleteNews(@RequestBody RegisteredUser user) {
        logger.info("Deleting news with id {} at time {}.", user.getId(), Calendar.getInstance().getTime());
        try {
            registeredUserService.remove(user.getId());
            return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
        }catch (EntityNotFoundException e){
            throw new ValidationException("Requested user does not exist!", HttpStatus.NOT_FOUND);
        }
    }



}
