package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.domain.util.GeneralException;
import construction_and_testing.public_transport_system.service.definition.TransportLineService;
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
@RequestMapping("/transportLine")
public class TransportLineController {

    private static final Logger logger = LoggerFactory.getLogger(TransportLineController.class);

    @Autowired
    private TransportLineService transportLineService;

    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransportLine>> getAll() {
        logger.info("Requesting all available transport lines at time {}.", Calendar.getInstance().getTime());
        return new ResponseEntity<>(transportLineService.getAll(), HttpStatus.OK);
    }

    /**
     * @param id of requested transport line
     * @return transport line with requested id
     */
    @RequestMapping(method = RequestMethod.GET, value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<TransportLine> getById(@PathVariable String id) {
        logger.info("Requesting transport line with id {} at time {}.", id, Calendar.getInstance().getTime());
        try {
            return new ResponseEntity<>(transportLineService.findById(Long.parseLong(id)), HttpStatus.FOUND);
        } catch (NumberFormatException e) {
            throw new GeneralException("Bad format of requested id!", HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            throw new GeneralException("Requested transportLine does not exist!", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param transportLine that needs to be added
     * @return added transportLine
     */
    @RequestMapping(method = RequestMethod.POST, value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<TransportLine> addTransportLine(@RequestBody TransportLine transportLine) {
        logger.info("Adding transport line with at time {}.", Calendar.getInstance().getTime());
        TransportLine newTransportLine = transportLineService.add(transportLine);
        if( newTransportLine != null){
            return new ResponseEntity<>(newTransportLine, HttpStatus.CREATED);
        }else{
            throw new GeneralException("TransportLine with given name already exist!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param transportLine that needs to be updated
     * @return updated transportLine
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<TransportLine> updatedTransportLine(@RequestBody TransportLine transportLine) {
        logger.info("Updating transportLine with id {} at time {}.", transportLine.getId(), Calendar.getInstance().getTime());
        return new ResponseEntity<>(transportLineService.update(transportLine), HttpStatus.OK);
    }

    /**
     * @param transportLine that needs to be deleted
     * @return message about action results
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> deleteTransportLine(@RequestBody TransportLine transportLine) {
        logger.info("Deleting transportLine with id {} at time {}.", transportLine.getId(), Calendar.getInstance().getTime());
        try {
            transportLineService.remove(transportLine.getId());
            return new ResponseEntity<>("TransportLine successfully deleted!", HttpStatus.OK);
        }catch (EntityNotFoundException e){
            throw new GeneralException("Requested transportLine does not exist!", HttpStatus.NOT_FOUND);
        }
    }
}
