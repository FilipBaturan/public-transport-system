package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.domain.Zone;
import construction_and_testing.public_transport_system.domain.util.ValidationException;
import construction_and_testing.public_transport_system.service.ZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.List;

/**
 * API for zones
 */
@RestController
@RequestMapping("/zone")
public class ZoneController {

    private static final Logger logger = LoggerFactory.getLogger(ZoneController.class);

    @Autowired
    private ZoneService zoneService;

    /**
     * @return all zones
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Zone>> getAll() {
        logger.info("Requesting all available zones at time {}.", Calendar.getInstance().getTime());
        return new ResponseEntity<>(zoneService.getAll(), HttpStatus.OK);
    }

    /**
     * @param id of requested zone
     * @return zone with requested id
     */
    @RequestMapping(method = RequestMethod.GET, value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Zone> getById(@PathVariable String id) {
        logger.info("Requesting zone with id {} at time {}.", id, Calendar.getInstance().getTime());
        try {
            return new ResponseEntity<>(zoneService.findById(Long.parseLong(id)), HttpStatus.FOUND);
        } catch (NumberFormatException e) {
            throw new ValidationException("Bad format of requested id!", HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            throw new ValidationException("Requested zone does not exist!", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param zone that needs to be added
     * @return added zone
     */
    @RequestMapping(method = RequestMethod.POST, value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Zone> addZone(@RequestBody Zone zone) {
        logger.info("Adding zone with at time {}.", Calendar.getInstance().getTime());
        Zone newZone = zoneService.add(zone);
        if( newZone != null){
            return new ResponseEntity<>(newZone, HttpStatus.CREATED);
        }else{
            throw new ValidationException("Zone with given name already exist!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param zone that needs to be updated
     * @return updated zone
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Zone> updateZone(@RequestBody Zone zone) {
        logger.info("Updating zone with id {} at time {}.", zone.getId(), Calendar.getInstance().getTime());
        return new ResponseEntity<>(zoneService.update(zone), HttpStatus.OK);
    }

    /**
     * @param zone that needs to be deleted
     * @return message about action results
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> deleteZone(@RequestBody Zone zone) {
        logger.info("Deleting zone with id {} at time {}.", zone.getId(), Calendar.getInstance().getTime());
        try {
            zoneService.remove(zone.getId());
            return new ResponseEntity<>("Zone successfully deleted!", HttpStatus.OK);
        }catch (EntityNotFoundException e){
            throw new ValidationException("Requested zone does not exist!", HttpStatus.NOT_FOUND);
        }
    }
}
