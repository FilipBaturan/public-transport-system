package construction_and_testing.public_transport_system.service.unit;

import construction_and_testing.public_transport_system.domain.Zone;
import construction_and_testing.public_transport_system.repository.TransportLineRepository;
import construction_and_testing.public_transport_system.repository.ZoneRepository;
import construction_and_testing.public_transport_system.service.definition.ZoneService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static construction_and_testing.public_transport_system.constants.ZoneConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ZoneServiceImplUnitTest {

    @MockBean
    private ZoneRepository zoneRepository;

    @MockBean
    private TransportLineRepository transportLineRepository;

    @Autowired
    private ZoneService zoneService;

    @Before
    public void setUp() throws Exception {
        Mockito.when(transportLineRepository.findAllById(DB_TR_ID)).thenReturn(DB_TR);
        Mockito.when(transportLineRepository.findAllById(new ArrayList<>())).thenReturn(new ArrayList<>());
        Mockito.when(transportLineRepository.findAllById(TR_ID_INVALID)).thenReturn(new ArrayList<>());
        Mockito.when(zoneRepository.save(any(Zone.class))).then(invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                Zone zone = (Zone) arguments[0];
                if (zone.getName().equals(NOT_UNIQUE_NAME)) {
                    throw new DataIntegrityViolationException(null);
                }else if (zone.getName().equals(DEL_NAME)){
                    zone.setActive(false);
                    DEL_ZONE = zone;
                }else{
                    zone.setId(NEW_ID);
                    DB_ZONES.add(zone);
                }
                return zone;
            }
            return null;
        });
        Mockito.when(zoneRepository.findAll()).thenReturn(DB_ZONES);

        Mockito.when(zoneRepository.findById(DB_ID)).thenReturn(Optional.of(DB_ZONE)).thenReturn(Optional.of(DEL_ZONE));
        Mockito.when(zoneRepository.findById(DEFAULT_ZONE_ID)).thenReturn(Optional.of(new Zone(DEFAULT_ZONE_ID)));
        Mockito.when(zoneRepository.findById(DEL_ID_INVALID)).thenThrow(GeneralException.class);
    }

    /**
     * Test zone with transport lines
     */
    @Test
    public void saveWithLines() {
        Zone zone = new Zone(null, DB_NAME, DB_TR_SAT, true);
        zone.getLines().forEach(transportLine -> transportLine.setZone(zone));
        int countBefore = zoneService.getAll().size();

        Zone dbZone = zoneService.save(zone);
        assertThat(zoneService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbZone.getId()).isEqualTo(NEW_ID);
        assertThat(dbZone.getName()).isEqualTo(zone.getName());
        assertThat(dbZone.getLines()).isEqualTo(zone.getLines());
        assertThat(dbZone.isActive()).isEqualTo(zone.isActive());

        Mockito.verify(transportLineRepository, Mockito.times(1)).findAllById(DB_TR_ID);
        Mockito.verify(zoneRepository, Mockito.times(1)).save(any(Zone.class));

    }

    /**
     * Test zone with transport lines
     */
    @Test
    public void saveWithNoLines() {
        Zone zone = new Zone(null, DB_NAME, new HashSet<>(), true);
        int countBefore = zoneService.getAll().size();

        Zone dbZone = zoneService.save(zone);
        assertThat(zoneService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbZone.getId()).isEqualTo(NEW_ID);
        assertThat(dbZone.getName()).isEqualTo(zone.getName());
        assertThat(dbZone.getLines()).isEqualTo(zone.getLines());
        assertThat(dbZone.isActive()).isEqualTo(zone.isActive());

        Mockito.verify(transportLineRepository, Mockito.times(1)).findAllById(new ArrayList<>());
        Mockito.verify(zoneRepository, Mockito.times(1)).save(any(Zone.class));

    }

    /**
     * Test zone with not unique name
     */
    @Test(expected = GeneralException.class)
    public void saveWithInvalidName() {
        Zone zone = new Zone(null, NOT_UNIQUE_NAME, new HashSet<>(), true);
        int countBefore = zoneService.getAll().size();

        Zone dbZone = zoneService.save(zone);
        assertThat(zoneService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbZone.getId()).isEqualTo(NEW_ID);
        assertThat(dbZone.getName()).isEqualTo(zone.getName());
        assertThat(dbZone.getLines()).isEqualTo(zone.getLines());
        assertThat(dbZone.isActive()).isEqualTo(zone.isActive());

        Mockito.verify(transportLineRepository, Mockito.times(1)).findAllById(new ArrayList<>());
        Mockito.verify(zoneRepository, Mockito.times(1)).save(any(Zone.class));

    }

    /**
     * Test zone with invalid transport lines data associated
     */
    @Test(expected = GeneralException.class)
    public void saveWithInvalidLines() {
        Zone zone = new Zone(null, NEW_NAME, TR_INVALID, true);
        zone.getLines().forEach(transportLine -> transportLine.setZone(zone));
        int countBefore = zoneService.getAll().size();

        Zone dbZone = zoneService.save(zone);
        assertThat(zoneService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbZone.getId()).isEqualTo(NEW_ID);
        assertThat(dbZone.getName()).isEqualTo(zone.getName());
        assertThat(dbZone.getLines()).isEqualTo(zone.getLines());
        assertThat(dbZone.isActive()).isEqualTo(zone.isActive());

        Mockito.verify(transportLineRepository, Mockito.times(1)).findAllById(DB_TR_ID);
        Mockito.verify(zoneRepository, Mockito.times(1)).save(any(Zone.class));

    }

    /**
     * Test valid zone deletion
     */
    @Test
    public void remove() {
        zoneService.remove(DB_ID);

        Mockito.verify(zoneRepository, Mockito.times(1)).findById(DB_ID);
        Mockito.verify(zoneRepository, Mockito.times(1)).findById(DEFAULT_ZONE_ID);
        Mockito.verify(zoneRepository, Mockito.times(1)).save(any(Zone.class));

        Zone zone = zoneService.findById(DB_ID);

        assertThat(zone.isActive()).isFalse();
        zone.getLines().forEach(
                transportLine -> assertThat(transportLine.getZone().getId()).isEqualTo(DEFAULT_ZONE_ID));
    }

    /**
     * Test zone deletion that does not exist in database
     */
    @Test(expected = GeneralException.class)
    public void removeNotValidId() {
        zoneService.remove(DEL_ID_INVALID);

        Mockito.verify(zoneRepository, Mockito.times(1)).findById(DEL_ID_INVALID);
        Mockito.verify(zoneRepository, Mockito.never()).findById(DEFAULT_ZONE_ID);
        Mockito.verify(zoneRepository, Mockito.never()).save(any(Zone.class));

        Zone zone = zoneService.findById(DEL_ID);
        assertThat(zone.isActive()).isFalse();
        zone.getLines().forEach(transportLine ->
                assertThat(transportLine.getZone().getId()).isEqualTo(DEFAULT_ZONE_ID));
    }

    /**
     * Test default zone deletion that can not be deleted
     */
    @Test(expected = GeneralException.class)
    public void removeDefaultZone() {
        zoneService.remove(DEFAULT_ZONE_ID);

        Mockito.verify(zoneRepository, Mockito.never()).findById(DEL_ID_INVALID);
        Mockito.verify(zoneRepository, Mockito.never()).findById(DEFAULT_ZONE_ID);
        Mockito.verify(zoneRepository, Mockito.never()).save(any(Zone.class));

        Zone zone = zoneService.findById(DEL_ID);
        assertThat(zone.isActive()).isFalse();
        zone.getLines().forEach(transportLine ->
                assertThat(transportLine.getZone().getId()).isEqualTo(DEFAULT_ZONE_ID));
    }
}