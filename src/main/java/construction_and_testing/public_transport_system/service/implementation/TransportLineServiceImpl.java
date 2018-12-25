package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.Schedule;
import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.domain.Vehicle;
import construction_and_testing.public_transport_system.domain.Zone;
import construction_and_testing.public_transport_system.repository.*;
import construction_and_testing.public_transport_system.service.definition.TransportLineService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransportLineServiceImpl implements TransportLineService {

    @Autowired
    private TransportLineRepository transportLineRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public List<TransportLine> getAll() {
        return transportLineRepository.findAll();
    }

    @Override
    public TransportLine findById(Long id) {
        try {
            return transportLineRepository.findById(id).orElseThrow(() ->
                    new GeneralException("Requested transport line does not exist!", HttpStatus.BAD_REQUEST));
        } catch (InvalidDataAccessApiUsageException e) {
            throw new GeneralException("Invalid id!", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public TransportLine save(TransportLine transportLine) {
        try {
            this.validate(transportLine);
            return transportLineRepository.save(transportLine);
        } catch (DataIntegrityViolationException e) {
            throw new GeneralException("Transport line with given name already exist!", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            throw new GeneralException("Transport lines have invalid schedule or position associated!",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void remove(Long id) {
        Optional<TransportLine> entity = transportLineRepository.findById(id);
        if (entity.isPresent()) {
            TransportLine transportLine = entity.get();
            transportLine.setActive(false);
            transportLineRepository.save(transportLine);
        } else {
            throw new GeneralException("Transport line with id:" + id + " does not exist!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * get all schedules
     * delete schedules that are not associated with any transport line
     * creates empty schedules for new transport lines
     * delete all transport routes in database
     * save new transport lines
     *
     * @param transportLines that need to be saved
     * @return save transport lines
     */
    @Override
    @Transactional
    public List<TransportLine> replaceAll(Iterable<TransportLine> transportLines) {
        this.validate(transportLines);
        List<Schedule> associatedSchedules = new ArrayList<>();
        List<Schedule> schedules = scheduleRepository.findAll(); // all schedules in database
        List<TransportLine> dbTransportLines = transportLineRepository.findAll(); // all transport lines in database

        this.validateSchedule(transportLines, schedules, associatedSchedules);
        this.filterAndRemoveUnassociatedSchedules(schedules, associatedSchedules);
        this.associateDefaultZoneToNewTransportLines(transportLines);
        this.filterAssociatedTransportLines(dbTransportLines, transportLines);
        if (!dbTransportLines.isEmpty()) {
            this.removeStationsFromTransportLines(dbTransportLines);
            this.removeTicketsFromTransportLines(dbTransportLines);
        }


        try {
            transportLineRepository.deleteAll(dbTransportLines);
            return transportLineRepository.saveAll(transportLines);
        } catch (DataIntegrityViolationException e) {
            throw new GeneralException("Transport line with given name already exist!", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) { // PersistentObjectException handled
            throw new GeneralException("Transport lines have invalid schedule or position associated!",
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param transportLines      that contain schedules that need to be validated
     * @param schedules           valid schedules in database
     * @param associatedSchedules valid schedules that are associated to transport lines
     */
    private void validateSchedule(Iterable<TransportLine> transportLines, List<Schedule> schedules,
                                  List<Schedule> associatedSchedules) {
        transportLines.forEach(transportLine -> {
            if (transportLine.getSchedule() == null) {
                transportLine.setSchedule(new HashSet<>());
            } else {
                transportLine.getSchedule().forEach(schedule -> {
                    Schedule found = this.findScheduleById(schedules, schedule.getId());
                    // corrupted schedule associated to transport line
                    if (schedule.getId() != null && found == null) { // schedule does not exist in database
                        throw new GeneralException("Schedule associated to transport line "
                                + transportLine.getName() + " does not exist!", HttpStatus.BAD_REQUEST);
                    } else if (schedule.getId() != null &&
                            !found.getTransportLine().getId().equals(transportLine.getId())) {
                        //  schedule associate to wrong transport line
                        throw new GeneralException("Schedule has already associated to another transport line!",
                                HttpStatus.BAD_REQUEST);
                    } else if (schedule.getId() != null) { // valid schedule associated to transport line
                        if (found != null) {
                            // replace item in transportLine
                            for (Schedule tempSchedule : transportLine.getSchedule()) {
                                if (tempSchedule.getId().equals(found.getId())) {
                                    tempSchedule.setId(found.getId());
                                    tempSchedule.setTransportLine(found.getTransportLine());
                                    tempSchedule.setDepartures(found.getDepartures());
                                    tempSchedule.setDayOfWeek(found.getDayOfWeek());
                                    tempSchedule.setActive(found.isActive());
                                    break;
                                }
                            }
                        }
                        associatedSchedules.add(schedule);
                    }
                });
            }

        });
    }

    /**
     * @param schedules which search is performed
     * @param id        of target schedule
     * @return founded schedule or null if does not exist in collection
     */
    private Schedule findScheduleById(Iterable<Schedule> schedules, Long id) {
        for (Schedule schedule : schedules) {
            if (schedule.getId().equals(id)) {
                return schedule;
            }
        }
        return null;
    }

    /**
     * @param schedules           valid schedules in database
     * @param associatedSchedules with transport lines
     */
    private void filterAndRemoveUnassociatedSchedules(List<Schedule> schedules, List<Schedule> associatedSchedules) {
        if (!schedules.isEmpty()) {
            // remove elements that are in persistentSchedules collection
            schedules.removeIf(schedule -> associatedSchedules.stream()
                    .anyMatch(schedule1 -> schedule1.getId().equals(schedule.getId())));
            // schedules now contains items for deletion
            scheduleRepository.deleteAll(schedules);
        }
    }

    /**
     * @param transportLines contains new transport lines
     */
    private void associateDefaultZoneToNewTransportLines(Iterable<TransportLine> transportLines) {
        // associate new transport lines with default zone
        Zone defaultZone = zoneRepository.findById(1L).orElseThrow(
                () -> new GeneralException("Default zone does not exist!", HttpStatus.INTERNAL_SERVER_ERROR));
        transportLines.forEach(transportLine -> {
            if (transportLine.getZone() == null || transportLine.getZone().getId() == null) {
                transportLine.setZone(defaultZone);
            }
        });
    }

    /**
     * @param dbTransportLines valid transport lines in database
     * @param transportLines   that need to be saved
     */
    private void filterAssociatedTransportLines(List<TransportLine> dbTransportLines,
                                                Iterable<TransportLine> transportLines) {
        // remove items that exist in transportLine and
        // then dbTransportLines contains items for deletion
        dbTransportLines.removeIf(transportLine -> {
            boolean exist = false;
            for (TransportLine transportLine1 : transportLines) {
                if (transportLine.getId().equals(transportLine1.getId())) {
                    exist = true;
                    break;
                }
            }
            return exist;
        });
    }

    /**
     * @param dbTransportLines valid transport lines in database
     */
    private void removeStationsFromTransportLines(List<TransportLine> dbTransportLines) {
        List<Vehicle> vehicles = vehicleRepository
                .findByTransportLine(dbTransportLines.stream().map(TransportLine::getId).collect(Collectors.toList()));
        vehicles.forEach(vehicle -> vehicle.setCurrentLine(null));
        vehicleRepository.saveAll(vehicles);
    }

    /**
     * @param dbTransportLines valid transport lines in database
     */
    private void removeTicketsFromTransportLines(List<TransportLine> dbTransportLines) {
        ticketRepository.deleteAll(ticketRepository
                .findByTransportLine(dbTransportLines.stream().map(TransportLine::getId).collect(Collectors.toList())));

    }

    /**
     * @param transportLine that needs to be validated
     */
    private void validate(TransportLine transportLine) {
        Set<ConstraintViolation<TransportLine>> violations = Validation.buildDefaultValidatorFactory()
                .getValidator().validate(transportLine);
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("Name ");
            for (ConstraintViolation<TransportLine> violation : violations) {
                builder.append(violation.getMessage());
                builder.append("\n");
            }
            throw new GeneralException(builder.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param transportLines that need to be validated
     */
    private void validate(Iterable<TransportLine> transportLines) {
        transportLines.forEach(this::validate);
    }
}
