package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.domain.DTO.StationCollectionDTO;
import construction_and_testing.public_transport_system.domain.DTO.StationDTO;
import construction_and_testing.public_transport_system.domain.Station;
import construction_and_testing.public_transport_system.domain.StationPosition;
import construction_and_testing.public_transport_system.service.definition.StationService;
import construction_and_testing.public_transport_system.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;

import static construction_and_testing.public_transport_system.constants.StationConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class StationControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private StationService stationService;

    @SpyBean
    private StationController stationController;

    private final String URL = "/api/station";

    @Before
    public void setUp() throws Exception {
        Mockito.doNothing().when(stationController).validateJSON(any(String.class), any(String.class));
    }

    /**
     * Test get all stations from database
     */
    @Test
    public void getAll() {
        ResponseEntity<StationDTO[]> result = testRestTemplate
                .getForEntity(this.URL, StationDTO[].class);

        StationDTO[] body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body).hasSize(DB_COUNT);
    }

    /**
     * Test with valid id
     */
    @Test
    public void findById() {
        ResponseEntity<StationDTO> result = testRestTemplate
                .getForEntity(this.URL + "/" + DB_ID, StationDTO.class);

        StationDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(body).isNotNull();
        assertThat(body.getId()).isEqualTo(DB_ID);
        assertThat(body.getName()).isEqualTo(DB_NAME);
        assertThat(body.getType()).isEqualTo(DB_TYPE);
        assertThat(body.getPosition().getId()).isEqualTo(DB_POSITION);
        assertThat(body.isActive()).isEqualTo(DB_ACTIVE);
    }

    /**
     * Test with invalid id
     */
    @Test
    public void findByInvalidId() {
        ResponseEntity<String> result = testRestTemplate
                .getForEntity(this.URL + "/" + DB_ID_INVALID, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Requested station does not exist!");
    }

    /**
     * Test valid vehicle saving
     */
    @Test
    public void save() throws Exception {
        StationDTO station = new StationDTO(new Station(null, NEW_NAME, new StationPosition(),
                NEW_TYPE, NEW_ACTIVE));
        String jsonStation = TestUtil.json(station);

        ResponseEntity<StationDTO> result = testRestTemplate.postForEntity(this.URL, jsonStation, StationDTO.class);

        StationDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(station.getName());
        assertThat(body.getType()).isEqualTo(station.getType());
        assertThat(body.getPosition().getLongitude()).isEqualTo(station.getPosition().getLongitude());
        assertThat(body.isActive()).isEqualTo(station.isActive());
    }

    /**
     * Test with null values
     */
    @Test
    public void saveWithNullValues() throws Exception {
        StationDTO station = new StationDTO(new Station(null, null, null, null, true));
        String jsonStation = TestUtil.json(station);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URL, jsonStation, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Invalid vehicle data!");
    }

    /**
     * Test with to short name value
     */
    @Test
    public void saveWithShortName() throws Exception {
        StationDTO station = new StationDTO(
                new Station(null, NEW_NAME_SHORT_LENGTH, new StationPosition(), NEW_TYPE, NEW_ACTIVE));
        String jsonStation = TestUtil.json(station);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URL, jsonStation, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
    }

    /**
     * Test with too long name value
     */
    @Test
    public void saveWithLongName() throws Exception {
        StationDTO station = new StationDTO(
                new Station(null, NEW_NAME_LONG_LENGTH, new StationPosition(), NEW_TYPE, NEW_ACTIVE));
        String jsonStation = TestUtil.json(station);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URL, jsonStation, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
    }

    /**
     * Test with min length name value
     */
    @Test
    public void saveWithMinLengthName() throws Exception {
        StationDTO station = new StationDTO(
                new Station(null, NEW_NAME_MIN_LENGTH, new StationPosition(), NEW_TYPE, NEW_ACTIVE));
        String jsonStation = TestUtil.json(station);

        ResponseEntity<StationDTO> result = testRestTemplate.postForEntity(this.URL, jsonStation, StationDTO.class);

        StationDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(station.getName());
        assertThat(body.getType()).isEqualTo(station.getType());
        assertThat(body.getPosition().getLongitude()).isEqualTo(station.getPosition().getLongitude());
        assertThat(body.isActive()).isEqualTo(station.isActive());
    }

    /**
     * Test with max length name value
     */
    @Test
    public void saveWithMaxLengthName() throws Exception {
        StationDTO station = new StationDTO(
                new Station(null, NEW_NAME_MAX_LENGTH, new StationPosition(), NEW_TYPE, NEW_ACTIVE));
        String jsonStation = TestUtil.json(station);

        ResponseEntity<StationDTO> result = testRestTemplate.postForEntity(this.URL, jsonStation, StationDTO.class);

        StationDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(station.getName());
        assertThat(body.getType()).isEqualTo(station.getType());
        assertThat(body.getPosition().getLongitude()).isEqualTo(station.getPosition().getLongitude());
        assertThat(body.isActive()).isEqualTo(station.isActive());
    }

    /**
     * Test valid station deletion
     */
    @Test
    public void delete() throws Exception {
        StationDTO station = new StationDTO(new Station(DEL_ID, DB_NAME, new StationPosition(), DB_TYPE, DB_ACTIVE));
        String jsonStation = TestUtil.json(station);

        testRestTemplate.delete(this.URL, jsonStation, String.class);
    }

    /**
     * Test station deletion that does not exist in database
     */
    @Test
    public void deleteWithInvalidId() throws Exception {
        StationDTO station = new StationDTO(new Station(DEL_ID_INVALID, DB_NAME, new StationPosition(), DB_TYPE, DB_ACTIVE));
        String jsonStation = TestUtil.json(station);

        testRestTemplate.delete(this.URL, jsonStation, String.class);
    }

    /**
     * Test replace all station
     */
    @Test
    public void replaceAll() throws Exception {
        StationCollectionDTO stations =
                new StationCollectionDTO(NEW_STATIONS.stream().map(StationDTO::new).collect(Collectors.toList()));
        String jsonStations = TestUtil.json(stations);

        ResponseEntity<StationDTO[]> result = testRestTemplate.postForEntity(this.URL + "/replace",
                jsonStations, StationDTO[].class);

        StationDTO[] body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body).hasSize(stations.getStations().size());
    }

    /**
     * Test with too short name value
     */
    @Test
    public void replaceAllWithShortName() throws Exception {
        NEW_STATIONS.get(0).setName(NEW_NAME_SHORT_LENGTH);
        StationCollectionDTO stations =
                new StationCollectionDTO(NEW_STATIONS.stream().map(StationDTO::new).collect(Collectors.toList()));
        String jsonStations = TestUtil.json(stations);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URL + "/replace",
                jsonStations, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
    }

    /**
     * Test with too long name value
     */
    @Test
    public void replaceAllWithLongName() throws Exception {
        NEW_STATIONS.get(0).setName(NEW_NAME_LONG_LENGTH);
        StationCollectionDTO stations =
                new StationCollectionDTO(NEW_STATIONS.stream().map(StationDTO::new).collect(Collectors.toList()));
        String jsonStations = TestUtil.json(stations);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URL + "/replace",
                jsonStations, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
    }

    /**
     * Test with min length name value
     */
    @Test
    public void replaceAllWithMinLengthName() throws Exception {
        NEW_STATIONS.get(0).setName(NEW_NAME_MIN_LENGTH);
        StationCollectionDTO stations =
                new StationCollectionDTO(NEW_STATIONS.stream().map(StationDTO::new).collect(Collectors.toList()));
        String jsonStations = TestUtil.json(stations);

        ResponseEntity<StationDTO[]> result = testRestTemplate.postForEntity(this.URL + "/replace",
                jsonStations, StationDTO[].class);

        StationDTO[] body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body).hasSize(stations.getStations().size());
    }

    /**
     * Test with max length name value
     */
    @Test
    public void replaceAllWithMaxLengthName() throws Exception {
        NEW_STATIONS.get(0).setName(NEW_NAME_MAX_LENGTH);
        StationCollectionDTO stations =
                new StationCollectionDTO(NEW_STATIONS.stream().map(StationDTO::new).collect(Collectors.toList()));
        String jsonStations = TestUtil.json(stations);

        ResponseEntity<StationDTO[]> result = testRestTemplate.postForEntity(this.URL + "/replace",
                jsonStations, StationDTO[].class);

        StationDTO[] body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body).hasSize(stations.getStations().size());
    }
}