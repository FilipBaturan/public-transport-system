package construction_and_testing.public_transport_system.service.unit;

import construction_and_testing.public_transport_system.domain.*;
import construction_and_testing.public_transport_system.domain.enums.DayOfWeek;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;
import construction_and_testing.public_transport_system.repository.*;
import construction_and_testing.public_transport_system.service.definition.TransportLineService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static construction_and_testing.public_transport_system.constants.TransportLineConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransportLineServiceImplUnitTest {

    @MockBean
    private TransportLineRepository transportLineRepository;

    @MockBean
    private TransportLinePositionRepository transportLinePositionRepository;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @MockBean
    private ZoneRepository zoneRepository;

    @MockBean
    private VehicleRepository vehicleRepository;

    @MockBean
    private TicketRepository ticketRepository;

    @Autowired
    private TransportLineService transportLineService;

    private boolean negativeTest = false;

    private boolean updateTest = false;

    private List<Schedule> schedules = DB_SCHEDULES;

    private List<Ticket> tickets = DB_TICKETS;

    @Before
    public void setUp() {
        this.negativeTest = false;
        this.updateTest = false;
        this.schedules = DB_SCHEDULES.stream().map(Schedule::new).collect(Collectors.toList());
        this.tickets = DB_TICKETS.stream().map(Ticket::new).collect(Collectors.toList());
        NEW_TRANSPORT_LINES.get(0).setName("R1");

        Mockito.when(transportLineRepository.findAll()).thenReturn(DB_TRANSPORT_LINES);
        Mockito.when(transportLineRepository.findById(DEL_ID)).thenReturn(Optional.of(DB_TRANSPORT_LINE))
                .thenReturn(Optional.of(DEL_TRANSPORT_LINE));
        Mockito.when(transportLineRepository.findById(DEL_ID_INVALID))
                .thenThrow(new GeneralException("Transport line does not exist", HttpStatus.BAD_REQUEST));
        Mockito.when(transportLineRepository.save(any(TransportLine.class))).then(invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                TransportLine transportLine = (TransportLine) arguments[0];
                if (DEL_ID.equals(transportLine.getId())) {
                    DEL_TRANSPORT_LINE = transportLine;
                } else if (transportLine.getName() == null || transportLine.getType() == null) {
                    throw new GeneralException("Invalid transport line data!", HttpStatus.BAD_REQUEST);
                } else if (transportLine.getId() == null && transportLine.getPositions().getId() != null) {
                    throw new GeneralException("Invalid transport line position associated!", HttpStatus.BAD_REQUEST);
                } else if (transportLine.getName().equals(DB_NOT_UNIQUE_NAME)) {
                    throw new GeneralException("Transport line with with given name already exist!",
                            HttpStatus.BAD_REQUEST);
                } else {
                    transportLine.setId(NEW_ID);
                    transportLine.getPositions().setId(NEW_POSITION_ID);
                    DB_TRANSPORT_LINES.add(transportLine);
                }
                return transportLine;
            }
            return null;
        });

        Mockito.when(transportLinePositionRepository.save(any(TransportLinePosition.class))).then(invocation -> {
            Object[] arguments = invocation.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                return arguments[0];
            }
            return null;
        });

        Mockito.when(scheduleRepository.findAll()).then(invocationOnMock -> this.schedules.stream()
                .map(Schedule::new).collect(Collectors.toList())).then(invocationOnMock -> {
            if (!negativeTest) {
                List<Schedule> result = new ArrayList<>();
                for (Schedule schedule : this.schedules) {
                    if (schedule.getId() != 102L) {
                        result.add(schedule);
                    }
                }
                return result;
            } else {
                return new ArrayList<Schedule>();
            }
        });
        Mockito.doAnswer(invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                @SuppressWarnings("unchecked")
                Iterable<Schedule> iterable = (Iterable<Schedule>) arguments[0];
                this.schedules.removeIf(schedule -> {
                    boolean exist = false;
                    for (Schedule s : iterable) {
                        if (s.getId().equals(schedule.getId())) {
                            exist = true;
                            break;
                        }
                    }
                    return exist;
                });
            }
            return null;
        }).when(scheduleRepository).deleteAll(any());

        Mockito.when(scheduleRepository.findAllById(any())).thenReturn(new ArrayList<>());

        Mockito.when(ticketRepository.findAll()).then(invocationOnMock -> this.tickets.stream()
                .map(Ticket::new).collect(Collectors.toList())).then(invocationOnMock -> {
            if (!negativeTest) {
                List<Ticket> result = new ArrayList<>();
                for (Ticket schedule : this.tickets) {
                    if (schedule.getId() != 102L) {
                        result.add(schedule);
                    }
                }
                return result;
            } else {
                return new ArrayList<Ticket>();
            }
        });

        Mockito.doAnswer(invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                @SuppressWarnings("unchecked")
                Iterable<Ticket> iterable = (Iterable<Ticket>) arguments[0];
                this.tickets.removeIf(schedule -> {
                    boolean exist = false;
                    for (Ticket s : iterable) {
                        if (s.getId().equals(schedule.getId())) {
                            exist = true;
                            break;
                        }
                    }
                    return exist;
                });
            }
            return null;
        }).when(ticketRepository).deleteAll(any());

        Mockito.when(ticketRepository.findByTransportLine(any())).then(invocation -> {
            if (negativeTest) {
                return this.tickets;
            } else {
                return new ArrayList<>();
            }
        });

        Mockito.when(zoneRepository.findById(1L))
                .thenReturn(Optional.of(new Zone(1L, "Novi Sad", null, true)));

        Mockito.when(vehicleRepository.findByTransportLine(any())).then(invocationOnMock -> {
            if (!negativeTest || updateTest) {
                return DB_VEHICLES;
            } else {
                return new ArrayList<>();
            }
        });
        Mockito.when(vehicleRepository.saveAll(any())).then(invocation -> {
            if (negativeTest) {
                List<Vehicle> vehicles = new ArrayList<>();
                for (Vehicle v : DB_VEHICLES) {
                    vehicles.add(new Vehicle(v.getId(), v.getName(), v.getType(),
                            null, v.isActive()));
                }
                return vehicles;
            } else {
                return DB_VEHICLES;
            }
        });

        Mockito.when(vehicleRepository.findAll()).then(invocation -> {
            if (negativeTest) {
                List<Vehicle> vehicles = new ArrayList<>();
                for (Vehicle v : DB_VEHICLES) {
                    vehicles.add(new Vehicle(v.getId(), v.getName(), v.getType(),
                            null, v.isActive()));
                }
                return vehicles;
            } else {
                DB_VEHICLES.forEach(vehicle -> {
                    if (vehicle.getCurrentLine() == null) {
                        vehicle.setCurrentLine(new TransportLine(1L, "R1", VehicleType.BUS,
                                new TransportLinePosition(1L, "45.25674,23.45442 46.75338,24.27895(red|R3)",
                                        null, true), new HashSet<>(),
                                new Zone(2L, "Liman", null, true), true));
                    }
                });
                return DB_VEHICLES;
            }

        });

        Mockito.doNothing().when(transportLineRepository).deleteAll(any());
        Mockito.when(transportLineRepository.saveAll(any())).then(invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                @SuppressWarnings("unchecked")
                Iterable<TransportLine> iterable = (Iterable<TransportLine>) arguments[0];
                long id = 700L;
                for (TransportLine t : iterable) {
                    if (t.getId() == null) {
                        t.setId(++id);
                        t.getPositions().setId(++id);
                    }
                }
                return iterable;
            }
            return null;
        });
    }

    /**
     * Test valid transport line saving
     */
    @Test
    public void save() {
        negativeTest = true;
        TransportLine transportLine =
                new TransportLine(null, NEW_NAME, NEW_TYPE, new TransportLinePosition(null,
                        "45.23,26.24  44.74,36.12 (green|" + NEW_NAME + ")", null, true),
                        new HashSet<>(), DB_ZONE, true);
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

        Mockito.verify(transportLineRepository, Mockito.times(1)).save(any(TransportLine.class));
        Mockito.verify(transportLinePositionRepository, Mockito.times(1))
                .save(any(TransportLinePosition.class));
        Mockito.verify(vehicleRepository, Mockito.never()).saveAll(any());
        Mockito.verify(scheduleRepository, Mockito.times(1)).findAllById(any());
    }

    /**
     * Test valid transport line saving with updated
     * Vehicles associated with transport line should be updated
     * with null current line
     */
    @Test
    public void saveTransportLineWithUpdatedType() {
        negativeTest = true;
        updateTest = true;
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

        assertThat(transportLineService.getAll().size()).isEqualTo(countBefore);
        assertThat(dbTransportLine.getName()).isEqualTo(transportLine.getName());
        assertThat(dbTransportLine.getType()).isEqualTo(VehicleType.METRO);
        assertThat(dbTransportLine.getPositions().getId()).isNotNull();
        assertThat(dbTransportLine.getPositions().getContent()).isEqualTo(transportLine.getPositions().getContent());
        assertThat(dbTransportLine.getSchedule().size()).isEqualTo(transportLine.getSchedule().size());
        assertThat(dbTransportLine.getZone().getId()).isEqualTo(transportLine.getZone().getId());

        Mockito.verify(transportLineRepository, Mockito.times(1)).save(any(TransportLine.class));
        Mockito.verify(transportLinePositionRepository, Mockito.times(1))
                .save(any(TransportLinePosition.class));
        Mockito.verify(vehicleRepository, Mockito.times(1)).saveAll(any());
        Mockito.verify(scheduleRepository, Mockito.times(1)).findAllById(any());

        List<Vehicle> vehicles = vehicleRepository.findAll();
        vehicles.forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNull());
    }

    /**
     * Test null saving
     */
    @Test(expected = GeneralException.class)
    public void saveNull() {
        transportLineService.save(null);
    }

    /**
     * Test with null values
     */
    @Test(expected = GeneralException.class)
    public void saveWithNullValues() {
        TransportLine transportLine =
                new TransportLine(null, null, null, null, null, null, true);
        transportLineService.save(transportLine);
    }

    /**
     * Test transport line position has long id instead of null
     */
    @Test(expected = GeneralException.class)
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
    public void saveWithNotUniqueName() {
        TransportLine transportLine =
                new TransportLine(null, DB_NOT_UNIQUE_NAME, NEW_TYPE, NEW_POSITION, new HashSet<>(),
                        DB_ZONE, true);
        transportLineService.save(transportLine);

        Mockito.verify(transportLineRepository, Mockito.times(1)).save(any(TransportLine.class));
    }


    /**
     * Test with to short name value
     */
    @Test(expected = GeneralException.class)
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

        Mockito.verify(transportLineRepository, Mockito.times(1)).save(any(TransportLine.class));
        Mockito.verify(transportLinePositionRepository, Mockito.times(1))
                .save(any(TransportLinePosition.class));
        Mockito.verify(scheduleRepository, Mockito.times(1)).findAllById(any());
    }

    /**
     * Test with max length name value
     */
    @Test
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

        Mockito.verify(transportLineRepository, Mockito.times(1)).save(any(TransportLine.class));
        Mockito.verify(transportLinePositionRepository, Mockito.times(1))
                .save(any(TransportLinePosition.class));
        Mockito.verify(scheduleRepository, Mockito.times(1)).findAllById(any());
    }

    /**
     * Test valid transport line deletion
     */
    @Test
    public void remove() {
        transportLineService.remove(DEL_ID);

        Mockito.verify(transportLineRepository, Mockito.times(1)).findById(DEL_ID);
        Mockito.verify(transportLineRepository, Mockito.times(1)).save(any(TransportLine.class));

        TransportLine transportLine = transportLineService.findById(DEL_ID);
        assertThat(transportLine.isActive()).isFalse();
    }

    /**
     * Test transport line deletion that does not exist in database
     */
    @Test(expected = GeneralException.class)
    public void removeInvalidId() {
        transportLineService.remove(DEL_ID_INVALID);
    }

    /**
     * Test valid replacement
     */
    @Test
    public void replaceAll() {
        List<TransportLine> transportLines = transportLineService.replaceAll(NEW_TRANSPORT_LINES);

        Mockito.verify(transportLineRepository, Mockito.times(1)).findAll();
        Mockito.verify(transportLineRepository, Mockito.times(1)).deleteAll(any());
        Mockito.verify(transportLineRepository, Mockito.times(1)).saveAll(any());
        Mockito.verify(scheduleRepository, Mockito.times(1)).findAll();
        Mockito.verify(scheduleRepository, Mockito.times(1)).deleteAll(any());
        Mockito.verify(zoneRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(vehicleRepository, Mockito.times(1)).findByTransportLine(any());
        Mockito.verify(vehicleRepository, Mockito.times(1)).saveAll(any());
        Mockito.verify(ticketRepository, Mockito.times(1)).deleteAll(any());
        Mockito.verify(ticketRepository, Mockito.times(1)).findByTransportLine(any());

        assertThat(transportLines).isNotNull();
        assertThat(transportLines.size()).isEqualTo(NEW_TRANSPORT_LINES.size());
        assertThat(transportLines.size()).isEqualTo(NEW_TRANSPORT_LINES.size());
        transportLines.forEach(transportLine -> {
            assertThat(transportLine.getId()).isNotNull();
            assertThat(transportLine.getName()).isIn(NEW_TRANSPORT_LINES
                    .stream().map(TransportLine::getName).collect(Collectors.toList()));
        });
        assertThat(scheduleRepository.findAll().size()).isEqualTo(DB_SCHEDULES_COUNT - DEL_SCHEDULE_COUNT);
        vehicleRepository.findAll().forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNotNull());
        assertThat(ticketRepository.findAll().size()).isEqualTo(DB_TICKET_COUNT);


    }

    /**
     * Test null replacing
     */
    @Test(expected = GeneralException.class)
    public void replaceAllNull() {
        transportLineService.replaceAll(null);
    }

    /**
     * Test with schedule associated to wrong transport line
     */
    @Test(expected = GeneralException.class)
    public void replaceAllWithInvalidScheduleAssociation() {
        transportLineService.replaceAll(NEW_TRANSPORT_LINES_INVALID_SCHEDULE_ASSOCIATION);
    }

    /**
     * Test replacement transport lines with no zone
     */
    @Test
    public void replaceAllWithNoZone() {
        negativeTest = true;
        List<TransportLine> transportLines = transportLineService.replaceAll(NEW_TRANSPORT_LINES_NO_ZONE);

        Mockito.verify(transportLineRepository, Mockito.times(1)).findAll();
        Mockito.verify(transportLineRepository, Mockito.times(1)).deleteAll(any());
        Mockito.verify(transportLineRepository, Mockito.times(1)).saveAll(any());
        Mockito.verify(scheduleRepository, Mockito.times(1)).findAll();
        Mockito.verify(scheduleRepository, Mockito.times(1)).deleteAll(any());
        Mockito.verify(zoneRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(vehicleRepository, Mockito.times(1)).findByTransportLine(any());
        Mockito.verify(vehicleRepository, Mockito.times(1)).saveAll(any());

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
    public void replaceAllWithNullSchedule() {
        negativeTest = true;
        List<TransportLine> transportLines = transportLineService.replaceAll(NEW_TRANSPORT_LINES_NO_SCHEDULE);

        Mockito.verify(transportLineRepository, Mockito.times(1)).findAll();
        Mockito.verify(transportLineRepository, Mockito.times(1)).deleteAll(any());
        Mockito.verify(transportLineRepository, Mockito.times(1)).saveAll(any());
        Mockito.verify(scheduleRepository, Mockito.times(1)).findAll();
        Mockito.verify(scheduleRepository, Mockito.times(1)).deleteAll(any());
        Mockito.verify(zoneRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(vehicleRepository, Mockito.times(1)).findByTransportLine(any());
        Mockito.verify(vehicleRepository, Mockito.times(1)).saveAll(any());

        assertThat(transportLines).isNotNull();
        assertThat(transportLines.size()).isEqualTo(NEW_TRANSPORT_LINES_NO_SCHEDULE.size());
        assertThat(scheduleRepository.findAll()).isEmpty();
        transportLines.forEach(transportLine -> assertThat(transportLine.getSchedule().size()).isEqualTo(0));
        vehicleRepository.findAll().forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNull());
        assertThat(ticketRepository.findAll().size()).isEqualTo(0);
    }

    /**
     * Test replacement with schedule that does not exist in database
     */
    @Test(expected = GeneralException.class)
    public void replaceAllWithInvalidSchedule() {
        transportLineService.replaceAll(NEW_TRANSPORT_LINES_INVALID_SCHEDULE);
    }

    /**
     * Test with too short name value
     */
    @Test(expected = GeneralException.class)
    public void replaceAllWithShortName() {
        NEW_TRANSPORT_LINES.get(0).setName(NEW_NAME_SHORT_LENGTH);
        transportLineService.replaceAll(NEW_TRANSPORT_LINES);
    }

    /**
     * Test with too long name value
     */
    @Test(expected = GeneralException.class)
    public void replaceAllWithLongName() {
        NEW_TRANSPORT_LINES.get(0).setName(NEW_NAME_LONG_LENGTH);
        transportLineService.replaceAll(NEW_TRANSPORT_LINES);
    }

    /**
     * Test with min length name value
     */
    @Test
    public void replaceAllWithMinLengthName() {
        NEW_TRANSPORT_LINES.get(0).setName(NEW_NAME_MIN_LENGTH);
        List<TransportLine> transportLines = transportLineService.replaceAll(NEW_TRANSPORT_LINES);

        Mockito.verify(transportLineRepository, Mockito.times(1)).findAll();
        Mockito.verify(transportLineRepository, Mockito.times(1)).deleteAll(any());
        Mockito.verify(transportLineRepository, Mockito.times(1)).saveAll(any());
        Mockito.verify(scheduleRepository, Mockito.times(1)).findAll();
        Mockito.verify(scheduleRepository, Mockito.times(1)).deleteAll(any());
        Mockito.verify(zoneRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(vehicleRepository, Mockito.times(1)).findByTransportLine(any());
        Mockito.verify(vehicleRepository, Mockito.times(1)).saveAll(any());
        Mockito.verify(ticketRepository, Mockito.times(1)).deleteAll(any());
        Mockito.verify(ticketRepository, Mockito.times(1)).findByTransportLine(any());

        assertThat(transportLines).isNotNull();
        assertThat(transportLines.size()).isEqualTo(NEW_TRANSPORT_LINES.size());
        assertThat(scheduleRepository.findAll().size()).isEqualTo(DB_SCHEDULES_COUNT - DEL_SCHEDULE_COUNT);
        vehicleRepository.findAll().forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNotNull());
        assertThat(ticketRepository.findAll().size()).isEqualTo(DB_TICKET_COUNT);
    }

    /**
     * Test with max length name value
     */
    @Test
    public void replaceAllWithMaxLengthName() {
        NEW_TRANSPORT_LINES.get(0).setName(NEW_NAME_MAX_LENGTH);
        List<TransportLine> transportLines = transportLineService.replaceAll(NEW_TRANSPORT_LINES);

        Mockito.verify(transportLineRepository, Mockito.times(1)).findAll();
        Mockito.verify(transportLineRepository, Mockito.times(1)).deleteAll(any());
        Mockito.verify(transportLineRepository, Mockito.times(1)).saveAll(any());
        Mockito.verify(scheduleRepository, Mockito.times(1)).findAll();
        Mockito.verify(scheduleRepository, Mockito.times(1)).deleteAll(any());
        Mockito.verify(zoneRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(vehicleRepository, Mockito.times(1)).findByTransportLine(any());
        Mockito.verify(vehicleRepository, Mockito.times(1)).saveAll(any());
        Mockito.verify(ticketRepository, Mockito.times(1)).deleteAll(any());
        Mockito.verify(ticketRepository, Mockito.times(1)).findByTransportLine(any());

        assertThat(transportLines).isNotNull();
        assertThat(transportLines.size()).isEqualTo(NEW_TRANSPORT_LINES.size());
        assertThat(scheduleRepository.findAll().size()).isEqualTo(DB_SCHEDULES_COUNT - DEL_SCHEDULE_COUNT);
        vehicleRepository.findAll().forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNotNull());
        assertThat(ticketRepository.findAll().size()).isEqualTo(DB_TICKET_COUNT);
    }
}