package construction_and_testing.public_transport_system.service.integration;

import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.domain.TransportLinePosition;
import construction_and_testing.public_transport_system.repository.ScheduleRepository;
import construction_and_testing.public_transport_system.repository.VehicleRepository;
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
import java.util.HashSet;
import java.util.List;

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
        TransportLine transportLine = transportLineService.findById(null);
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
     * Test valid transport line saving
     */
    @Test
    @Transactional
    public void save() {
        TransportLine transportLine =
                new TransportLine(null, NEW_NAME, NEW_TYPE,
                        new TransportLinePosition(null, "", null, true), new HashSet<>(), DB_ZONE, true);
        transportLine.getPositions().setTransportLine(transportLine);
        int countBefore = transportLineService.getAll().size();

        TransportLine dbTransportLine = transportLineService.save(transportLine);
        assertThat(dbTransportLine).isNotNull();

        assertThat(transportLineService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbTransportLine.getName()).isEqualTo(transportLine.getName());
        assertThat(dbTransportLine.getType()).isEqualTo(transportLine.getType());
        assertThat(dbTransportLine.getPositions().getId()).isNotNull();
        assertThat(dbTransportLine.getSchedule().size()).isEqualTo(0);
        assertThat(dbTransportLine.getZone().getId()).isEqualTo(DB_ZONE.getId());
    }

    /**
     * Test with null values
     */
    @Test(expected = DataIntegrityViolationException.class)
    @Transactional
    public void saveWithNullValues() {
        TransportLine transportLine =
                new TransportLine(null, null, null, null, null, null, true);
        int countBefore = transportLineService.getAll().size();

        TransportLine dbTransportLine = transportLineService.save(transportLine);
        assertThat(dbTransportLine).isNotNull();

        assertThat(transportLineService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbTransportLine.getName()).isEqualTo(transportLine.getName());
        assertThat(dbTransportLine.getType()).isEqualTo(transportLine.getType());
        assertThat(dbTransportLine.getPositions().getId()).isNotNull();
        assertThat(dbTransportLine.getSchedule().size()).isEqualTo(0);
        assertThat(dbTransportLine.getZone().getId()).isEqualTo(DB_ZONE.getId());
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
        int countBefore = transportLineService.getAll().size();

        TransportLine dbTransportLine = transportLineService.save(transportLine);
        assertThat(dbTransportLine).isNotNull();

        assertThat(transportLineService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbTransportLine.getName()).isEqualTo(transportLine.getName());
        assertThat(dbTransportLine.getType()).isEqualTo(transportLine.getType());
        assertThat(dbTransportLine.getPositions().getId()).isNotNull();
        assertThat(dbTransportLine.getSchedule().size()).isEqualTo(0);
        assertThat(dbTransportLine.getZone().getId()).isEqualTo(DB_ZONE.getId());
    }

    /**
     * Test transport line with not unique name
     */
    @Test(expected = GeneralException.class)
    public void saveWithInvalidName() {
        TransportLine transportLine =
                new TransportLine(null, DB_NAME, NEW_TYPE, NEW_POSITION, new HashSet<>(), DB_ZONE, true);
        transportLineService.save(transportLine);
    }

    /**
     * Test valid transport line deletion
     */
    @Test
    @Transactional
    public void remove() {
        transportLineService.remove(DEL_ID);
        TransportLine transportLine = transportLineService.findById(DEL_ID);
        assertThat(transportLine.isActive()).isFalse();
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
    public void replaceAll() {
        int countBefore = scheduleRepository.findAll().size();

        List<TransportLine> transportLines = transportLineService.replaceAll(NEW_TRANSPORT_LINES);

        assertThat(transportLines).isNotNull();
        assertThat(transportLines.size()).isEqualTo(NEW_TRANSPORT_LINES.size());
        assertThat(scheduleRepository.findAll().size()).isEqualTo(countBefore - DEL_SCHEDULE_COUNT);
        vehicleRepository.findAll().forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNotNull());


    }

    /**
     * Test with schedule associated to wrong transport line
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void replaceAllWithInvalidScheduleAssociation() {
        int countBefore = scheduleRepository.findAll().size();

        List<TransportLine> transportLines = transportLineService
                .replaceAll(NEW_TRANSPORT_LINES_INVALID_SCHEDULE_ASSOCIATION);

        assertThat(transportLines).isNotNull();
        assertThat(transportLines.size()).isEqualTo(NEW_TRANSPORT_LINES_INVALID_SCHEDULE_ASSOCIATION.size());
        assertThat(scheduleRepository.findAll().size()).isEqualTo(countBefore - DEL_SCHEDULE_COUNT);
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
        vehicleRepository.findAll().forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNull());
    }

    /**
     * Test replacement with schedule that does not exist in database
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void replaceAllWithInvalidSchedule() {
        transportLineService.replaceAll(NEW_TRANSPORT_LINES_INVALID_SCHEDULE);

    }
}