package construction_and_testing.public_transport_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import construction_and_testing.public_transport_system.converter.VehicleConverter;
import construction_and_testing.public_transport_system.domain.DTO.VehicleDTO;
import construction_and_testing.public_transport_system.domain.Vehicle;
import construction_and_testing.public_transport_system.domain.util.GeneralException;
import construction_and_testing.public_transport_system.service.definition.TransportLineService;
import construction_and_testing.public_transport_system.service.definition.VehicleService;
import org.everit.json.schema.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController extends ValidationController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private TransportLineService transportLineService;

    /**
     * @return all available vehicles
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VehicleDTO>> getAll() {
        logger.info("Requesting all available vehicles at time {}.", Calendar.getInstance().getTime());
        return new ResponseEntity<>(VehicleConverter.fromEntityList(vehicleService.getAll(),VehicleDTO::new),
                HttpStatus.OK);
    }

    /**
     * @param id of requested vehicle
     * @return vehicle with requested id
     */
    @RequestMapping(method = RequestMethod.GET, value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<VehicleDTO> getById(@PathVariable String id) {
        logger.info("Requesting vehicle with id {} at time {}.", id, Calendar.getInstance().getTime());
        return new ResponseEntity<>(new VehicleDTO(vehicleService.findById(Long.parseLong(id))), HttpStatus.FOUND);
    }

    /**
     * @param vehicle that needs to be added
     * @return added vehicle
     */
    @RequestMapping(method = RequestMethod.POST, value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<VehicleDTO> saveVehicle(@RequestBody String vehicle) throws IOException, ValidationException {
        logger.info("Adding vehicle with at time {}.", Calendar.getInstance().getTime());
        validateJSON(vehicle, "vehicle.json");
        ObjectMapper mapper = new ObjectMapper();
        Vehicle temp = new Vehicle(mapper.readValue(vehicle,VehicleDTO.class));
        temp.setCurrentLine(transportLineService.findById(temp.getCurrentLine().getId()));
        return new ResponseEntity<>(new VehicleDTO(vehicleService.save(temp)), HttpStatus.OK);
    }

    /**
     * @param vehicle that needs to be deleted
     * @return message about action results
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> deleteVehicle(@RequestBody String vehicle) throws IOException, ValidationException {
        logger.info("Deleting vehicle at time {}.", Calendar.getInstance().getTime());
        validateJSON(vehicle,"vehicle.json");
        ObjectMapper mapper = new ObjectMapper();
        vehicleService.remove((mapper.readValue(vehicle, VehicleDTO.class)).getId());
        return new ResponseEntity<>("Vehicle successfully deleted!", HttpStatus.OK);
    }


}
