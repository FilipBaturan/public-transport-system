package construction_and_testing.public_transport_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import construction_and_testing.public_transport_system.converter.ScheduleConverter;
import construction_and_testing.public_transport_system.domain.DTO.ScheduleDTO;
import construction_and_testing.public_transport_system.domain.Schedule;
import construction_and_testing.public_transport_system.service.definition.ScheduleService;
import construction_and_testing.public_transport_system.service.definition.TransportLineService;
import org.everit.json.schema.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController extends ValidationController {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TransportLineService transportLineService;

    /**
     * GET /api/schedule
     *
     * @return all available schedules
     */
    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAll() {
        logger.info("Requesting all available schedules at time {}.", Calendar.getInstance().getTime());
        return new ResponseEntity<>(ScheduleConverter.fromEntityList(scheduleService.getAll(), ScheduleDTO::new),
                HttpStatus.OK);
    }

    /**
     * GET /api/schedule/{id}
     *
     * @param id of requested schedule
     * @return schedule with requested id
     */
    @GetMapping("{/id}")
    public ResponseEntity<ScheduleDTO> findById(@PathVariable String id) {
        logger.info("Requesting station with id {} at time {}.", id, Calendar.getInstance().getTime());
        return new ResponseEntity<>(new ScheduleDTO(scheduleService.findById(Long.parseLong(id))), HttpStatus.FOUND);
    }

    /**
     * POST /api/schedule
     *
     * @param schedule that needs to be saved
     * @return saved schedule
     */
    @PostMapping()
    public ResponseEntity<ScheduleDTO> create(@RequestBody String schedule) throws IOException, ValidationException {
        logger.info("Saving schedule at time {}.", Calendar.getInstance().getTime());
        validateJSON(schedule,"schedule.json");
        ObjectMapper mapper = new ObjectMapper();
        Schedule temp = new Schedule(mapper.readValue(schedule, ScheduleDTO.class));
        temp.setTransportLine(transportLineService.findById(temp.getTransportLine().getId()));
        return new ResponseEntity<>(new ScheduleDTO(scheduleService.save(temp))
                , HttpStatus.ACCEPTED);
    }

    /**
     * DELETE /api/schedule/{id}
     *
     * @param schedule that needs to be deleted
     * @return message about action results
     */
    @DeleteMapping("{/id}")
    //@PreAuthorize("")
    public ResponseEntity<String> delete(@RequestBody String schedule) throws IOException, ValidationException {
        logger.info("Deleting station at time {}.", Calendar.getInstance().getTime());
        validateJSON(schedule,"schedule.json");
        ObjectMapper mapper = new ObjectMapper();
        scheduleService.remove((new Schedule(mapper.readValue(schedule, ScheduleDTO.class))).getId());
        return new ResponseEntity<>("Schedule successfully deleted!", HttpStatus.OK);
    }
}
