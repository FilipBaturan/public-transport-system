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
    private TransportLinePositionRepository transportLinePositionRepository;

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
            this.placeSchedulesInTransportLine(scheduleRepository.findAllById(transportLine.getSchedule()
                    .stream().map(Schedule::getId).collect(Collectors.toList())), transportLine);
            this.performVehicleConsistency(transportLine);
            transportLineRepository.save(transportLine);
            return transportLinePositionRepository.save(transportLine.getPositions()).getTransportLine();
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
            throw new GeneralException("Requested transport line does not exist!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Gets all schedules
     * Deletes schedules that are not associated with any transport line
     * Creates empty schedules for new transport lines
     * Deletes all transport routes in database
     * Saves new transport lines
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
        try {
            this.validateSchedule(transportLines, schedules, associatedSchedules);
            this.filterAndRemoveUnassociatedSchedules(schedules, associatedSchedules);
            this.associateDefaultZoneToNewTransportLines(transportLines);
            this.filterAssociatedTransportLines(dbTransportLines, transportLines);
            if (!dbTransportLines.isEmpty()) {
                this.removeVehiclesFromTransportLines(dbTransportLines);
                this.removeTicketsFromTransportLines(dbTransportLines);
            }
            transportLineRepository.deleteAll(dbTransportLines);
            return transportLineRepository.saveAll(transportLines);
        } catch (RuntimeException e) { // PersistentObjectException handled
            throw new GeneralException("Transport lines have invalid schedule or position associated!",
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Places schedules to transport line
     *
     * @param schedules     that need to be placed in transport line
     * @param transportLine target transport line
     */
    private void placeSchedulesInTransportLine(Iterable<Schedule> schedules, TransportLine transportLine) {
        for (Schedule schedule : transportLine.getSchedule()) {
            schedules.forEach(s -> {
                if (s.getId().equals(schedule.getId())) {
                    schedule.setId(s.getId());
                    schedule.setDayOfWeek(s.getDayOfWeek());
                    schedule.setDepartures(s.getDepartures());
                    schedule.setActive(s.isActive());
                }
            });
        }
    }

    /**
     * Validate schedule that are associated with transport lines
     *
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
     * Finds schedule with targeted id
     *
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
     * Filters and removes schedule that needs to be removed
     *
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
     * Adds new transport lines to default zone
     *
     * @param transportLines contain new transport lines
     */
    private void associateDefaultZoneToNewTransportLines(Iterable<TransportLine> transportLines) {
        // associate new transport lines with default zone
        @SuppressWarnings("OptionalGetWithoutIsPresent") Zone defaultZone = zoneRepository.findById(1L).get();
        transportLines.forEach(transportLine -> {
            if (transportLine.getZone() == null || transportLine.getZone().getId() == null) {
                transportLine.setZone(defaultZone);
            }
        });
    }

    /**
     * Filters transport lines that needs to be removed
     *
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
     * Sets null to vehicles that are associated with transport lines
     *
     * @param dbTransportLines valid transport lines in database
     */
    private void removeVehiclesFromTransportLines(List<TransportLine> dbTransportLines) {
        List<Vehicle> vehicles = vehicleRepository
                .findByTransportLine(dbTransportLines.stream()
                        .map(TransportLine::getId).collect(Collectors.toList()));
        vehicles.forEach(vehicle -> vehicle.setCurrentLine(null));
        vehicleRepository.saveAll(vehicles);
    }

    /**
     * Removes tickets that are associated with transport line
     *
     * @param dbTransportLines valid transport lines in database
     */
    private void removeTicketsFromTransportLines(List<TransportLine> dbTransportLines) {
        ticketRepository.deleteAll(ticketRepository
                .findByTransportLine(dbTransportLines.stream().map(TransportLine::getId).collect(Collectors.toList())));

    }

    /**
     * Sets null to vehicles that are associated with transport line
     * if type of transport line is changed
     *
     * @param transportLine that is updated
     */
    private void performVehicleConsistency(TransportLine transportLine) {
        List<Vehicle> vehicles = vehicleRepository.findByTransportLine(
                new ArrayList<Long>() {{
                    add(transportLine.getId());
                }});
        if (!vehicles.isEmpty()) {
            if (vehicles.get(0).getType() != transportLine.getType()) {
                vehicles.forEach(vehicle -> vehicle.setCurrentLine(null));
                vehicleRepository.saveAll(vehicles);
            }
        }
    }

    /**
     * Validate transport line properties
     *
     * @param transportLine that needs to be validated
     */
    private void validate(TransportLine transportLine) {
        if (transportLine == null) {
            throw new GeneralException("Invalid transport line", HttpStatus.BAD_REQUEST);
        }
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
     * Validates transport lines properties
     *
     * @param transportLines that need to be validated
     */
    private void validate(Iterable<TransportLine> transportLines) {
        if (transportLines == null) {
            throw new GeneralException("Invalid transport line", HttpStatus.BAD_REQUEST);
        }
        this.validateUnique(transportLines);
        transportLines.forEach(this::validate);
    }

    private void validateUnique(Iterable<TransportLine> transportLines) {
        List<String> names = new ArrayList<>();
        transportLines.forEach(transportLine -> {
            if (names.contains(transportLine.getName())) {
                throw new GeneralException("Transport line with given name already exist!", HttpStatus.BAD_REQUEST);
            }
            names.add(transportLine.getName());
        });
    }
}
