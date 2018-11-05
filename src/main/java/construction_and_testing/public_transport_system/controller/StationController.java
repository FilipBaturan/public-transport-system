package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.domain.Station;
import construction_and_testing.public_transport_system.domain.util.ValidationException;
import construction_and_testing.public_transport_system.service.StationService;
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

@RestController
@RequestMapping("/station")
public class StationController {

    private static final Logger logger = LoggerFactory.getLogger(StationController.class);

    @Autowired
    private StationService stationService;

    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Station>> getAll() {
        logger.info("Requesting all available stations at time {}.", Calendar.getInstance().getTime());
        return new ResponseEntity<>(stationService.getAll(), HttpStatus.OK);
    }

    /**
     * @param id of requested station
     * @return station with requested id
     */
    @RequestMapping(method = RequestMethod.GET, value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Station> getById(@PathVariable String id) {
        logger.info("Requesting station with id {} at time {}.", id, Calendar.getInstance().getTime());
        try {
            return new ResponseEntity<>(stationService.findById(Long.parseLong(id)), HttpStatus.FOUND);
        } catch (NumberFormatException e) {
            throw new ValidationException("Bad format of requested id!", HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            throw new ValidationException("Requested station does not exist!", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param station that needs to be added
     * @return added station
     */
    @RequestMapping(method = RequestMethod.POST, value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Station> addStation(@RequestBody Station station) {
        logger.info("Adding station with at time {}.", Calendar.getInstance().getTime());
        Station newStation = stationService.add(station);
        if( newStation != null){
            return new ResponseEntity<>(newStation, HttpStatus.CREATED);
        }else{
            throw new ValidationException("Station with given name already exist!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param station that needs to be updated
     * @return updated station
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Station> updateStation(@RequestBody Station station) {
        logger.info("Updating station with id {} at time {}.", station.getId(), Calendar.getInstance().getTime());
        return new ResponseEntity<>(stationService.update(station), HttpStatus.OK);
    }

    /**
     * @param station that needs to be deleted
     * @return message about action results
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> deleteStation(@RequestBody Station station) {
        logger.info("Deleting station with id {} at time {}.", station.getId(), Calendar.getInstance().getTime());
        try {
            stationService.remove(station.getId());
            return new ResponseEntity<>("Station successfully deleted!", HttpStatus.OK);
        }catch (EntityNotFoundException e){
            throw new ValidationException("Requested station does not exist!", HttpStatus.NOT_FOUND);
        }
    }


}
