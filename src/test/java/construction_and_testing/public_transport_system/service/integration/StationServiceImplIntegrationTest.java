package construction_and_testing.public_transport_system.service.integration;

import construction_and_testing.public_transport_system.domain.Station;
import construction_and_testing.public_transport_system.service.definition.StationService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static construction_and_testing.public_transport_system.constants.StationConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StationServiceImplIntegrationTest {

    @Autowired
    private StationService stationService;

    /**
     * Test get all stations from database
     */
    @Test
    public void getAll() {
        List<Station> stations = stationService.getAll();
        assertThat(stations).hasSize(DB_COUNT);
    }

    /**
     * Test with valid id
     */
    @Test
    public void findById() {
        Station station = stationService.findById(DB_ID);
        assertThat(station).isNotNull();

        assertThat(station.getId()).isEqualTo(DB_ID);
        assertThat(station.getName()).isEqualTo(DB_NAME);
        assertThat(station.getType()).isEqualTo(DB_TYPE);
        assertThat(station.getPosition().getId()).isEqualTo(DB_POSITION);
        assertThat(station.isActive()).isEqualTo(DB_ACTIVE);
    }

    /**
     * Test with invalid id
     */
    @Test(expected = GeneralException.class)
    public void findByInvalidId() {
        stationService.findById(DB_ID_INVALID);
    }

    /**
     * Test with null id
     */
    @Test(expected = GeneralException.class)
    public void findByNullId() {
        Station station = stationService.findById(null);
        assertThat(station).isNotNull();

        assertThat(station.getId()).isEqualTo(DB_ID);
        assertThat(station.getName()).isEqualTo(DB_NAME);
        assertThat(station.getType()).isEqualTo(DB_TYPE);
        assertThat(station.getPosition().getId()).isEqualTo(DB_POSITION);
        assertThat(station.isActive()).isEqualTo(DB_ACTIVE);
    }

    /**
     * Test valid vehicle saving
     */
    @Test
    @Transactional
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

    }

    /**
     * Test with null values
     */
    @Test(expected = DataIntegrityViolationException.class)
    @Transactional
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

    }

    /**
     * Test valid station deletion
     */
    @Test
    @Transactional
    public void remove() {
        stationService.remove(DEL_ID);
        Station station = stationService.findById(DEL_ID);
        assertThat(station.isActive()).isFalse();
    }

    /**
     * Test station deletion that does not exist in database
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void removeInvalidId() {
        stationService.remove(DEL_ID_INVALID);
    }

    /**
     * Test replace all station
     */
    @Test
    @Transactional
    public void replaceAll() {
        List<Station> stationsBefore = stationService.getAll();
        List<Station> stations = stationService.replaceAll(NEW_STATIONS);

        assertThat(stations).isNotNull();
        assertThat(stations.size()).isEqualTo(NEW_STATIONS.size());
        assertThat(stations).doesNotContainSequence(stationsBefore);
    }
}