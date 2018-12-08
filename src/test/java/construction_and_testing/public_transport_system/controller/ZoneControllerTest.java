package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.domain.DTO.ZoneDTO;
import construction_and_testing.public_transport_system.domain.Zone;
import construction_and_testing.public_transport_system.service.definition.ZoneService;
import construction_and_testing.public_transport_system.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.HashSet;

import static construction_and_testing.public_transport_system.constants.ZoneConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ZoneControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ZoneService zoneService;

    @SpyBean
    private ZoneController zoneController;

    private final String URI = "/api/zone";

    @Before
    public void setUp() throws Exception {
        Mockito.doNothing().when(zoneController).validateJSON(any(String.class), any(String.class));
    }

    /**
     * Test get all zone from database
     */
    @Test
    public void getAll() {
        ResponseEntity<ZoneDTO[]> result = testRestTemplate
                .getForEntity(this.URI, ZoneDTO[].class);

        ZoneDTO[] body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body).hasSize(DB_COUNT);
    }

    /**
     * Test with valid id
     */
    @Test
    public void findById() {
        ResponseEntity<ZoneDTO> result = testRestTemplate
                .getForEntity(this.URI + "/" + DB_ID, ZoneDTO.class);

        ZoneDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(body).isNotNull();
        assertThat(body.getId()).isEqualTo(DB_ID);
        assertThat(body.getName()).isEqualTo(DB_NAME);
        assertThat(body.isActive()).isEqualTo(DB_ACTIVE);
        assertThat(body.getLines().size()).isEqualTo(DB_TR_COUNT);
    }

    /**
     * Test with invalid id
     */
    @Test
    public void findByNotValidId() {
        ResponseEntity<String> result = testRestTemplate
                .getForEntity(this.URI + "/" + DB_ID_INVALID, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Requested zone does not exist!");
    }

    /**
     * Test zone with no transport lines
     */
    @Test
    @Transactional
    public void saveWithNoLines() throws Exception {
        ZoneDTO zone = new ZoneDTO(new Zone(null, NEW_NAME, new HashSet<>(), true));
        String jsonZone = TestUtil.json(zone);

        ResponseEntity<ZoneDTO> result = testRestTemplate.postForEntity(this.URI, jsonZone, ZoneDTO.class);

        ZoneDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(zone.getName());
        assertThat(body.getLines()).isEqualTo(zone.getLines());
        assertThat(body.isActive()).isEqualTo(zone.isActive());
    }

    /**
     * Test zone with transport lines
     */
    @Test
    @Transactional
    public void saveWithLines() throws Exception {
        ZoneDTO zone = new ZoneDTO(new Zone(null, NEW_NAME, NEW_LINES, true));
        String jsonZone = TestUtil.json(zone);

        ResponseEntity<ZoneDTO> result = testRestTemplate.postForEntity(this.URI, jsonZone, ZoneDTO.class);

        ZoneDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(zone.getName());
        assertThat(body.getLines()).hasSize(zone.getLines().size());
        assertThat(body.isActive()).isEqualTo(zone.isActive());
    }

    /**
     * Test zone with invalid transport lines data associated
     */
    @Test
    @Transactional
    public void saveWithInvalidLines() throws Exception {
        ZoneDTO zone = new ZoneDTO(new Zone(null, NEW_NAME, NEW_LINES_INVALID, true));
        String jsonZone = TestUtil.json(zone);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URI, jsonZone, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Invalid transport line data associated!");
    }

    /**
     * Test zone with not unique name
     */
    @Test
    @Transactional
    public void saveWithInvalidName() throws Exception {
        ZoneDTO zone = new ZoneDTO(new Zone(null, NEW_NAME, new HashSet<>(), true));
        String jsonZone = TestUtil.json(zone);

        ResponseEntity<ZoneDTO> result1 = testRestTemplate.postForEntity(this.URI, jsonZone, ZoneDTO.class);

        ZoneDTO body1 = result1.getBody();

        assertThat(result1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body1).isNotNull();

        zone = new ZoneDTO(new Zone(null, NEW_NAME, NEW_LINES, true));
        jsonZone = TestUtil.json(zone);

        ResponseEntity<String> result2 = testRestTemplate.postForEntity(this.URI, jsonZone, String.class);

        String body2 = result2.getBody();

        assertThat(result2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body2).isNotNull();

        assertThat(body2).isEqualTo("Zone with given name already exist!");
    }

    /**
     * Test valid zone deletion
     */
    @Test
    @Transactional
    public void delete() throws Exception {
        ZoneDTO zone = new ZoneDTO(new Zone(DEL_ID, DB_NAME, new HashSet<>(), true));
        String jsonZone = TestUtil.json(zone);

        //ResponseEntity<String> result = testRestTemplate.(this.URI, jsonZone, String.class);

//        String body = result.getBody();
//
//        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(body).isNotNull();
//        assertThat(body).isEqualTo("Zone successfully deleted!");
    }
}