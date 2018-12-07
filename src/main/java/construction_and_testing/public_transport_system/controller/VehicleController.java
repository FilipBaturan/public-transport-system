package construction_and_testing.public_transport_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import construction_and_testing.public_transport_system.converter.VehicleConverter;
import construction_and_testing.public_transport_system.domain.DTO.VehicleDTO;
import construction_and_testing.public_transport_system.domain.Vehicle;
import construction_and_testing.public_transport_system.service.definition.TransportLineService;
import construction_and_testing.public_transport_system.service.definition.VehicleService;
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

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController extends ValidationController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private TransportLineService transportLineService;

    /**
     * GET /api/vehicle
     *
     * @return all available vehicles
     */
    @GetMapping
    public ResponseEntity<List<VehicleDTO>> findAll() {
        logger.info("Requesting all available vehicles at time {}.", Calendar.getInstance().getTime());
        return new ResponseEntity<>(VehicleConverter.fromEntityList(vehicleService.getAll(), VehicleDTO::new),
                HttpStatus.OK);
    }

    /**
     * GET /api/vehicle/{id}
     *
     * @param id of requested vehicle
     * @return vehicle with requested id
     */
    @GetMapping("{id}")
    public ResponseEntity<VehicleDTO> findById(@PathVariable String id) {
        logger.info("Requesting vehicle with id {} at time {}.", id, Calendar.getInstance().getTime());
        return new ResponseEntity<>(new VehicleDTO(vehicleService.findById(Long.parseLong(id))), HttpStatus.FOUND);
    }

    /**
     * POST /api/vehicle
     *
     * @param vehicle that needs to be added
     * @return added vehicle
     */
    @PostMapping()
    public ResponseEntity<VehicleDTO> create(@RequestBody String vehicle) throws IOException, ValidationException {
        logger.info("Adding vehicle with at time {}.", Calendar.getInstance().getTime());
        validateJSON(vehicle, "vehicle.json");
        ObjectMapper mapper = new ObjectMapper();
        Vehicle temp = new Vehicle(mapper.readValue(vehicle, VehicleDTO.class));
        temp.setCurrentLine(transportLineService.findById(temp.getCurrentLine().getId()));
        return new ResponseEntity<>(new VehicleDTO(vehicleService.save(temp)), HttpStatus.OK);
    }

    /**
     * DELETE /api/zone/{id}
     *
     * @param vehicle that needs to be deleted
     * @return message about action results
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@RequestBody String vehicle) throws IOException, ValidationException {
        logger.info("Deleting vehicle at time {}.", Calendar.getInstance().getTime());
        validateJSON(vehicle, "vehicle.json");
        ObjectMapper mapper = new ObjectMapper();
        vehicleService.remove((mapper.readValue(vehicle, VehicleDTO.class)).getId());
        return new ResponseEntity<>("Vehicle successfully deleted!", HttpStatus.OK);
    }


}
