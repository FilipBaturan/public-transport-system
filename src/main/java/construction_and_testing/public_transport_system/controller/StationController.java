package construction_and_testing.public_transport_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import construction_and_testing.public_transport_system.converter.StationConverter;
import construction_and_testing.public_transport_system.domain.DTO.StationDTO;
import construction_and_testing.public_transport_system.domain.Station;
import construction_and_testing.public_transport_system.service.definition.StationService;
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

@RestController
@RequestMapping("/api/station")
public class StationController extends ValidationController {

    private static final Logger logger = LoggerFactory.getLogger(StationController.class);

    @Autowired
    private StationService stationService;

    /**
     * GET /rest/station
     *
     * @return all available stations
     */
    @GetMapping
    public ResponseEntity<List<StationDTO>> getAll() {
        logger.info("Requesting all available stations at time {}.", Calendar.getInstance().getTime());
        return new ResponseEntity<>(StationConverter.fromEntityList(stationService.getAll(), StationDTO::new),
                HttpStatus.OK);
    }

    /**
     * GET /rest/station/{id}
     *
     * @param id of requested station
     * @return station with requested id
     */
    @GetMapping("{/id}")
    public ResponseEntity<StationDTO> findById(@PathVariable String id) {
        logger.info("Requesting station with id {} at time {}.", id, Calendar.getInstance().getTime());
        return new ResponseEntity<>(new StationDTO(stationService.findById(Long.parseLong(id))), HttpStatus.FOUND);
    }

    /**
     * POST /api/station
     *
     * @param station that needs to be added
     * @return added station
     */
    @PostMapping
    public ResponseEntity<StationDTO> create (@RequestBody String station) throws IOException, ValidationException {
        logger.info("Saving station at time {}.", Calendar.getInstance().getTime());
        validateJSON(station,"station.json");
        ObjectMapper mapper = new ObjectMapper();
        return new ResponseEntity<>(new StationDTO(stationService.save(new Station(mapper.readValue(station,
                StationDTO.class)))), HttpStatus.ACCEPTED);
    }

    /**
     * DELETE /api/station/{id}
     *
     * @param station that needs to be deleted
     * @return message about action results
     */
    @DeleteMapping("{/id}")
    public ResponseEntity<String> delete (@RequestBody String station) throws IOException, ValidationException {
        logger.info("Deleting station at time {}.", Calendar.getInstance().getTime());
        validateJSON(station,"station.json");
        ObjectMapper mapper = new ObjectMapper();
        stationService.remove((new Station(mapper.readValue(station, StationDTO.class))).getId());
        return new ResponseEntity<>("Station successfully deleted!", HttpStatus.OK);
    }

}
