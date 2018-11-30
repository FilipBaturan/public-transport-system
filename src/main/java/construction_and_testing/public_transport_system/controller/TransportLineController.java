package construction_and_testing.public_transport_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import construction_and_testing.public_transport_system.converter.TransportLineConverter;
import construction_and_testing.public_transport_system.domain.DTO.TransportLineColletionDTO;
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
@RequestMapping("/api/transportLine")
public class TransportLineController extends ValidationController {

    private static final Logger logger = LoggerFactory.getLogger(TransportLineController.class);

    @Autowired
    private TransportLineService transportLineService;

    /**
     * GET /api/transportLine
     *
     * @return all available transport lines
     */
    @GetMapping
    public ResponseEntity<List<TransportLineDTO>> findAll() {
        logger.info("Requesting all available transport lines at time {}.", Calendar.getInstance().getTime());
        return new ResponseEntity<>(TransportLineConverter.fromEntityList(transportLineService.getAll(),
                TransportLineDTO::new), HttpStatus.OK);
    }

    /**
     * GET /api/transportLine/{id}
     *
     * @param id of requested transport line
     * @return transport line with requested id
     */
    @GetMapping("{/id}")
    public ResponseEntity<TransportLineDTO> findById(@PathVariable String id) {
        logger.info("Requesting transport line with id {} at time {}.", id, Calendar.getInstance().getTime());
        return new ResponseEntity<>(new TransportLineDTO(transportLineService.findById(Long.parseLong(id))),
                HttpStatus.FOUND);
    }

    /**
     * POST /api/transportLine
     *
     * @param transportLine that needs to be saved
     * @return saved transportLine
     */
    @PostMapping()
    public ResponseEntity<TransportLineDTO> create(@RequestBody String transportLine) throws IOException,
            ValidationException {
        logger.info("Saving transport line with at time {}.", Calendar.getInstance().getTime());
        validateJSON(transportLine, "transportLine.json");
        ObjectMapper mapper = new ObjectMapper();
        return new ResponseEntity<>(new TransportLineDTO(transportLineService.save(new
                TransportLine(mapper.readValue(transportLine, TransportLineDTO.class)))), HttpStatus.OK);
    }

    /**
     * POST /api/transportLine/replace
     *
     * @param transportLines that need to be added
     * @return added transport lines
     */
    @PostMapping("/replace")
    public ResponseEntity<List<TransportLineDTO>> replaceAll(@RequestBody String transportLines) throws
            IOException, ValidationException {
        logger.info("Replacing all transport lines at time {}.", Calendar.getInstance().getTime());
        validateJSON(transportLines, "transportLineCollection.json");
        ObjectMapper mapper = new ObjectMapper();
        return new ResponseEntity<>(TransportLineConverter.fromEntityList(transportLineService
                .replaceAll(TransportLineConverter.toEntityList(
                        mapper.readValue(transportLines, TransportLineColletionDTO.class).getTransportLines(),
                        TransportLine::new)), TransportLineDTO::new), HttpStatus.ACCEPTED);
    }

    /**
     * DELETE /api/transportLine/{id}
     *
     * @param transportLine that needs to be deleted
     * @return message about action results
     */
    @DeleteMapping("{/id}")
    public ResponseEntity<String> delete(@RequestBody String transportLine) throws IOException,
            ValidationException {
        logger.info("Deleting transportLine with id at time {}.", Calendar.getInstance().getTime());
        validateJSON(transportLine, "transportLineCollection.json");
        ObjectMapper mapper = new ObjectMapper();
        transportLineService.remove((new TransportLine(mapper.readValue(transportLine,
                TransportLineDTO.class))).getId());
        return new ResponseEntity<>("TransportLine successfully deleted!", HttpStatus.OK);
    }
}
