package construction_and_testing.public_transport_system.service.integration;

import construction_and_testing.public_transport_system.domain.Station;
import construction_and_testing.public_transport_system.domain.StationPosition;
import construction_and_testing.public_transport_system.service.definition.StationService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
        stationService.findById(null);
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
     * Test null saving
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void saveNull() {
        stationService.save(null);
    }

    /**
     * Test with null values
     */
    @Test(expected = GeneralException.class)
    public void saveWithNullValues() {
        Station station = new Station(null, null, null, null, true);
        stationService.save(station);
    }

    /**
     * Test with to short firstName value
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void saveWithShortName() {
        Station station = new Station(null, NEW_NAME_SHORT_LENGTH, new StationPosition(), NEW_TYPE, NEW_ACTIVE);
        station.getPosition().setStation(station);
        stationService.save(station);
    }

    /**
     * Test with too long firstName value
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void saveWithLongName() {
        Station station = new Station(null, NEW_NAME_LONG_LENGTH, new StationPosition(), NEW_TYPE, NEW_ACTIVE);
        station.getPosition().setStation(station);
        stationService.save(station);
    }

    /**
     * Test with min length firstName value
     */
    @Test
    @Transactional
    public void saveWithMinLengthName() {
        Station station = new Station(DB_ID, NEW_NAME_MIN_LENGTH,
                new StationPosition(DB_POSITION, 45.32, 45.21, true, null),
                NEW_TYPE, NEW_ACTIVE);
        station.getPosition().setStation(station);
        int countBefore = stationService.getAll().size();

        Station dbStation = stationService.save(station);
        assertThat(dbStation).isNotNull();

        assertThat(stationService.getAll().size()).isEqualTo(countBefore);
        assertThat(dbStation.getId()).isEqualTo(station.getId());
        assertThat(dbStation.getName()).isEqualTo(station.getName());
        assertThat(dbStation.getType()).isEqualTo(station.getType());
        assertThat(dbStation.getPosition().getId()).isEqualTo(station.getPosition().getId());
        assertThat(dbStation.getPosition().getLatitude()).isEqualTo(station.getPosition().getLatitude());
        assertThat(dbStation.getPosition().getLongitude()).isEqualTo(station.getPosition().getLongitude());
        assertThat(dbStation.isActive()).isEqualTo(station.isActive());
    }

    /**
     * Test with max length firstName value
     */
    @Test
    @Transactional
    public void saveWithMaxLengthName() {
        Station station = new Station(null, NEW_NAME_MAX_LENGTH, new StationPosition(), NEW_TYPE, NEW_ACTIVE);
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
        stations.forEach(transportLine -> {
            assertThat(transportLine.getId()).isNotNull();
            assertThat(transportLine.getName()).isIn(NEW_STATIONS
                    .stream().map(Station::getName).collect(Collectors.toList()));
        });
        assertThat(stations).doesNotContainSequence(stationsBefore);
    }

    /**
     * Test null replacement
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void replaceAllNull() {
        stationService.replaceAll(null);
    }

    /**
     * Test with too short firstName value
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void replaceAllWithShortName() {
        NEW_STATIONS.get(0).setName(NEW_NAME_SHORT_LENGTH);
        stationService.replaceAll(NEW_STATIONS);
    }

    /**
     * Test with too long firstName value
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void replaceAllWithLongName() {
        NEW_STATIONS.get(0).setName(NEW_NAME_LONG_LENGTH);
        stationService.replaceAll(NEW_STATIONS);
    }

    /**
     * Test with min length firstName value
     */
    @Test
    @Transactional
    public void replaceAllWithMinLengthName() {
        List<Station> stationsBefore = stationService.getAll();
        NEW_STATIONS.get(0).setName(NEW_NAME_MIN_LENGTH);
        List<Station> stations = stationService.replaceAll(NEW_STATIONS);

        assertThat(stations).isNotNull();
        assertThat(stations.size()).isEqualTo(NEW_STATIONS.size());
        assertThat(stations).doesNotContainSequence(stationsBefore);
    }

    /**
     * Test with max length firstName value
     */
    @Test
    @Transactional
    public void replaceAllWithMaxLengthName() {
        List<Station> stationsBefore = stationService.getAll();
        NEW_STATIONS.get(0).setName(NEW_NAME_MAX_LENGTH);
        List<Station> stations = stationService.replaceAll(NEW_STATIONS);

        assertThat(stations).isNotNull();
        assertThat(stations.size()).isEqualTo(NEW_STATIONS.size());
        assertThat(stations).doesNotContainSequence(stationsBefore);
    }
}