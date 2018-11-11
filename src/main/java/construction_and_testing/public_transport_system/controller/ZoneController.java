package construction_and_testing.public_transport_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import construction_and_testing.public_transport_system.converter.ZoneConverter;
import construction_and_testing.public_transport_system.domain.DTO.ZoneDTO;
import construction_and_testing.public_transport_system.domain.Zone;
import construction_and_testing.public_transport_system.service.definition.ZoneService;
import org.everit.json.schema.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * API for zones
 */
@RestController
@RequestMapping("/zone")
public class ZoneController extends ValidationController {

    private static final Logger logger = LoggerFactory.getLogger(ZoneController.class);

    @Autowired
    private ZoneService zoneService;

    /**
     * @return all zones
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ZoneDTO>> getAll() {
        logger.info("Requesting all available zones at time {}.", Calendar.getInstance().getTime());
        return new ResponseEntity<>(ZoneConverter.fromEntityList(zoneService.getAll(),ZoneDTO::new), HttpStatus.OK);
    }

    /**
     * @param id of requested zone
     * @return zone with requested id
     */
    @RequestMapping(method = RequestMethod.GET, value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ZoneDTO> getById(@PathVariable String id) {
        logger.info("Requesting zone with id {} at time {}.", id, Calendar.getInstance().getTime());
        return new ResponseEntity<>(new ZoneDTO(zoneService.findById(Long.parseLong(id))), HttpStatus.FOUND);
    }

    /**
     * @param zone that needs to be added
     * @return added zone
     */
    @RequestMapping(method = RequestMethod.POST, value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ZoneDTO> saveZone(@RequestBody String zone) throws IOException, ValidationException {
        logger.info("Saving zone at time {}.", Calendar.getInstance().getTime());
        validateJSON(zone, "zone.json");
        ObjectMapper mapper = new ObjectMapper();
        return new ResponseEntity<>(new ZoneDTO(zoneService.save(new Zone(mapper.readValue(zone,ZoneDTO.class)))),
                HttpStatus.ACCEPTED);
    }

    /**
     * @param zone that needs to be deleted
     * @return message about action results
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> deleteZone(@RequestBody String zone) throws IOException, ValidationException {
        logger.info("Deleting zone at time {}.", Calendar.getInstance().getTime());
        validateJSON(zone, "zone.json");
        ObjectMapper mapper = new ObjectMapper();
        zoneService.remove((new Zone(mapper.readValue(zone,ZoneDTO.class)).getId()));
        return new ResponseEntity<>("Zone successfully deleted!", HttpStatus.OK);
    }
}
