package construction_and_testing.public_transport_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import construction_and_testing.public_transport_system.converter.TransportLineConverter;
import construction_and_testing.public_transport_system.domain.DTO.TransportLineDTO;
import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.domain.util.GeneralException;
import construction_and_testing.public_transport_system.service.definition.TransportLineService;
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
@RequestMapping("/transportLine")
public class TransportLineController extends ValidationController {

    private static final Logger logger = LoggerFactory.getLogger(TransportLineController.class);

    @Autowired
    private TransportLineService transportLineService;

    /**
     * @return all available transport lines
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransportLineDTO>> getAll() {
        logger.info("Requesting all available transport lines at time {}.", Calendar.getInstance().getTime());
        List<TransportLineDTO> temp = TransportLineConverter.fromEntityList(transportLineService.getAll(),
                TransportLineDTO::new);
        return new ResponseEntity<>(temp, HttpStatus.OK);
    }

    /**
     * @param id of requested transport line
     * @return transport line with requested id
     */
    @RequestMapping(method = RequestMethod.GET, value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<TransportLineDTO> getById(@PathVariable String id) {
        logger.info("Requesting transport line with id {} at time {}.", id, Calendar.getInstance().getTime());
        return new ResponseEntity<>(new TransportLineDTO(transportLineService.findById(Long.parseLong(id))),
                HttpStatus.FOUND);
    }

    /**
     * @param transportLine that needs to be saved
     * @return saved transportLine
     */
    @RequestMapping(method = RequestMethod.POST, value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<TransportLineDTO> saveTransportLine(@RequestBody String transportLine) throws IOException,
            ValidationException {
        logger.info("Saving transport line with at time {}.", Calendar.getInstance().getTime());
        validateJSON(transportLine, "transportLine.json");
        ObjectMapper mapper = new ObjectMapper();
        return new ResponseEntity<>(new TransportLineDTO(transportLineService.save(new
                TransportLine(mapper.readValue(transportLine, TransportLineDTO.class)))), HttpStatus.OK);
    }

    /**
     * @param transportLine that needs to be deleted
     * @return message about action results
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> deleteTransportLine(@RequestBody String transportLine) throws IOException,
            ValidationException {
        logger.info("Deleting transportLine with id at time {}.", Calendar.getInstance().getTime());
        validateJSON(transportLine, "transportLine.json");
        ObjectMapper mapper = new ObjectMapper();
        transportLineService.remove((new TransportLine(mapper.readValue(transportLine,
                TransportLineDTO.class))).getId());
        return new ResponseEntity<>("TransportLine successfully deleted!", HttpStatus.OK);
    }
}
