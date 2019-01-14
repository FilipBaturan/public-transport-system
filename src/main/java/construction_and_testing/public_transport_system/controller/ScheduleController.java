package construction_and_testing.public_transport_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import construction_and_testing.public_transport_system.converter.ScheduleConverter;
import construction_and_testing.public_transport_system.domain.DTO.ScheduleDTO;
import construction_and_testing.public_transport_system.domain.Schedule;
import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.domain.enums.AuthorityType;
import construction_and_testing.public_transport_system.domain.enums.DayOfWeek;
import construction_and_testing.public_transport_system.service.definition.ScheduleService;
import construction_and_testing.public_transport_system.service.definition.TransportLineService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.everit.json.schema.ValidationException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

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
    //@PreAuthorize("isAuthenticated()")
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
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDTO> findById(@PathVariable("id") String id) {
        logger.info("Requesting schedule with id {} at time {}.", id, Calendar.getInstance().getTime());
        System.out.println(id);
        return new ResponseEntity<>(new ScheduleDTO(scheduleService.findById(Long.parseLong(id))), HttpStatus.FOUND);
    }


    /**
     * GET /api/schedule/findByTransportLineIdAndDayOfWeek/{id}
     *
     * @param id        of a transport lines for which a schedule is requested
     * @param dayOfWeek day of week for which the schedule is requested
     * @return schedule for a transport line with requested id
     */
    @GetMapping("/findByTrLineIdAndDayOfWeek/{id}")
    public ResponseEntity<ScheduleDTO> findByTransportLineIdAndDayOfWeek(@PathVariable Long id, @RequestParam String dayOfWeek) {
        logger.info("Requesting schedule for transprot line with  {} at time {}.", id, Calendar.getInstance().getTime());
        Schedule schedule = scheduleService.findByTransportLineIdAndDayOfWeek(id, DayOfWeek.valueOf(dayOfWeek).ordinal());
        return new ResponseEntity<>(new ScheduleDTO(schedule), HttpStatus.OK);
    }

    /**
     * GET /api/schedule/findByTransportLineId/{id}
     *
     * @param id        of a transport lines for which a schedule is requested
     * @return schedule for a transport line with requested id
     */
    @GetMapping("/findByTransportLineId/{id}")
    public ResponseEntity<List<ScheduleDTO>> findByTransportLineId(@PathVariable Long id) {
        logger.info("Requesting schedule for transprot line with  {} at time {}.", id, Calendar.getInstance().getTime());
        return new ResponseEntity<>(ScheduleConverter.fromEntityList(scheduleService.findByTransportLineId(id), ScheduleDTO::new), HttpStatus.OK);
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
        validateJSON(schedule, "schedule.json");
        ObjectMapper mapper = new ObjectMapper();
        Schedule temp = new Schedule(mapper.readValue(schedule, ScheduleDTO.class));
        temp.setTransportLine(transportLineService.findById(temp.getTransportLine().getId()));
        return new ResponseEntity<>(new ScheduleDTO(scheduleService.save(temp))
                , HttpStatus.ACCEPTED);
    }

    @PutMapping("/updateSchedule")
    ResponseEntity<Boolean> updateSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = null;

        try {
            schedule = this.scheduleService.findById(scheduleDTO.getId());
        } catch (GeneralException ge) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        ModelMapper mapper = new ModelMapper();
        mapper.map(scheduleDTO, schedule);

        try {
            this.scheduleService.save(schedule);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (GeneralException e) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * DELETE /api/schedule/{id}
     *
     * @param schedule that needs to be deleted
     * @return message about action results
     */
    @DeleteMapping("{id}")
    //@PreAuthorize("")
    public ResponseEntity<String> delete(@PathVariable("id") String id) throws IOException, ValidationException {
        logger.info("Deleting station at time {}.", Calendar.getInstance().getTime());
        //validateJSON(id, "schedule.json");
        ObjectMapper mapper = new ObjectMapper();
        scheduleService.remove(Long.parseLong(id));//(new Schedule(mapper.readValue(id, ScheduleDTO.class))).getId());
        return new ResponseEntity<>("Schedule successfully deleted!", HttpStatus.OK);
    }
}
