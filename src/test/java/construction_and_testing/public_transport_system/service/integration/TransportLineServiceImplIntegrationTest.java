package construction_and_testing.public_transport_system.service.integration;

import construction_and_testing.public_transport_system.domain.Schedule;
import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.domain.TransportLinePosition;
import construction_and_testing.public_transport_system.domain.Vehicle;
import construction_and_testing.public_transport_system.domain.enums.DayOfWeek;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;
import construction_and_testing.public_transport_system.repository.ScheduleRepository;
import construction_and_testing.public_transport_system.repository.TicketRepository;
import construction_and_testing.public_transport_system.repository.VehicleRepository;
import construction_and_testing.public_transport_system.repository.ZoneRepository;
import construction_and_testing.public_transport_system.service.definition.TransportLineService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static construction_and_testing.public_transport_system.constants.TransportLineConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransportLineServiceImplIntegrationTest {

    @Autowired
    private TransportLineService transportLineService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ZoneRepository zoneRepository;


    /**
     * Test get all stations from database
     */
    @Test
    public void getAll() {
        List<TransportLine> transportLines = transportLineService.getAll();
        assertThat(transportLines).hasSize(DB_COUNT);
    }

    /**
     * Test with valid id
     */
    @Test
    public void findById() {
        TransportLine transportLine = transportLineService.findById(DB_ID);
        assertThat(transportLine).isNotNull();

        assertThat(transportLine.getId()).isEqualTo(DB_ID);
        assertThat(transportLine.getName()).isEqualTo(DB_NAME);
        assertThat(transportLine.getType()).isEqualTo(DB_TYPE);
        assertThat(transportLine.getPositions().getId()).isEqualTo(DB_POSITION.getId());
        assertThat(transportLine.getSchedule().size()).isEqualTo(DB_SCHEDULE_COUNT);
        assertThat(transportLine.getZone().getId()).isEqualTo(DB_ZONE.getId());
        assertThat(transportLine.isActive()).isEqualTo(DB_ACTIVE);
    }

    /**
     * Test with invalid id
     */
    @Test(expected = GeneralException.class)
    public void findByInvalidId() {
        transportLineService.findById(DB_ID_INVALID);

    }

    /**
     * Test with null id
     */
    @Test(expected = GeneralException.class)
    public void findByNullId() {
        transportLineService.findById(null);
    }

    /**
     * Test valid transport line saving
     */
    @Test
    @Transactional
    public void save() {
        TransportLine transportLine =
                new TransportLine(null, NEW_NAME, NEW_TYPE,
                        new TransportLinePosition(null, "45.23,26.24  44.74,36.12 (green|" + NEW_NAME + ")",
                                null, true), new HashSet<>(), DB_ZONE, true);
        transportLine.getPositions().setTransportLine(transportLine);
        int countBefore = transportLineService.getAll().size();

        TransportLine dbTransportLine = transportLineService.save(transportLine);
        assertThat(dbTransportLine).isNotNull();

        assertThat(transportLineService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbTransportLine.getName()).isEqualTo(transportLine.getName());
        assertThat(dbTransportLine.getType()).isEqualTo(transportLine.getType());
        assertThat(dbTransportLine.getPositions().getId()).isNotNull();
        assertThat(dbTransportLine.getPositions().getContent().contains(NEW_NAME)).isTrue();
        assertThat(dbTransportLine.getSchedule().size()).isEqualTo(0);
        assertThat(dbTransportLine.getZone().getId()).isEqualTo(DB_ZONE.getId());
    }

    /**
     * Test null saving
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void saveNull() {
        transportLineService.save(null);
    }

    /**
     * Test valid transport line saving with updated
     * Vehicles associated with transport line should be updated
     * with null current line
     */
    @Test
    @Transactional
    public void saveTransportLineWithUpdatedType() {
        TransportLine transportLine =
                new TransportLine(DB_ID, DB_NAME, VehicleType.METRO,
                        DB_POSITION, new HashSet<>(), DB_ZONE, true);
        transportLine.getPositions().setTransportLine(transportLine);

        Schedule schedule1 = new Schedule(100L, transportLine, DayOfWeek.WORKDAY, new ArrayList<String>() {{
            add("08:00");
            add("08:15");
            add("8:30");
            add("08:45");
            add("09:00");
        }}, true);

        Schedule schedule2 = new Schedule(103L, transportLine, DayOfWeek.SATURDAY, new ArrayList<String>() {{
            add("18:00");
            add("18:15");
            add("18:30");
            add("18:45");
            add("19:00");
        }}, true);

        Schedule schedule3 = new Schedule(105L, transportLine, DayOfWeek.SUNDAY, new ArrayList<String>() {{
            add("22:00");
            add("22:15");
            add("22:30");
            add("22:45");
            add("23:00");
        }}, true);

        transportLine.getSchedule().add(schedule1);
        transportLine.getSchedule().add(schedule2);
        transportLine.getSchedule().add(schedule3);

        int countBefore = transportLineService.getAll().size();

        TransportLine dbTransportLine = transportLineService.save(transportLine);
        assertThat(dbTransportLine).isNotNull();

        List<Vehicle> vehicles = vehicleRepository.findAll();

        assertThat(transportLineService.getAll().size()).isEqualTo(countBefore);
        assertThat(dbTransportLine.getName()).isEqualTo(transportLine.getName());
        assertThat(dbTransportLine.getType()).isEqualTo(VehicleType.METRO);
        assertThat(dbTransportLine.getPositions().getId()).isNotNull();
        assertThat(dbTransportLine.getPositions().getContent()).isEqualTo(transportLine.getPositions().getContent());
        assertThat(dbTransportLine.getSchedule().size()).isEqualTo(transportLine.getSchedule().size());
        assertThat(dbTransportLine.getZone().getId()).isEqualTo(transportLine.getZone().getId());

        vehicles.forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNull());
    }

    /**
     * Test with null values
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void saveWithNullValues() {
        TransportLine transportLine =
                new TransportLine(null, null, null, null,
                        null, null, true);
        transportLineService.save(transportLine);
    }

    /**
     * Test transport line position has long id instead of null
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void saveWithInvalidFormat() {
        TransportLine transportLine =
                new TransportLine(null, NEW_NAME, NEW_TYPE,
                        new TransportLinePosition(22L, "", null, true),
                        new HashSet<>(), DB_ZONE, true);
        transportLineService.save(transportLine);
    }

    /**
     * Test transport line with not unique name
     */
    @Test(expected = GeneralException.class)
    public void saveWithInvalidName() {
        TransportLine transportLine =
                new TransportLine(null, DB_NAME, NEW_TYPE, NEW_POSITION, new HashSet<>(),
                        DB_ZONE, true);
        transportLineService.save(transportLine);
    }

    /**
     * Test with to short name value
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void saveWithShortName() {
        TransportLine transportLine =
                new TransportLine(null, NEW_NAME_SHORT_LENGTH, NEW_TYPE,
                        new TransportLinePosition(null, "", null, true),
                        new HashSet<>(), DB_ZONE, true);
        transportLine.getPositions().setTransportLine(transportLine);
        transportLineService.save(transportLine);
    }

    /**
     * Test with too long name value
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void saveWithLongName() {
        TransportLine transportLine =
                new TransportLine(null, NEW_NAME_LONG_LENGTH, NEW_TYPE,
                        new TransportLinePosition(null, "", null, true),
                        new HashSet<>(), DB_ZONE, true);
        transportLine.getPositions().setTransportLine(transportLine);
        transportLineService.save(transportLine);
    }

    /**
     * Test with min length name value
     */
    @Test
    @Transactional
    public void saveWithMinLengthName() {
        TransportLine transportLine =
                new TransportLine(null, NEW_NAME_MIN_LENGTH, NEW_TYPE, new TransportLinePosition(null,
                        "45.23,26.24  44.74,36.12 (green|" + NEW_NAME_MIN_LENGTH + ")", null,
                        true), new HashSet<>(), DB_ZONE, true);
        transportLine.getPositions().setTransportLine(transportLine);
        int countBefore = transportLineService.getAll().size();

        TransportLine dbTransportLine = transportLineService.save(transportLine);
        assertThat(dbTransportLine).isNotNull();

        assertThat(transportLineService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbTransportLine.getName()).isEqualTo(transportLine.getName());
        assertThat(dbTransportLine.getType()).isEqualTo(transportLine.getType());
        assertThat(dbTransportLine.getPositions().getId()).isNotNull();
        assertThat(dbTransportLine.getPositions().getContent().contains(NEW_NAME_MIN_LENGTH)).isTrue();
        assertThat(dbTransportLine.getSchedule().size()).isEqualTo(0);
        assertThat(dbTransportLine.getZone().getId()).isEqualTo(DB_ZONE.getId());
    }

    /**
     * Test with max length name value
     */
    @Test
    @Transactional
    public void saveWithMaxLengthName() {
        TransportLine transportLine =
                new TransportLine(null, NEW_NAME_MAX_LENGTH, NEW_TYPE, new TransportLinePosition(null,
                        "45.23,26.24  44.74,36.12 (green|" + NEW_NAME_MAX_LENGTH + ")", null,
                        true), new HashSet<>(), DB_ZONE, true);
        transportLine.getPositions().setTransportLine(transportLine);
        int countBefore = transportLineService.getAll().size();

        TransportLine dbTransportLine = transportLineService.save(transportLine);
        assertThat(dbTransportLine).isNotNull();

        assertThat(transportLineService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbTransportLine.getName()).isEqualTo(transportLine.getName());
        assertThat(dbTransportLine.getType()).isEqualTo(transportLine.getType());
        assertThat(dbTransportLine.getPositions().getId()).isNotNull();
        assertThat(dbTransportLine.getPositions().getContent().contains(NEW_NAME_MAX_LENGTH)).isTrue();
        assertThat(dbTransportLine.getSchedule().size()).isEqualTo(0);
        assertThat(dbTransportLine.getZone().getId()).isEqualTo(DB_ZONE.getId());
    }

    /**
     * Test valid transport line deletion
     */
    @Test
    @Transactional
    public void remove() {
        int countBefore = transportLineService.getAll().size();
        transportLineService.remove(DEL_ID);
        TransportLine transportLine = transportLineService.findById(DEL_ID);
        assertThat(transportLine.isActive()).isFalse();
        assertThat(transportLineService.getAll().size()).isEqualTo(countBefore - 1);
    }

    /**
     * Test transport line deletion that does not exist in database
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void removeInvalidId() {
        transportLineService.remove(DEL_ID_INVALID);
    }


    /**
     * Test valid replacement
     */
    @Test
    @Transactional
    public void replaceAll() {
        int countBeforeSchedule = scheduleRepository.findAll().size();
        int countBeforeTicket = ticketRepository.findAll().size();

        List<TransportLine> transportLines = transportLineService.replaceAll(NEW_TRANSPORT_LINES);

        assertThat(transportLines).isNotNull();
        assertThat(transportLines.size()).isEqualTo(NEW_TRANSPORT_LINES.size());
        transportLines.forEach(transportLine -> {
            assertThat(transportLine.getId()).isNotNull();
            assertThat(transportLine.getName()).isIn(NEW_TRANSPORT_LINES
                    .stream().map(TransportLine::getName).collect(Collectors.toList()));
        });
        assertThat(scheduleRepository.findAll().size()).isEqualTo(countBeforeSchedule - DEL_SCHEDULE_COUNT);
        vehicleRepository.findAll().forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNotNull());
        assertThat(ticketRepository.findAll().size()).isEqualTo(countBeforeTicket);
    }

    /**
     * Test replacement when default zone does not exist
     */
    @Test(expected = DataIntegrityViolationException.class)
    @Transactional
    public void replaceAllNoDefaultZone() {
        zoneRepository.deleteById(1L);
        transportLineService.replaceAll(NEW_TRANSPORT_LINES);
    }

    /**
     * Test with schedule associated to wrong transport line
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void replaceAllWithInvalidScheduleAssociation() {
        transportLineService.replaceAll(NEW_TRANSPORT_LINES_INVALID_SCHEDULE_ASSOCIATION);
    }

    /**
     * Test replacement transport lines with no zone
     */
    @Test
    @Transactional
    public void replaceAllWithNoZone() {
        List<TransportLine> transportLines = transportLineService.replaceAll(NEW_TRANSPORT_LINES_NO_ZONE);

        assertThat(transportLines).isNotNull();
        assertThat(transportLines.size()).isEqualTo(NEW_TRANSPORT_LINES_NO_ZONE.size());
        transportLines.forEach(transportLine -> assertThat(transportLine.getZone().getId()).isEqualTo(DEFAULT_ZONE_ID));
        assertThat(scheduleRepository.findAll()).isEmpty();
        vehicleRepository.findAll().forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNull());
        assertThat(ticketRepository.findAll().size()).isEqualTo(0);
    }

    /**
     * Test replacement transport lines with null schedule
     */
    @Test
    @Transactional
    public void replaceAllWithNullSchedule() {
        List<TransportLine> transportLines = transportLineService.replaceAll(NEW_TRANSPORT_LINES_NO_SCHEDULE);

        assertThat(transportLines).isNotNull();
        assertThat(transportLines.size()).isEqualTo(NEW_TRANSPORT_LINES_NO_SCHEDULE.size());
        assertThat(scheduleRepository.findAll()).isEmpty();
        transportLines.forEach(transportLine -> assertThat(transportLine.getSchedule().size()).isEqualTo(0));
        vehicleRepository.findAll().forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNull());
        assertThat(ticketRepository.findAll().size()).isEqualTo(0);
    }

    /**
     * Test null replacing
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void replaceAllNull() {
        transportLineService.replaceAll(null);
    }

    /**
     * Test replacement transport lines with duplicate name
     */
    @Test(expected = GeneralException.class)
    public void replaceAllWithNotUniqueName() {
        transportLineService.replaceAll(NEW_TRANSPORT_LINES_NOT_UNIQUE_NAME);
    }

    /**
     * Test replacement with schedule that does not exist in database
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void replaceAllWithInvalidSchedule() {
        transportLineService.replaceAll(NEW_TRANSPORT_LINES_INVALID_SCHEDULE);
    }

    /**
     * Test with too short name value
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void replaceAllWithShortName() {
        NEW_TRANSPORT_LINES.get(0).setName(NEW_NAME_SHORT_LENGTH);
        transportLineService.replaceAll(NEW_TRANSPORT_LINES);
    }

    /**
     * Test with too long name value
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void replaceAllWithLongName() {
        NEW_TRANSPORT_LINES.get(0).setName(NEW_NAME_LONG_LENGTH);
        transportLineService.replaceAll(NEW_TRANSPORT_LINES);
    }

    /**
     * Test with min length name value
     */
    @Test
    @Transactional
    public void replaceAllWithMinLengthName() {
        int countBeforeSchedule = scheduleRepository.findAll().size();
        int countBeforeTicket = ticketRepository.findAll().size();

        NEW_TRANSPORT_LINES.get(0).setName(NEW_NAME_MIN_LENGTH);
        List<TransportLine> transportLines = transportLineService.replaceAll(NEW_TRANSPORT_LINES);

        assertThat(transportLines).isNotNull();
        assertThat(transportLines.size()).isEqualTo(NEW_TRANSPORT_LINES.size());
        assertThat(scheduleRepository.findAll().size()).isEqualTo(countBeforeSchedule - DEL_SCHEDULE_COUNT);
        vehicleRepository.findAll().forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNotNull());
        assertThat(ticketRepository.findAll().size()).isEqualTo(countBeforeTicket);
    }

    /**
     * Test with max length name value
     */
    @Test
    @Transactional
    public void replaceAllWithMaxLengthName() {
        int countBeforeSchedule = scheduleRepository.findAll().size();
        int countBeforeTicket = ticketRepository.findAll().size();

        NEW_TRANSPORT_LINES.get(0).setName(NEW_NAME_MAX_LENGTH);
        List<TransportLine> transportLines = transportLineService.replaceAll(NEW_TRANSPORT_LINES);

        assertThat(transportLines).isNotNull();
        assertThat(transportLines.size()).isEqualTo(NEW_TRANSPORT_LINES.size());
        assertThat(scheduleRepository.findAll().size()).isEqualTo(countBeforeSchedule - DEL_SCHEDULE_COUNT);
        vehicleRepository.findAll().forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNotNull());
        assertThat(ticketRepository.findAll().size()).isEqualTo(countBeforeTicket);
    }
}