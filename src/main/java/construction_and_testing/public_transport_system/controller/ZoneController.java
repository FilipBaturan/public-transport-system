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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * API for zone
 */
@RestController
@RequestMapping("/api/zone")
public class ZoneController extends ValidationController {

    private static final Logger logger = LoggerFactory.getLogger(ZoneController.class);

    @Autowired
    private ZoneService zoneService;

    /**
     * GET /api/zone
     *
     * @return all available zones
     */
    @GetMapping
    public ResponseEntity<List<ZoneDTO>> getAll() {
        logger.info("Requesting all available zones at time {}.", Calendar.getInstance().getTime());
        return new ResponseEntity<>(ZoneConverter.fromEntityList(zoneService.getAll(), e -> ZoneConverter.fromEntity(e)), HttpStatus.OK);
    }

    /**
     * GET /api/zone/{id}
     *
     * @param id of requested zone
     * @return zone with requested id
     */
    @GetMapping("{id}")
    public ResponseEntity<ZoneDTO> findById(@PathVariable String id) {
        logger.info("Requesting zone with id {} at time {}.", id, Calendar.getInstance().getTime());
        return new ResponseEntity<>(new ZoneDTO(zoneService.findById(Long.parseLong(id))), HttpStatus.FOUND);
    }

    /**
     * POST /api/zone
     *
     * @param zone that needs to be added
     * @return added zone
     */
    @PostMapping
    public ResponseEntity<ZoneDTO> save(@RequestBody String zone) throws IOException, ValidationException {
        logger.info("Saving zone at time {}.", Calendar.getInstance().getTime());
        validateJSON(zone, "zone.json");
        ObjectMapper mapper = new ObjectMapper();
        return new ResponseEntity<>(new ZoneDTO(zoneService.save(new Zone(mapper.readValue(zone, ZoneDTO.class)))),
                HttpStatus.OK);
    }

    /**
     * DELETE /api/zone
     *
     * @param zone that needs to be deleted
     * @return message about action results
     */
    @DeleteMapping()
    public ResponseEntity<String> delete(@RequestBody String zone) throws IOException, ValidationException {
        logger.info("Deleting zone at time {}.", Calendar.getInstance().getTime());
        validateJSON(zone, "zone.json");
        ObjectMapper mapper = new ObjectMapper();
        zoneService.remove((new Zone(mapper.readValue(zone, ZoneDTO.class)).getId()));
        return new ResponseEntity<>("Zone successfully deleted!", HttpStatus.OK);
    }
}
