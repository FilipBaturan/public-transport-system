package construction_and_testing.public_transport_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import construction_and_testing.public_transport_system.converter.ScheduleConverter;
import construction_and_testing.public_transport_system.domain.DTO.ScheduleDTO;
import construction_and_testing.public_transport_system.domain.Schedule;
import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.domain.enums.DayOfWeek;
import construction_and_testing.public_transport_system.service.definition.ScheduleService;
import construction_and_testing.public_transport_system.service.definition.TransportLineService;
import construction_and_testing.public_transport_system.util.GeneralException;
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
        try {
            logger.info("Requesting schedule with id {} at time {}.", id, Calendar.getInstance().getTime());
            return new ResponseEntity<>(new ScheduleDTO(scheduleService.findById(Long.parseLong(id))), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * GET /api/schedule/findByTransportLineIdAndDayOfWeek/{id}&dayOfWeek=:dayOfWeek
     *
     * @param id        of a transport lines for which a schedule is requested
     * @param dayOfWeek day of week for which the schedule is requested
     * @return schedule for a transport line with requested id
     */
    @GetMapping("/findByTrLineIdAndDayOfWeek/{id}")
    public ResponseEntity<ScheduleDTO> findByTransportLineIdAndDayOfWeek(@PathVariable String id, @RequestParam String dayOfWeek) {
        logger.info("Requesting schedule for transprot line with  {} at time {}.", id, Calendar.getInstance().getTime());
        try {
            Schedule schedule = scheduleService.findByTransportLineIdAndDayOfWeek(Long.parseLong(id), DayOfWeek.valueOf(dayOfWeek).ordinal());
            System.out.println(schedule.toString());
            return new ResponseEntity<>(new ScheduleDTO(schedule), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * GET /api/schedule/findByTransportLineId/{id}
     *
     * @param id of a transport lines for which a schedule is requested
     * @return schedule for a transport line with requested id
     */
    @GetMapping("/findByTransportLineId/{id}")
    public ResponseEntity<List<ScheduleDTO>> findByTransportLineId(@PathVariable("id") String id) {
        try {
            logger.info("Requesting schedule for transprot line with  {} at time {}.", id, Calendar.getInstance().getTime());
            return new ResponseEntity<>(ScheduleConverter.fromEntityList(scheduleService.findByTransportLineId(Long.parseLong(id)), ScheduleDTO::new), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * POST /api/schedule
     *
     * @param scheduleDTO that is to be saved
     * @return saved schedule
     */
    @PostMapping
    public ResponseEntity<ScheduleDTO> create(@RequestBody ScheduleDTO scheduleDTO) throws IOException, ValidationException {
        logger.info("Saving schedule at time {}.", Calendar.getInstance().getTime());
        //validateJSON(schedule, "schedule.json");
        try {
            TransportLine transportLine = transportLineService.findById(scheduleDTO.getTransportLine().getId());
            if (transportLine == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Schedule schedule = scheduleService.findScheduleIfExists(new Schedule(scheduleDTO));
            if (schedule == null) {
                /*if (scheduleDTO.getId() == null)
                    return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
                else
                    schedule = new Schedule(scheduleDTO);*/
                schedule = new Schedule(scheduleDTO);
            }

            schedule.setTransportLine(transportLine);
            schedule.setDepartures(scheduleDTO.getDepartures());
            schedule.setActive(true);

            return new ResponseEntity<>(new ScheduleDTO(scheduleService.save(schedule))
                    , HttpStatus.ACCEPTED);
        } catch (GeneralException ge) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * PUT /api/schedule/updateSchedule
     *
     * @param scheduleDTOS schedules that are to be updated
     * @return true if schedules are updated successfully, else false
     */
    @PutMapping("/updateSchedule")
    ResponseEntity<Boolean> updateSchedule(@RequestBody List<ScheduleDTO> scheduleDTOS) {
        for (ScheduleDTO scheduleDTO : scheduleDTOS) {
            Schedule schedule = null;
            try {
                schedule = this.scheduleService.findById(scheduleDTO.getId());
            } catch (GeneralException ge) {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }

            schedule.setDepartures(scheduleDTO.getDepartures());
            schedule.setActive(scheduleDTO.isActive());

            try {
                this.scheduleService.save(schedule);

            } catch (GeneralException e) {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }


    /**
     * DELETE /api/schedule/{id}
     *
     * @param id of a schedule that is to be deleted
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
