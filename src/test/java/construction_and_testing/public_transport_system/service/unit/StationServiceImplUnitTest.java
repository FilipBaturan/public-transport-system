package construction_and_testing.public_transport_system.service.unit;

import construction_and_testing.public_transport_system.domain.Station;
import construction_and_testing.public_transport_system.repository.StationRepository;
import construction_and_testing.public_transport_system.service.definition.StationService;
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

import java.util.Optional;

import static construction_and_testing.public_transport_system.constants.StationConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StationServiceImplUnitTest {

    @MockBean
    private StationRepository stationRepository;

    @Autowired
    private StationService stationService;

    @Before
    public void setUp() throws Exception {
        Mockito.when(stationRepository.findAll()).thenReturn(DB_STATIONS);
        Mockito.when(stationRepository.findById(DEL_ID)).thenReturn(Optional.of(DB_STATION))
                .thenReturn(Optional.of(DEL_STATION));
        Mockito.when(stationRepository.save(any(Station.class))).then(invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                Station station = (Station) arguments[0];
                if (DEL_ID.equals(station.getId())) {
                    DEL_STATION = station;
                } else if (station.getName() == null || station.getType() == null) {
                    throw new GeneralException("Invalid vehicle data!", HttpStatus.BAD_REQUEST);
                } else {
                    station.setId(NEW_ID);
                    DB_STATIONS.add(station);
                }
                return station;
            }
            return null;
        });
    }

    /**
     * Test valid vehicle saving
     */
    @Test
    public void save() {
        Station station = new Station(null, NEW_NAME, NEW_POSITION, NEW_TYPE, NEW_ACTIVE);
        station.getPosition().setStation(station);
        int countBefore = stationService.getAll().size();

        Station dbStation = stationService.save(station);
        assertThat(dbStation).isNotNull();

        assertThat(stationService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbStation.getName()).isEqualTo(station.getName());
        assertThat(dbStation.getType()).isEqualTo(station.getType());
        assertThat(dbStation.getPosition()).isEqualTo(station.getPosition());
        assertThat(dbStation.isActive()).isEqualTo(station.isActive());

        Mockito.verify(stationRepository, Mockito.times(1)).save(any(Station.class));
    }

    /**
     * Test with null values
     */
    @Test(expected = GeneralException.class)
    public void saveWithNullValues() {
        Station station = new Station(null, null, null, null, true);
        int countBefore = stationService.getAll().size();

        Station dbStation = stationService.save(station);
        assertThat(dbStation).isNotNull();

        assertThat(stationService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbStation.getName()).isEqualTo(station.getName());
        assertThat(dbStation.getType()).isEqualTo(station.getType());
        assertThat(dbStation.getPosition()).isEqualTo(station.getPosition());
        assertThat(dbStation.isActive()).isEqualTo(station.isActive());

        Mockito.verify(stationRepository, Mockito.times(1)).save(any(Station.class));
    }

    /**
     * Test valid station deletion
     */
    @Test
    public void remove() {
        stationService.remove(DEL_ID);

        Mockito.verify(stationRepository, Mockito.times(1)).findById(DEL_ID);
        Mockito.verify(stationRepository, Mockito.times(1)).save(any(Station.class));

        Station station = stationService.findById(DEL_ID);
        assertThat(station.isActive()).isFalse();


    }

    /**
     * Test station deletion that does not exist in database
     */
    @Test(expected = GeneralException.class)
    public void removeInvalidId() {
        stationService.remove(DEL_ID_INVALID);

        Mockito.verify(stationRepository, Mockito.times(1)).findById(DEL_ID);
        Mockito.verify(stationRepository, Mockito.times(1)).save(any(Station.class));
    }
}