package construction_and_testing.public_transport_system.service.unit;

import construction_and_testing.public_transport_system.domain.Zone;
import construction_and_testing.public_transport_system.repository.TransportLineRepository;
import construction_and_testing.public_transport_system.repository.ZoneRepository;
import construction_and_testing.public_transport_system.service.definition.ZoneService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static construction_and_testing.public_transport_system.constants.ZoneConstants.*;

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
        Mockito.when(zoneRepository.findById(DB_ID)).thenReturn(Optional.of(DB_ZONE));
        Mockito.when(zoneRepository.findAll()).thenReturn(DB_ZONES);
    }

    @Test
    public void save() {
        Zone zone = new Zone(null, DB_NAME, DB_TR_SAT, true);
        int countBefore = zoneRepository.findAll().size();

        Zone dbZone = zoneRepository.save(zone);

    }

    @Test
    public void remove() {
    }
}