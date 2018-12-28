package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.domain.DTO.ScheduleDTO;
import construction_and_testing.public_transport_system.domain.DTO.TransportLineDTO;
import construction_and_testing.public_transport_system.domain.Schedule;
import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.service.definition.ScheduleService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static construction_and_testing.public_transport_system.constants.ScheduleConstants.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ScheduleControllerTest {

    private final String URL = "/api/schedule";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ScheduleService scheduleService;

    @SpyBean
    private ScheduleController scheduleController;

    @Before
    public void setUp() throws Exception {
        Mockito.doNothing().when(scheduleController).validateJSON(any(String.class), any(String.class));
    }

    /**
     * Test get all transport lines from database
     */
    @Test
    public void findAll() {
        ResponseEntity<ScheduleDTO[]> result = testRestTemplate
                .getForEntity(this.URL, ScheduleDTO[].class);

        ScheduleDTO[] body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body).hasSize(DB_COUNT);
    }

    /**
     * Test with valid id
     */
    @Test
    public void findById() {
        ResponseEntity<ScheduleDTO> result = testRestTemplate
                .getForEntity(this.URL + "/" + DB_VALID_ID, ScheduleDTO.class);

        ScheduleDTO body = result.getBody();
        System.out.println(this.URL + "/" + DB_VALID_ID);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(body).isNotNull();

        assertThat(body.getId()).isEqualTo(DB_VALID_ID);
        //assertThat(schedule.getTransportLine().getName()).isEqualTo(DB_TL_NAME);
        assertThat(body.getDayOfWeek()).isEqualTo(DB_VALID_DAY_OF_WEEK);
        //assertThat(schedule.getDepartures()).isEqualTo(DB_VALID_DEPARTURES);
        assertThat(body.getDepartures().size()).isEqualTo(DB_VALID_DEPARTURES_SIZE);
        assertThat(body.isActive()).isEqualTo(DB_VALID_ACTIVE);
    }

    /**
     * Test with invalid id
     */
    @Test
    public void findByInvalidId() {
        ResponseEntity<String> result = testRestTemplate
                .getForEntity(this.URL + "/" + DB_INVALID_ID, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Requested schedule does not exist!");
    }

    /**
     * Test valid transport line saving
     */
    @Test
    public void save() throws Exception {
        ScheduleDTO scheduleDTO = new ScheduleDTO(
                new Schedule(null, DB_TRANSPORT_LINE, DB_VALID_DAY_OF_WEEK, DB_NEW_DEPARTURES, true));
        String jsonSchedule = TestUtil.json(scheduleDTO);

        ResponseEntity<ScheduleDTO> result = testRestTemplate.postForEntity(this.URL, jsonSchedule,
                ScheduleDTO.class);

        ScheduleDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(body).isNotNull();
        //assertThat(schedule.getTransportLine().getName()).isEqualTo(DB_TL_NAME);
        assertThat(body.getDayOfWeek()).isEqualTo(scheduleDTO.getDayOfWeek());
        //assertThat(schedule.getDepartures()).isEqualTo(DB_VALID_DEPARTURES);
        assertThat(body.getDepartures().size()).isEqualTo(scheduleDTO.getDepartures().size());
        assertThat(body.isActive()).isEqualTo(true);
    }

    /**
     * Test with null values
     */
    @Test
    public void saveWithNullValues() throws Exception {
        ScheduleDTO scheduleDTO = new ScheduleDTO(
                new Schedule(null, null, null, null, true));
        String jsonSchedule = TestUtil.json(scheduleDTO);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URL, jsonSchedule, String.class);

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
        ScheduleDTO scheduleDTO = new ScheduleDTO(
                new Schedule(null, DB_INVALID_TRANSPORT_LINE, DB_VALID_DAY_OF_WEEK, DB_NEW_DEPARTURES, true));
        String jsonSchedule = TestUtil.json(scheduleDTO);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URL, jsonSchedule, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Requested transport line does not exist!");
    }



}


