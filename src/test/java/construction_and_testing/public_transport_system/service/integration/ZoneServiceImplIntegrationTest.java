package construction_and_testing.public_transport_system.service.integration;

import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.domain.Zone;
import construction_and_testing.public_transport_system.repository.ZoneRepository;
import construction_and_testing.public_transport_system.service.definition.ZoneService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

import static construction_and_testing.public_transport_system.constants.ZoneConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ZoneServiceImplIntegrationTest {

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private ZoneRepository zoneRepository;

    /**
     * Test get all zone from database
     */
    @Test
    public void getAll() {
        List<Zone> zones = zoneService.getAll();
        assertThat(zones).hasSize(DB_COUNT);
    }

    /**
     * Test with valid id
     */
    @Test
    public void findById() {
        Zone dbZone = zoneService.findById(DB_ID);
        assertThat(dbZone).isNotNull();

        assertThat(dbZone.getId()).isEqualTo(DB_ID);
        assertThat(dbZone.getName()).isEqualTo(DB_NAME);
        assertThat(dbZone.isActive()).isEqualTo(DB_ACTIVE);
        assertThat(dbZone.getLines()).hasSize(DB_TR_COUNT);
    }

    /**
     * Test with invalid id
     */
    @Test(expected = GeneralException.class)
    public void findByInvalidId() {
        zoneService.findById(DB_ID_INVALID);
    }

    /**
     * Test with null id
     */
    @Test(expected = GeneralException.class)
    public void findByNullId() {
        zoneService.findById(null);
    }

    /**
     * Test with no transport lines
     */
    @Test
    @Transactional
    public void saveNoLines() {
        Zone zone = new Zone(null, NEW_NAME, new HashSet<>(), true);
        int countBefore = zoneService.getAll().size();

        Zone dbZone = zoneService.save(zone);
        assertThat(dbZone).isNotNull();

        assertThat(zoneService.getAll()).hasSize(countBefore + 1);

        assertThat(dbZone.getName()).isEqualTo(zone.getName());
        assertThat(dbZone.isActive()).isEqualTo(zone.isActive());
        assertThat(dbZone.getLines()).isEqualTo(zone.getLines());
    }

    /**
     * Test save with transport lines
     */
    @Test
    @Transactional
    public void saveWithLines() {
        Zone zone = new Zone(null, NEW_NAME, NEW_LINES, true);
        zone.getLines().forEach((TransportLine t) -> t.setZone(zone));
        int countBefore = zoneService.getAll().size();

        Zone dbZone = zoneService.save(zone);
        assertThat(dbZone).isNotNull();

        assertThat(zoneService.getAll()).hasSize(countBefore + 1);

        assertThat(dbZone.getName()).isEqualTo(zone.getName());
        assertThat(dbZone.isActive()).isEqualTo(zone.isActive());
        assertThat(dbZone.getLines()).isEqualTo(zone.getLines());

    }

    /**
     * Test null saving
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void saveNull() {
        zoneService.save(null);
    }

    /**
     * Test save with transport lines
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void saveWithLinesNoDefaultZone() {
        zoneRepository.deleteById(1L);
        Zone zone = new Zone(DB_ID, NEW_NAME, new HashSet<>(), true);
        zone.getLines().forEach((TransportLine t) -> t.setZone(zone));
        zoneService.save(zone);
    }

    /**
     * Test with invalid transport lines data associated
     */
    @Test(expected = GeneralException.class)
    public void saveWithInvalidLines() {
        Zone zone = new Zone(null, NEW_NAME, NEW_LINES_INVALID, true);
        zone.getLines().forEach(transportLine -> transportLine.setZone(zone));
        zoneService.save(zone);
    }

    /**
     * Test with null transport lines data associated
     */
    @Test
    @Transactional
    public void saveWithNullLines() {
        Zone zone = new Zone(null, NEW_NAME, null, true);
        int countBefore = zoneService.getAll().size();

        Zone dbZone = zoneService.save(zone);
        assertThat(dbZone).isNotNull();

        assertThat(zoneService.getAll()).hasSize(countBefore + 1);

        assertThat(dbZone.getName()).isEqualTo(zone.getName());
        assertThat(dbZone.isActive()).isEqualTo(zone.isActive());
        assertThat(dbZone.getLines()).isEqualTo(zone.getLines());

    }

    /**
     * Test with null values
     */
    @Test(expected = GeneralException.class)
    public void saveWithNullValues() {
        Zone zone = new Zone(null, null, NEW_LINES, true);
        zoneService.save(zone);
    }

    /**
     * Test with not unique name
     */
    @Test(expected = GeneralException.class)
    public void saveWithInvalidName() {
        Zone zone1 = new Zone(null, NEW_NAME, new HashSet<>(), true);
        Zone zone2 = new Zone(null, NEW_NAME, NEW_LINES, true);

        Zone dbZone1 = zoneService.save(zone1);
        assertThat(dbZone1).isNotNull();

        zoneService.save(zone2);
    }

    /**
     * Test with to short name value
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void saveWithShortName() {
        Zone zone = new Zone(null, NEW_NAME_SHORT_LENGTH, NEW_LINES, true);
        zone.getLines().forEach((TransportLine t) -> t.setZone(zone));
        zoneService.save(zone);
    }

    /**
     * Test with too long name value
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void saveWithLongName() {
        Zone zone = new Zone(null, NEW_NAME_LONG_LENGTH, NEW_LINES, true);
        zone.getLines().forEach((TransportLine t) -> t.setZone(zone));
        zoneService.save(zone);
    }

    /**
     * Test with min length name value
     */
    @Test
    @Transactional
    public void saveWithMinLengthName() {
        Zone zone = new Zone(DB_ID, NEW_NAME_MIN_LENGTH, NEW_LINES, true);
        zone.getLines().forEach((TransportLine t) -> t.setZone(zone));
        int countBefore = zoneService.getAll().size();

        Zone dbZone = zoneService.save(zone);
        assertThat(dbZone).isNotNull();

        assertThat(zoneService.getAll()).hasSize(countBefore);

        assertThat(dbZone.getName()).isEqualTo(zone.getName());
        assertThat(dbZone.isActive()).isEqualTo(zone.isActive());
        assertThat(dbZone.getLines()).isEqualTo(zone.getLines());
    }

    /**
     * Test with max length name value
     */
    @Test
    @Transactional
    public void saveWithMaxLengthName() {
        Zone zone = new Zone(null, NEW_NAME_MAX_LENGTH, NEW_LINES, true);
        zone.getLines().forEach((TransportLine t) -> t.setZone(zone));
        int countBefore = zoneService.getAll().size();

        Zone dbZone = zoneService.save(zone);
        assertThat(dbZone).isNotNull();

        assertThat(zoneService.getAll()).hasSize(countBefore + 1);

        assertThat(dbZone.getName()).isEqualTo(zone.getName());
        assertThat(dbZone.isActive()).isEqualTo(zone.isActive());
        assertThat(dbZone.getLines()).isEqualTo(zone.getLines());
    }


    /**
     * Test valid zone deletion
     */
    @Test
    @Transactional
    public void remove() {
        int countBefore = zoneService.getAll().size();
        zoneService.remove(DEL_ID);
        Zone zone = zoneService.findById(DEL_ID);
        assertThat(zone.isActive()).isFalse();
        zone.getLines().forEach(transportLine ->
                assertThat(transportLine.getZone().getId()).isEqualTo(DEFAULT_ZONE_ID));
        assertThat(zoneService.getAll()).hasSize(countBefore - 1);
    }

    /**
     * Test zone deletion that does not exist in database
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void removeNotValidId() {
        zoneService.remove(DEL_ID_INVALID);
    }

    /**
     * Test default zone deletion that can not be deleted
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void removeDefaultZone() {
        zoneService.remove(DEFAULT_ZONE_ID);
    }

    /**
     * Test zone deletion when default does not exist
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void removeZoneNoDefault() {
        zoneRepository.deleteById(1L);
        zoneService.remove(DEL_ID);
    }
}