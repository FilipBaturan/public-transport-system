package construction_and_testing.public_transport_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import construction_and_testing.public_transport_system.domain.DTO.StationDTO;
import construction_and_testing.public_transport_system.domain.Station;
import construction_and_testing.public_transport_system.domain.util.GeneralException;
import construction_and_testing.public_transport_system.service.StationService;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/station")
public class StationController extends ValidationController {

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
        return new ResponseEntity<>(stationService.findById(Long.parseLong(id)), HttpStatus.FOUND);
    }

    /**
     * @param station that needs to be added
     * @return added station
     */
    @RequestMapping(method = RequestMethod.POST, value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Station> saveStation(@RequestBody String station) throws IOException, ValidationException {
        logger.info("Saving station at time {}.", Calendar.getInstance().getTime());
        validateJSON(station,"station.json");
        ObjectMapper mapper = new ObjectMapper();
        return new ResponseEntity<>(stationService.save(new Station(mapper.readValue(station, StationDTO.class))),
                HttpStatus.ACCEPTED);
    }

    /**
     * @param station that needs to be deleted
     * @return message about action results
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> deleteStation(@RequestBody Station station) {
        logger.info("Deleting station with id {} at time {}.", station.getId(), Calendar.getInstance().getTime());
        stationService.remove(station.getId());
        return new ResponseEntity<>("Station successfully deleted!", HttpStatus.OK);
    }

}
