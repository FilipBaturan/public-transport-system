package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.domain.Vehicle;
import construction_and_testing.public_transport_system.domain.util.GeneralException;
import construction_and_testing.public_transport_system.service.definition.VehicleService;
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
@RequestMapping("/vehicle")
public class VehicleController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private VehicleService vehicleService;

    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Vehicle>> getAll() {
        logger.info("Requesting all available zones at time {}.", Calendar.getInstance().getTime());
        return new ResponseEntity<>(vehicleService.getAll(), HttpStatus.OK);
    }


    /**
     * @param id of requested vehicle
     * @return vehicle with requested id
     */
    @RequestMapping(method = RequestMethod.GET, value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Vehicle> getById(@PathVariable String id) {
        logger.info("Requesting vehicle with id {} at time {}.", id, Calendar.getInstance().getTime());
        try {
            return new ResponseEntity<>(vehicleService.findById(Long.parseLong(id)), HttpStatus.FOUND);
        } catch (NumberFormatException e) {
            throw new GeneralException("Bad format of requested id!", HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            throw new GeneralException("Requested vehicle does not exist!", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param vehicle that needs to be added
     * @return added vehicle
     */
    @RequestMapping(method = RequestMethod.POST, value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle) {
        logger.info("Adding vehicle with at time {}.", Calendar.getInstance().getTime());
        Vehicle newVehicle = vehicleService.add(vehicle);
        if( newVehicle != null){
            return new ResponseEntity<>(newVehicle, HttpStatus.CREATED);
        }else{
            throw new GeneralException("Vehicle with given name already exist!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param vehicle that needs to be updated
     * @return updated Vehicle
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody Vehicle vehicle) {
        logger.info("Updating vehicle with id {} at time {}.", vehicle.getId(), Calendar.getInstance().getTime());
        return new ResponseEntity<>(vehicleService.update(vehicle), HttpStatus.OK);
    }

    /**
     * @param vehicle that needs to be deleted
     * @return message about action results
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> deleteVehicle(@RequestBody Vehicle vehicle) {
        logger.info("Deleting vehicle with id {} at time {}.", vehicle.getId(), Calendar.getInstance().getTime());
        try {
            vehicleService.remove(vehicle.getId());
            return new ResponseEntity<>("Vehicle successfully deleted!", HttpStatus.OK);
        }catch (EntityNotFoundException e){
            throw new GeneralException("Requested vehicle does not exist!", HttpStatus.NOT_FOUND);
        }
    }


}
