package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.domain.DTO.AuthenticationRequestDTO;
import construction_and_testing.public_transport_system.domain.DTO.AuthenticationResponseDTO;
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
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
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

    private String accessToken;

    private void setUnauthorizedUser(){
        ResponseEntity<AuthenticationResponseDTO> auth = testRestTemplate.postForEntity("/api/user/auth",
                new AuthenticationRequestDTO("username", "password"),
                AuthenticationResponseDTO.class);
        if( auth.getBody() == null){
            return;
        }
        accessToken = auth.getBody().getToken();
    }

    @Before
    public void setUp() throws Exception {
        Mockito.doNothing().when(stationController).validateJSON(any(String.class), any(String.class));
        ResponseEntity<AuthenticationResponseDTO> responseEntity = testRestTemplate.postForEntity("/api/user/auth",
                new AuthenticationRequestDTO("null", "null"),
                AuthenticationResponseDTO.class);
        if (responseEntity.getBody() != null){
            accessToken = responseEntity.getBody().getToken();
        }
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
    public void save() {
        StationDTO station = new StationDTO(new Station(null, NEW_NAME, new StationPosition(),
                NEW_TYPE, NEW_ACTIVE));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<StationDTO> httpEntity = new HttpEntity<>(station, headers);

        ResponseEntity<StationDTO> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                StationDTO.class);

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
    public void saveWithNullValues() {
        StationDTO station = new StationDTO(new Station(null, null, null, null, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<StationDTO> httpEntity = new HttpEntity<>(station, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Invalid vehicle data!");
    }

    /**
     * Test with to short name value
     */
    @Test
    public void saveWithShortName() {
        StationDTO station = new StationDTO(
                new Station(null, NEW_NAME_SHORT_LENGTH, new StationPosition(), NEW_TYPE, NEW_ACTIVE));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<StationDTO> httpEntity = new HttpEntity<>(station, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
    }

    /**
     * Test with too long name value
     */
    @Test
    public void saveWithLongName() {
        StationDTO station = new StationDTO(
                new Station(null, NEW_NAME_LONG_LENGTH, new StationPosition(), NEW_TYPE, NEW_ACTIVE));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<StationDTO> httpEntity = new HttpEntity<>(station, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
    }

    /**
     * Test with min length name value
     */
    @Test
    public void saveWithMinLengthName() {
        StationDTO station = new StationDTO(
                new Station(null, NEW_NAME_MIN_LENGTH, new StationPosition(), NEW_TYPE, NEW_ACTIVE));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<StationDTO> httpEntity = new HttpEntity<>(station, headers);

        ResponseEntity<StationDTO> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                StationDTO.class);

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
    public void saveWithMaxLengthName() {
        StationDTO station = new StationDTO(
                new Station(null, NEW_NAME_MAX_LENGTH, new StationPosition(), NEW_TYPE, NEW_ACTIVE));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<StationDTO> httpEntity = new HttpEntity<>(station, headers);

        ResponseEntity<StationDTO> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                StationDTO.class);

        StationDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(station.getName());
        assertThat(body.getType()).isEqualTo(station.getType());
        assertThat(body.getPosition().getLongitude()).isEqualTo(station.getPosition().getLongitude());
        assertThat(body.isActive()).isEqualTo(station.isActive());
    }

    /**
     * Test unauthorized user tries to save station
     */
    @Test
    public void saveUnauthorized() {
        setUnauthorizedUser();

        StationDTO station = new StationDTO(new Station(null, NEW_NAME, new StationPosition(),
                NEW_TYPE, NEW_ACTIVE));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<StationDTO> httpEntity = new HttpEntity<>(station, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(body).isNotNull();
    }

    /**
     * Test valid station deletion
     */
    @Test
    public void delete() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<StationDTO> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/" + DEL_ID,
                HttpMethod.DELETE, httpEntity, String.class);

        String body = result.getBody();

        assertThat(body).isEqualTo("Station successfully deleted!");
    }

    /**
     * Test station deletion that does not exist in database
     */
    @Test
    public void deleteWithInvalidId() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<StationDTO> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/" + DEL_ID_INVALID,
                HttpMethod.DELETE, httpEntity, String.class);

        String body = result.getBody();

        assertThat(body).isEqualTo("Requested station does not exist!");
    }

    /**
     * Test unauthorized user tries to delete station
     */
    @Test
    public void deleteUnauthorized() {
        setUnauthorizedUser();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<StationDTO> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/" + DEL_ID,
                HttpMethod.DELETE, httpEntity, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(body).isNotNull();
    }

    /**
     * Test replace all station
     */
    @Test
    public void replaceAll() {
        StationCollectionDTO stations =
                new StationCollectionDTO(NEW_STATIONS.stream().map(StationDTO::new).collect(Collectors.toList()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<StationCollectionDTO> httpEntity = new HttpEntity<>(stations, headers);

        ResponseEntity<StationDTO[]> result = testRestTemplate.exchange(this.URL + "/replace", HttpMethod.POST, httpEntity,
                StationDTO[].class);

        StationDTO[] body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body).hasSize(stations.getStations().size());
        (new ArrayList<>(Arrays.asList(body))).forEach(transportLine -> {
            assertThat(transportLine.getId()).isNotNull();
            assertThat(transportLine.getName()).isIn(NEW_STATIONS
                    .stream().map(Station::getName).collect(Collectors.toList()));
        });

    }

    /**
     * Test with too short name value
     */
    @Test
    public void replaceAllWithShortName() {
        NEW_STATIONS.get(0).setName(NEW_NAME_SHORT_LENGTH);
        StationCollectionDTO stations =
                new StationCollectionDTO(NEW_STATIONS.stream().map(StationDTO::new).collect(Collectors.toList()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<StationCollectionDTO> httpEntity = new HttpEntity<>(stations, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/replace", HttpMethod.POST, httpEntity,
                String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
    }

    /**
     * Test with too long name value
     */
    @Test
    public void replaceAllWithLongName() {
        NEW_STATIONS.get(0).setName(NEW_NAME_LONG_LENGTH);
        StationCollectionDTO stations =
                new StationCollectionDTO(NEW_STATIONS.stream().map(StationDTO::new).collect(Collectors.toList()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<StationCollectionDTO> httpEntity = new HttpEntity<>(stations, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/replace", HttpMethod.POST, httpEntity,
                String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
    }

    /**
     * Test with min length name value
     */
    @Test
    public void replaceAllWithMinLengthName() {
        NEW_STATIONS.get(0).setName(NEW_NAME_MIN_LENGTH);
        StationCollectionDTO stations =
                new StationCollectionDTO(NEW_STATIONS.stream().map(StationDTO::new).collect(Collectors.toList()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<StationCollectionDTO> httpEntity = new HttpEntity<>(stations, headers);

        ResponseEntity<StationDTO[]> result = testRestTemplate.exchange(this.URL + "/replace", HttpMethod.POST, httpEntity,
                StationDTO[].class);

        StationDTO[] body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body).hasSize(stations.getStations().size());
    }

    /**
     * Test with max length name value
     */
    @Test
    public void replaceAllWithMaxLengthName() {
        NEW_STATIONS.get(0).setName(NEW_NAME_MAX_LENGTH);
        StationCollectionDTO stations =
                new StationCollectionDTO(NEW_STATIONS.stream().map(StationDTO::new).collect(Collectors.toList()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<StationCollectionDTO> httpEntity = new HttpEntity<>(stations, headers);

        ResponseEntity<StationDTO[]> result = testRestTemplate.exchange(this.URL + "/replace", HttpMethod.POST, httpEntity,
                StationDTO[].class);

        StationDTO[] body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body).hasSize(stations.getStations().size());
    }

    /**
     * Test unauthorized user tries to replace stations
     */
    @Test
    public void replaceAllUnauthorized() {
        setUnauthorizedUser();

        StationCollectionDTO stations =
                new StationCollectionDTO(NEW_STATIONS.stream().map(StationDTO::new).collect(Collectors.toList()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<StationCollectionDTO> httpEntity = new HttpEntity<>(stations, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/replace",
                HttpMethod.POST, httpEntity, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(body).isNotNull();
    }
}