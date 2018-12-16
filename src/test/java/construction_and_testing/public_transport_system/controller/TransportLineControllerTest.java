package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.domain.DTO.TransportLineColletionDTO;
import construction_and_testing.public_transport_system.domain.DTO.TransportLineDTO;
import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.domain.TransportLinePosition;
import construction_and_testing.public_transport_system.repository.ScheduleRepository;
import construction_and_testing.public_transport_system.repository.VehicleRepository;
import construction_and_testing.public_transport_system.service.definition.TransportLineService;
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

    @Before
    public void setUp() throws Exception {
        Mockito.doNothing().when(transportLineController).validateJSON(any(String.class), any(String.class));
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
    public void save() throws Exception {
        TransportLineDTO transportLine = new TransportLineDTO(
                new TransportLine(null, NEW_NAME, NEW_TYPE, NEW_POSITION, new HashSet<>(), DB_ZONE, true));
        String jsonTransportLine = TestUtil.json(transportLine);

        ResponseEntity<TransportLineDTO> result = testRestTemplate.postForEntity(this.URL, jsonTransportLine,
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
    public void saveWithNullValues() throws Exception {
        TransportLineDTO transportLine = new TransportLineDTO(
                new TransportLine(null, null, null, null, null, null, true));
        String jsonTransportLine = TestUtil.json(transportLine);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URL, jsonTransportLine, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Transport lines have invalid schedule or position associated!");
    }

    /**
     * Test transport line position has long id instead of null
     */
    @Test
    public void saveWithInvalidFormat() throws Exception {
        TransportLineDTO transportLine = new TransportLineDTO(
                new TransportLine(null, NEW_NAME, NEW_TYPE,
                        new TransportLinePosition(22L, "", null, true),
                        new HashSet<>(), DB_ZONE, true));
        String jsonTransportLine = TestUtil.json(transportLine);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URL, jsonTransportLine, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Transport lines have invalid schedule or position associated!");
    }

    /**
     * Test transport line with not unique name
     */
    @Test
    public void saveWithInvalidName() throws Exception {
        TransportLineDTO transportLine = new TransportLineDTO(
                new TransportLine(null, DB_NAME, NEW_TYPE, NEW_POSITION, new HashSet<>(), DB_ZONE, true));
        String jsonTransportLine = TestUtil.json(transportLine);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URL, jsonTransportLine, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Transport line with given name already exist!");
    }

    /**
     * Test valid transport line deletion
     */
    @Test
    public void delete() throws Exception {
        TransportLineDTO transportLine = new TransportLineDTO(
                new TransportLine(DEL_ID, DB_NAME, NEW_TYPE, NEW_POSITION, new HashSet<>(), DB_ZONE, true));
        String jsonTransportLine = TestUtil.json(transportLine);

        testRestTemplate.delete(this.URL, jsonTransportLine, String.class);
    }

    /**
     * Test valid transport line deletion
     */
    @Test
    public void deleteWithInvalidId() throws Exception {
        TransportLineDTO transportLine = new TransportLineDTO(
                new TransportLine(DEL_ID_INVALID, DB_NAME, NEW_TYPE, NEW_POSITION, new HashSet<>(), DB_ZONE, true));
        String jsonTransportLine = TestUtil.json(transportLine);

        testRestTemplate.delete(this.URL, jsonTransportLine, String.class);
    }

    /**
     * Test valid replacement
     */
    @Test
    public void replaceAll() throws Exception {
        TransportLineColletionDTO transportLines =
                new TransportLineColletionDTO(NEW_TRANSPORT_LINES.stream()
                        .map(TransportLineDTO::new).collect(Collectors.toList()));
        String jsonTransportLine = TestUtil.json(transportLines);

        ResponseEntity<TransportLineDTO[]> result = testRestTemplate.postForEntity(this.URL + "/replace",
                jsonTransportLine, TransportLineDTO[].class);

        TransportLineDTO[] body = result.getBody();

        assertThat(body).isNotNull();
        assertThat(body.length).isEqualTo(NEW_TRANSPORT_LINES.size());
        assertThat(scheduleRepository.findAll().size()).isEqualTo(DB_SCHEDULES_COUNT - DEL_SCHEDULE_COUNT);
        vehicleRepository.findAll().forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNotNull());
    }

    /**
     * Test with schedule associated to wrong transport line
     */
    @Test
    public void replaceAllWithInvalidScheduleAssociation() throws Exception {
        TransportLineColletionDTO transportLines =
                new TransportLineColletionDTO(NEW_TRANSPORT_LINES_INVALID_SCHEDULE_ASSOCIATION.stream()
                        .map(TransportLineDTO::new).collect(Collectors.toList()));
        String jsonTransportLine = TestUtil.json(transportLines);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URL + "/replace",
                jsonTransportLine, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Schedule has already associated to another transport line!");
    }

    /**
     * Test replacement transport lines with no zone
     */
    @Test
    public void replaceAllWithNoZone() throws Exception {
        TransportLineColletionDTO transportLines =
                new TransportLineColletionDTO(NEW_TRANSPORT_LINES_NO_ZONE.stream()
                        .map(TransportLineDTO::new).collect(Collectors.toList()));
        String jsonTransportLine = TestUtil.json(transportLines);

        ResponseEntity<TransportLineDTO[]> result = testRestTemplate.postForEntity(this.URL + "/replace",
                jsonTransportLine, TransportLineDTO[].class);

        TransportLineDTO[] body = result.getBody();

        assertThat(body).isNotNull();
        assertThat(body.length).isEqualTo(NEW_TRANSPORT_LINES_NO_ZONE.size());
        assertThat(scheduleRepository.findAll()).isEmpty();
        vehicleRepository.findAll().forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNull());
    }

    /**
     * Test replacement transport lines with null schedule
     */
    @Test
    public void replaceAllWithNullSchedule() throws Exception {
        TransportLineColletionDTO transportLines =
                new TransportLineColletionDTO(NEW_TRANSPORT_LINES_NO_SCHEDULE.stream()
                        .map(TransportLineDTO::new).collect(Collectors.toList()));
        String jsonTransportLine = TestUtil.json(transportLines);

        ResponseEntity<TransportLineDTO[]> result = testRestTemplate.postForEntity(this.URL + "/replace",
                jsonTransportLine, TransportLineDTO[].class);

        TransportLineDTO[] body = result.getBody();

        assertThat(body).isNotNull();
        assertThat(body.length).isEqualTo(NEW_TRANSPORT_LINES_NO_SCHEDULE.size());
        assertThat(scheduleRepository.findAll()).isEmpty();
        vehicleRepository.findAll().forEach(vehicle -> assertThat(vehicle.getCurrentLine()).isNull());
    }

    /**
     * Test replacement with schedule that does not exist in database
     */
    @Test
    public void replaceAllWithInvalidSchedule() throws Exception {
        TransportLineColletionDTO transportLines =
                new TransportLineColletionDTO(NEW_TRANSPORT_LINES_INVALID_SCHEDULE.stream()
                        .map(TransportLineDTO::new).collect(Collectors.toList()));
        String jsonTransportLine = TestUtil.json(transportLines);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URL + "/replace",
                jsonTransportLine, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Schedule associated to transport line N7 does not exist!");
    }

}