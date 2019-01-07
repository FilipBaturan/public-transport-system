package construction_and_testing.public_transport_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import construction_and_testing.public_transport_system.converter.StationConverter;
import construction_and_testing.public_transport_system.domain.DTO.StationCollectionDTO;
import construction_and_testing.public_transport_system.domain.DTO.StationDTO;
import construction_and_testing.public_transport_system.domain.Station;
import construction_and_testing.public_transport_system.service.definition.StationService;
import org.everit.json.schema.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * API for station
 */
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
    @GetMapping("{id}")
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
    @PreAuthorize("hasAuthority('OPERATER')")
    public ResponseEntity<StationDTO> save(@RequestBody String station) throws IOException, ValidationException {
        logger.info("Saving station at time {}.", Calendar.getInstance().getTime());
        validateJSON(station, "station.json");
        ObjectMapper mapper = new ObjectMapper();
        Station temp = new Station(mapper.readValue(station, StationDTO.class));
        temp.getPosition().setStation(temp);
        return new ResponseEntity<>(new StationDTO(stationService.save(temp)), HttpStatus.OK);
    }

    /**
     * POST /api/station/replace
     *
     * @param stations that need to be added
     * @return added station
     */
    @PostMapping("/replace")
    @PreAuthorize("hasAuthority('OPERATER')")
    public ResponseEntity<List<StationDTO>> replaceAll(@RequestBody String stations) throws
            IOException, ValidationException {
        logger.info("Replacing all stations at time {}.", Calendar.getInstance().getTime());
        validateJSON(stations, "stationCollection.json");
        ObjectMapper mapper = new ObjectMapper();
        return new ResponseEntity<>(StationConverter.fromEntityList(stationService
                .replaceAll(StationConverter.toEntityList(mapper.readValue(stations, StationCollectionDTO.class)
                        .getStations(), Station::new)), StationDTO::new), HttpStatus.OK);
    }

    /**
     * DELETE /api/station
     *
     * @param id of station that needs to be deleted
     * @return message about action results
     */
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('OPERATER')")
    public ResponseEntity<String> delete(@PathVariable String id) throws ValidationException {
        logger.info("Deleting station at time {}.", Calendar.getInstance().getTime());
        stationService.remove(Long.parseLong(id));
        return new ResponseEntity<>("Station successfully deleted!", HttpStatus.OK);
    }

}
