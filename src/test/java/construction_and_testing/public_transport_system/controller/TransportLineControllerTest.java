package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.domain.DTO.AuthenticationRequestDTO;
import construction_and_testing.public_transport_system.domain.DTO.AuthenticationResponseDTO;
import construction_and_testing.public_transport_system.domain.DTO.TransportLineCollectionDTO;
import construction_and_testing.public_transport_system.domain.DTO.TransportLineDTO;
import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.domain.TransportLinePosition;
import construction_and_testing.public_transport_system.repository.ScheduleRepository;
import construction_and_testing.public_transport_system.repository.VehicleRepository;
import construction_and_testing.public_transport_system.service.definition.TransportLineService;
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
import java.util.HashSet;
import java.util.stream.Collectors;

import static construction_and_testing.public_transport_system.constants.TransportLineConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransportLineControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TransportLineService transportLineService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @SpyBean
    private TransportLineController transportLineController;

    private final String URL = "/api/transportLine";

    private String accessToken;

    private void setUnauthorizedUser() {
        ResponseEntity<AuthenticationResponseDTO> auth = testRestTemplate.postForEntity("/api/user/auth",
                new AuthenticationRequestDTO("username", "password"),
                AuthenticationResponseDTO.class);
        if (auth.getBody() == null) {
            return;
        }
        accessToken = auth.getBody().getToken();
    }

    @Before
    public void setUp() throws Exception {
        Mockito.doNothing().when(transportLineController).validateJSON(any(String.class), any(String.class));
        ResponseEntity<AuthenticationResponseDTO> responseEntity = testRestTemplate.postForEntity("/api/user/auth",
                new AuthenticationRequestDTO("null", "null"),
                AuthenticationResponseDTO.class);
        if (responseEntity.getBody() != null) {
            accessToken = responseEntity.getBody().getToken();
        }
    }

    /**
     * Test get all transport lines from database
     */
    @Test
    public void findAll() {
        ResponseEntity<TransportLineDTO[]> result = testRestTemplate
                .getForEntity(this.URL, TransportLineDTO[].class);

        TransportLineDTO[] body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body).hasSize(DB_COUNT);
    }

    /**
     * Test with valid id
     */
    @Test
    public void findById() {
        ResponseEntity<TransportLineDTO> result = testRestTemplate
                .getForEntity(this.URL + "/" + DB_ID, TransportLineDTO.class);

        TransportLineDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(body).isNotNull();
        assertThat(body.getId()).isEqualTo(DB_ID);
        assertThat(body.getName()).isEqualTo(DB_NAME);
        assertThat(body.getType()).isEqualTo(DB_TYPE);
        assertThat(body.getPositions().getId()).isEqualTo(DB_POSITION.getId());
        assertThat(body.getSchedule().size()).isEqualTo(DB_SCHEDULE_COUNT);
        assertThat(body.getZone()).isEqualTo(DB_ZONE.getId());
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
        assertThat(body).isEqualTo("Requested transport line does not exist!");
    }

    /**
     * Test valid transport line saving
     */
    @Test
    public void save() {
        TransportLineDTO transportLine = new TransportLineDTO(
                new TransportLine(null, NEW_NAME, NEW_TYPE, new TransportLinePosition(null,
                        "45.23,26.24  44.74,36.12 (green|" + NEW_NAME + ")", null, true),
                        new HashSet<>(), DB_ZONE, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineDTO> httpEntity = new HttpEntity<>(transportLine, headers);

        ResponseEntity<TransportLineDTO> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                TransportLineDTO.class);

        TransportLineDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(transportLine.getName());
        assertThat(body.getType()).isEqualTo(transportLine.getType());
        assertThat(body.getPositions().getContent()).isEqualTo(transportLine.getPositions().getContent());
        assertThat(body.getSchedule().size()).isEqualTo(transportLine.getSchedule().size());
        assertThat(body.getZone()).isEqualTo(transportLine.getZone());
        assertThat(body.isActive()).isEqualTo(transportLine.isActive());
    }

    /**
     * Test with null values
     */
    @Test
    public void saveWithNullValues() {
        TransportLineDTO transportLine = new TransportLineDTO(
                new TransportLine(null, null, null, null, null, null, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineDTO> httpEntity = new HttpEntity<>(transportLine, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Transport lines have invalid schedule or position associated!");
    }

    /**
     * Test transport line position has long id instead of null
     */
    @Test
    public void saveWithInvalidFormat() {
        TransportLineDTO transportLine = new TransportLineDTO(
                new TransportLine(null, NEW_NAME, NEW_TYPE,
                        new TransportLinePosition(22L, "", null, true),
                        new HashSet<>(), DB_ZONE, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineDTO> httpEntity = new HttpEntity<>(transportLine, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Transport lines have invalid schedule or position associated!");
    }

    /**
     * Test transport line with not unique firstName
     */
    @Test
    public void saveWithInvalidName() {
        TransportLineDTO transportLine = new TransportLineDTO(
                new TransportLine(null, DB_NAME, NEW_TYPE, new TransportLinePosition(), new HashSet<>(),
                        DB_ZONE, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineDTO> httpEntity = new HttpEntity<>(transportLine, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
    }

    /**
     * Test with to short firstName value
     */
    @Test
    public void saveWithShortName() {
        TransportLineDTO transportLine = new TransportLineDTO(
                new TransportLine(null, NEW_NAME_SHORT_LENGTH, NEW_TYPE,
                        NEW_POSITION, new HashSet<>(), DB_ZONE, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineDTO> httpEntity = new HttpEntity<>(transportLine, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
    }

    /**
     * Test with too long firstName value
     */
    @Test
    public void saveWithLongName() {
        TransportLineDTO transportLine = new TransportLineDTO(
                new TransportLine(null, NEW_NAME_LONG_LENGTH, NEW_TYPE,
                        NEW_POSITION, new HashSet<>(), DB_ZONE, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineDTO> httpEntity = new HttpEntity<>(transportLine, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
    }

    /**
     * Test with min length firstName value
     */
    @Test
    public void saveWithMinLengthName() {
        TransportLineDTO transportLine = new TransportLineDTO(
                new TransportLine(null, NEW_NAME_MIN_LENGTH, NEW_TYPE, new TransportLinePosition(null,
                        "45.23,26.24  44.74,36.12 (green|" + NEW_NAME_MIN_LENGTH + ")", null,
                        true), new HashSet<>(), DB_ZONE, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineDTO> httpEntity = new HttpEntity<>(transportLine, headers);

        ResponseEntity<TransportLineDTO> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                TransportLineDTO.class);

        TransportLineDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(transportLine.getName());
        assertThat(body.getType()).isEqualTo(transportLine.getType());
        assertThat(body.getPositions().getContent()).isEqualTo(transportLine.getPositions().getContent());
        assertThat(body.getSchedule().size()).isEqualTo(transportLine.getSchedule().size());
        assertThat(body.getZone()).isEqualTo(transportLine.getZone());
        assertThat(body.isActive()).isEqualTo(transportLine.isActive());
    }

    /**
     * Test with max length firstName value
     */
    @Test
    public void saveWithMaxLengthName() {
        TransportLineDTO transportLine = new TransportLineDTO(
                new TransportLine(null, NEW_NAME_MAX_LENGTH, NEW_TYPE, new TransportLinePosition(null,
                        "45.23,26.24  44.74,36.12 (green|" + NEW_NAME_MAX_LENGTH + ")", null,
                        true), new HashSet<>(), DB_ZONE, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineDTO> httpEntity = new HttpEntity<>(transportLine, headers);

        ResponseEntity<TransportLineDTO> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                TransportLineDTO.class);

        TransportLineDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(transportLine.getName());
        assertThat(body.getType()).isEqualTo(transportLine.getType());
        assertThat(body.getPositions().getContent()).isEqualTo(transportLine.getPositions().getContent());
        assertThat(body.getSchedule().size()).isEqualTo(transportLine.getSchedule().size());
        assertThat(body.getZone()).isEqualTo(transportLine.getZone());
        assertThat(body.isActive()).isEqualTo(transportLine.isActive());
    }

    /**
     * Test unauthorized user tries to save transport line
     */
    @Test
    public void saveUnauthorized() {
        setUnauthorizedUser();

        TransportLineDTO transportLine = new TransportLineDTO(
                new TransportLine(null, NEW_NAME, NEW_TYPE, new TransportLinePosition(null,
                        "45.23,26.24  44.74,36.12 (green|" + NEW_NAME + ")", null, true),
                        new HashSet<>(), DB_ZONE, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineDTO> httpEntity = new HttpEntity<>(transportLine, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(body).isNotNull();
    }

    /**
     * Test valid transport line deletion
     */
    @Test
    public void delete() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineDTO> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/" + DEL_ID,
                HttpMethod.DELETE, httpEntity, String.class);

        String body = result.getBody();

        assertThat(body).isEqualTo("Transport line successfully deleted!");
    }

    /**
     * Test valid transport line deletion
     */
    @Test
    public void deleteWithInvalidId() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineDTO> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/" + DEL_ID_INVALID,
                HttpMethod.DELETE, httpEntity, String.class);

        String body = result.getBody();

        assertThat(body).isEqualTo("Requested transport line does not exist!");
    }

    /**
     * Test unauthorized user tries to delete transport line
     */
    @Test
    public void deleteUnauthorized() {
        setUnauthorizedUser();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineDTO> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/" + DEL_ID,
                HttpMethod.DELETE, httpEntity, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(body).isNotNull();
    }

    /**
     * Test valid replacement
     */
    @Test
    public void replaceAll() {
        TransportLineCollectionDTO transportLines =
                new TransportLineCollectionDTO(NEW_TRANSPORT_LINES.stream()
                        .map(TransportLineDTO::new).collect(Collectors.toList()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineCollectionDTO> httpEntity = new HttpEntity<>(transportLines, headers);

        ResponseEntity<TransportLineDTO[]> result = testRestTemplate.exchange(this.URL + "/replace",
                HttpMethod.POST, httpEntity, TransportLineDTO[].class);

        TransportLineDTO[] body = result.getBody();

        assertThat(body).isNotNull();
        assertThat(body.length).isEqualTo(NEW_TRANSPORT_LINES.size());
        (new ArrayList<>(Arrays.asList(body))).forEach(transportLine -> {
            assertThat(transportLine.getId()).isNotNull();
            assertThat(transportLine.getName()).isIn(NEW_TRANSPORT_LINES
                    .stream().map(TransportLine::getName).collect(Collectors.toList()));
        });
        assertThat(scheduleRepository.findAll().size()).isEqualTo(DB_SCHEDULES_COUNT - DEL_SCHEDULE_COUNT_UN);
        vehicleRepository.findAll().forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNotNull());
    }

    /**
     * Test with schedule associated to wrong transport line
     */
    @Test
    public void replaceAllWithInvalidScheduleAssociation() {
        TransportLineCollectionDTO transportLines =
                new TransportLineCollectionDTO(NEW_TRANSPORT_LINES_INVALID_SCHEDULE_ASSOCIATION.stream()
                        .map(TransportLineDTO::new).collect(Collectors.toList()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineCollectionDTO> httpEntity = new HttpEntity<>(transportLines, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/replace",
                HttpMethod.POST, httpEntity, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Transport lines have invalid schedule or position associated!");
    }

    /**
     * Test replacement transport lines with no zone
     */
    @Test
    public void replaceAllWithNoZone() {
        TransportLineCollectionDTO transportLines =
                new TransportLineCollectionDTO(NEW_TRANSPORT_LINES_NO_ZONE.stream()
                        .map(TransportLineDTO::new).collect(Collectors.toList()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineCollectionDTO> httpEntity = new HttpEntity<>(transportLines, headers);

        ResponseEntity<TransportLineDTO[]> result = testRestTemplate.exchange(this.URL + "/replace",
                HttpMethod.POST, httpEntity, TransportLineDTO[].class);

        TransportLineDTO[] body = result.getBody();

        assertThat(body).isNotNull();
        assertThat(body.length).isEqualTo(NEW_TRANSPORT_LINES_NO_ZONE.size());
        (new ArrayList<>(Arrays.asList(body))).forEach(
                transportLine -> assertThat(transportLine.getZone()).isEqualTo(DEFAULT_ZONE_ID));
        assertThat(scheduleRepository.findAll()).isEmpty();
        vehicleRepository.findAll().forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNull());
    }

    /**
     * Test replacement transport lines with null schedule
     */
    @Test
    public void replaceAllWithNullSchedule() {
        TransportLineCollectionDTO transportLines =
                new TransportLineCollectionDTO(NEW_TRANSPORT_LINES_NO_SCHEDULE.stream()
                        .map(TransportLineDTO::new).collect(Collectors.toList()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineCollectionDTO> httpEntity = new HttpEntity<>(transportLines, headers);

        ResponseEntity<TransportLineDTO[]> result = testRestTemplate.exchange(this.URL + "/replace",
                HttpMethod.POST, httpEntity, TransportLineDTO[].class);

        TransportLineDTO[] body = result.getBody();

        assertThat(body).isNotNull();
        assertThat(body.length).isEqualTo(NEW_TRANSPORT_LINES_NO_SCHEDULE.size());
        (new ArrayList<>(Arrays.asList(body))).forEach(
                transportLine -> assertThat(transportLine.getSchedule().size()).isEqualTo(0));
        assertThat(scheduleRepository.findAll()).isEmpty();
        vehicleRepository.findAll().forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNull());
    }

    /**
     * Test replacement with schedule that does not exist in database
     */
    @Test
    public void replaceAllWithInvalidSchedule() {
        TransportLineCollectionDTO transportLines =
                new TransportLineCollectionDTO(NEW_TRANSPORT_LINES_INVALID_SCHEDULE.stream()
                        .map(TransportLineDTO::new).collect(Collectors.toList()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineCollectionDTO> httpEntity = new HttpEntity<>(transportLines, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/replace",
                HttpMethod.POST, httpEntity, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Transport lines have invalid schedule or position associated!");
    }

    /**
     * Test with too short firstName value
     */
    @Test
    public void replaceAllWithShortName() {
        NEW_TRANSPORT_LINES.get(0).setName(NEW_NAME_SHORT_LENGTH);
        TransportLineCollectionDTO transportLines =
                new TransportLineCollectionDTO(NEW_TRANSPORT_LINES.stream()
                        .map(TransportLineDTO::new).collect(Collectors.toList()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineCollectionDTO> httpEntity = new HttpEntity<>(transportLines, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/replace",
                HttpMethod.POST, httpEntity, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
    }

    /**
     * Test with too long firstName value
     */
    @Test
    public void replaceAllWithLongName() {
        NEW_TRANSPORT_LINES.get(0).setName(NEW_NAME_LONG_LENGTH);
        TransportLineCollectionDTO transportLines =
                new TransportLineCollectionDTO(NEW_TRANSPORT_LINES.stream()
                        .map(TransportLineDTO::new).collect(Collectors.toList()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineCollectionDTO> httpEntity = new HttpEntity<>(transportLines, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/replace",
                HttpMethod.POST, httpEntity, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
    }

    /**
     * Test with min length firstName value
     */
    @Test
    public void replaceAllWithMinLengthName() {
        NEW_TRANSPORT_LINES.get(0).setName(NEW_NAME_MIN_LENGTH);
        TransportLineCollectionDTO transportLines =
                new TransportLineCollectionDTO(NEW_TRANSPORT_LINES.stream()
                        .map(TransportLineDTO::new).collect(Collectors.toList()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineCollectionDTO> httpEntity = new HttpEntity<>(transportLines, headers);

        ResponseEntity<TransportLineDTO[]> result = testRestTemplate.exchange(this.URL + "/replace",
                HttpMethod.POST, httpEntity, TransportLineDTO[].class);

        TransportLineDTO[] body = result.getBody();

        assertThat(body).isNotNull();
        assertThat(body.length).isEqualTo(NEW_TRANSPORT_LINES.size());
        assertThat(scheduleRepository.findAll().size()).isEqualTo(DB_SCHEDULES_COUNT - DEL_SCHEDULE_COUNT_UN);
        vehicleRepository.findAll().forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNotNull());
    }

    /**
     * Test with max length firstName value
     */
    @Test
    public void replaceAllWithMaxLengthName() {
        NEW_TRANSPORT_LINES.get(0).setName(NEW_NAME_MAX_LENGTH);
        TransportLineCollectionDTO transportLines =
                new TransportLineCollectionDTO(NEW_TRANSPORT_LINES.stream()
                        .map(TransportLineDTO::new).collect(Collectors.toList()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineCollectionDTO> httpEntity = new HttpEntity<>(transportLines, headers);

        ResponseEntity<TransportLineDTO[]> result = testRestTemplate.exchange(this.URL + "/replace",
                HttpMethod.POST, httpEntity, TransportLineDTO[].class);

        TransportLineDTO[] body = result.getBody();

        assertThat(body).isNotNull();
        assertThat(body.length).isEqualTo(NEW_TRANSPORT_LINES.size());
        assertThat(scheduleRepository.findAll().size()).isEqualTo(DB_SCHEDULES_COUNT - DEL_SCHEDULE_COUNT_UN);
        vehicleRepository.findAll().forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNotNull());
    }

    /**
     * Test unauthorized user tries to replace transport lines
     */
    @Test
    public void replaceAllUnauthorized() {
        setUnauthorizedUser();

        TransportLineCollectionDTO transportLines =
                new TransportLineCollectionDTO(NEW_TRANSPORT_LINES_NO_ZONE.stream()
                        .map(TransportLineDTO::new).collect(Collectors.toList()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<TransportLineCollectionDTO> httpEntity = new HttpEntity<>(transportLines, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/replace",
                HttpMethod.POST, httpEntity, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(body).isNotNull();
    }
}