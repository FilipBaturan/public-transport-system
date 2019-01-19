package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.domain.DTO.ScheduleDTO;
import construction_and_testing.public_transport_system.domain.DTO.ZoneDTO;
import construction_and_testing.public_transport_system.domain.Schedule;
import construction_and_testing.public_transport_system.domain.enums.DayOfWeek;
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
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static construction_and_testing.public_transport_system.constants.ScheduleConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
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
        System.out.println(this.URL);
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

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(body).isNotNull();

        assertThat(body.getId()).isEqualTo(DB_VALID_ID);
        //assertThat(schedule.getTransportLine().getName()).isEqualTo(DB_TL_NAME);
        assertThat(body.getDayOfWeek()).isEqualTo(DB_VALID_DAY_OF_WEEK);
        //assertThat(schedule.getDepartures()).isEqualTo(DB_VALID_DEPARTURES);
        //assertThat(body.getDepartures().size()).isEqualTo(DB_VALID_DEPARTURES_SIZE);
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
     * Test valid schedule saving
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

    /**
     * Test valid schedule updating
     */
    @Test
    public void update() throws Exception {
        ScheduleDTO scheduleDTO = new ScheduleDTO(
                new Schedule(104L, DB_INVALID_TRANSPORT_LINE, DayOfWeek.WORKDAY, DB_NEW_DEPARTURES, true));

        ResponseEntity<Boolean> result = testRestTemplate
                .exchange(this.URL + "/updateSchedule/", HttpMethod.PUT,
                        new HttpEntity<ScheduleDTO>(scheduleDTO), Boolean.class);

        Boolean body = result.getBody();

        Schedule updatedSchedule = this.scheduleService.findById(104L);

        assertThat(body).isTrue();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updatedSchedule).isNotNull();
        assertThat(updatedSchedule.getTransportLine().getName()).isEqualTo(DB_INVALID_TRANSPORT_LINE.getName());
        assertThat(updatedSchedule.getDayOfWeek()).isEqualTo(scheduleDTO.getDayOfWeek());
        assertThat(updatedSchedule.getDepartures().size()).isEqualTo(scheduleDTO.getDepartures().size());
        int i = 0;
        for (String departure : updatedSchedule.getDepartures()) {
            assertThat(departure).isEqualTo(scheduleDTO.getDepartures().get(i));
            i++;
        }
        assertThat(updatedSchedule.isActive()).isEqualTo(true);
    }

    /**
     * Test invalid schedule updating (schedule doesn't exist)
     */
    @Test
    public void invalidUpdateScheduleDoesntExist() throws Exception {
        ScheduleDTO scheduleDTO = new ScheduleDTO(
                new Schedule(204L, DB_INVALID_TRANSPORT_LINE, DayOfWeek.WORKDAY, DB_NEW_DEPARTURES, true));

        ResponseEntity<Boolean> result = testRestTemplate
                .exchange(this.URL + "/updateSchedule/", HttpMethod.PUT,
                        new HttpEntity<ScheduleDTO>(scheduleDTO), Boolean.class);

        Boolean body = result.getBody();

        assertThat(body).isFalse();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * Test invalid schedule updating (transport line doesn't exist)
     */
    @Test
    public void invalidUpdateTransportLineDoesntExist() throws Exception {
        ScheduleDTO scheduleDTO = new ScheduleDTO(
                new Schedule(null, DB_INVALID_TRANSPORT_LINE, DayOfWeek.WORKDAY, DB_NEW_DEPARTURES, true));

        ResponseEntity<Boolean> result = testRestTemplate
                .exchange(this.URL + "/updateSchedule/", HttpMethod.PUT,
                        new HttpEntity<ScheduleDTO>(scheduleDTO), Boolean.class);

        Boolean body = result.getBody();

        assertThat(body).isFalse();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * Test valid schedule logical deleting
     */
    @Test
    public void logicalDeletion() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        //headers.add("X-Auth-Token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJudWxsIiwiYXVkaWVuY2UiOiJ3ZWIiLCJjcmVhdGVkIjoxNTQ2OTU4NzE1Njc3LCJleHAiOjE1NDY5OTQ3MTUsImF1dGhvcml0aWVzIjoiT1BFUkFURVIifQ.yJo-JOt-5iQ8SIxw0hM57TDFl9ZlruH9tB0-EM_-0wB2FspXHuE1VCWDJW7_bRStn-P0J1Q-Lx62x2fDYWNJ_g");
        HttpEntity<ZoneDTO> httpEntity = new HttpEntity<>(headers);
        System.out.println(this.URL + '/' + DB_VALID_ID);
        ResponseEntity<String> result = testRestTemplate
                .exchange(this.URL + '/' + DB_VALID_ID, HttpMethod.DELETE,
                        httpEntity, String.class);

        String body = result.getBody();

        Schedule updatedSchedule = this.scheduleService.findById(100L);

        assertThat(body).isEqualTo("Schedule successfully deleted!");
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updatedSchedule).isNotNull();
        assertThat(updatedSchedule.getTransportLine().getName()).isEqualTo(DB_INVALID_TRANSPORT_LINE.getName());
        /*assertThat(updatedSchedule.getDayOfWeek()).isEqualTo(scheduleDTO.getDayOfWeek());
        assertThat(updatedSchedule.getDepartures().size()).isEqualTo(scheduleDTO.getDepartures().size());
        int i = 0;
        for (String departure: updatedSchedule.getDepartures()) {
            assertThat(departure).isEqualTo(scheduleDTO.getDepartures().get(i));
            i++;
        }*/
        assertThat(updatedSchedule.isActive()).isEqualTo(true);
    }

    /**
     * Test with null values
     */
    /*@Test
    public void saveWithNullValues() throws Exception {
        ScheduleDTO scheduleDTO = new ScheduleDTO(
                new Schedule(null, null, null, null, true));
        String jsonSchedule = TestUtil.json(scheduleDTO);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URL, jsonSchedule, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Transport lines have invalid schedule or position associated!");
    }*/
}


