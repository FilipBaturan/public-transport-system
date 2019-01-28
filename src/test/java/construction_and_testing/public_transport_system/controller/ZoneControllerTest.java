package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.domain.DTO.AuthenticationRequestDTO;
import construction_and_testing.public_transport_system.domain.DTO.AuthenticationResponseDTO;
import construction_and_testing.public_transport_system.domain.DTO.ZoneDTO;
import construction_and_testing.public_transport_system.domain.Zone;
import construction_and_testing.public_transport_system.service.definition.ZoneService;
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

import java.util.HashSet;

import static construction_and_testing.public_transport_system.constants.ZoneConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ZoneControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ZoneService zoneService;

    @SpyBean
    private ZoneController zoneController;

    private final String URL = "/api/zone";

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
        Mockito.doNothing().when(zoneController).validateJSON(any(String.class), any(String.class));
        ResponseEntity<AuthenticationResponseDTO> responseEntity = testRestTemplate.postForEntity("/api/user/auth",
                new AuthenticationRequestDTO("null", "null"),
                AuthenticationResponseDTO.class);
        if (responseEntity.getBody() != null) {
            accessToken = responseEntity.getBody().getToken();
        }
    }

    /**
     * Test get all zone from database
     */
    @Test
    public void getAll() {
        ResponseEntity<ZoneDTO[]> result = testRestTemplate
                .getForEntity(this.URL, ZoneDTO[].class);

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
                .getForEntity(this.URL + "/" + DB_ID, ZoneDTO.class);

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
                .getForEntity(this.URL + "/" + DB_ID_INVALID, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Requested zone does not exist!");
    }

    /**
     * Test with no transport lines
     */
    @Test
    public void saveWithNoLines() {
        ZoneDTO zone = new ZoneDTO(new Zone(null, NEW_NAME, new HashSet<>(), true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<ZoneDTO> httpEntity = new HttpEntity<>(zone, headers);

        ResponseEntity<ZoneDTO> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                ZoneDTO.class);

        ZoneDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(zone.getName());
        assertThat(body.getLines()).isEqualTo(zone.getLines());
        assertThat(body.isActive()).isEqualTo(zone.isActive());
    }

    /**
     * Test with transport lines
     */
    @Test
    public void saveWithLines() {
        ZoneDTO zone = new ZoneDTO(new Zone(null, NEW_NAME, NEW_LINES, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<ZoneDTO> httpEntity = new HttpEntity<>(zone, headers);

        ResponseEntity<ZoneDTO> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                ZoneDTO.class);

        ZoneDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(zone.getName());
        assertThat(body.getLines()).hasSize(zone.getLines().size());
        assertThat(body.isActive()).isEqualTo(zone.isActive());
    }

    /**
     * Test with invalid transport lines data associated
     */
    @Test
    public void saveWithInvalidLines() {
        ZoneDTO zone = new ZoneDTO(new Zone(null, NEW_NAME, NEW_LINES_INVALID, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<ZoneDTO> httpEntity = new HttpEntity<>(zone, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Invalid transport line data associated!");
    }

    /**
     * Test null transport lines
     */
    @Test
    public void saveWithNullLines() {
        ZoneDTO zone = new ZoneDTO(new Zone(null, NEW_NAME, null, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<ZoneDTO> httpEntity = new HttpEntity<>(zone, headers);

        ResponseEntity<ZoneDTO> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                ZoneDTO.class);

        ZoneDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(zone.getName());
        assertThat(body.getLines()).hasSize(0);
        assertThat(body.isActive()).isEqualTo(zone.isActive());
    }

    /**
     * Test null values
     */
    @Test
    public void saveWithNullValues() {
        ZoneDTO zone = new ZoneDTO(new Zone(null, null, NEW_LINES, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<ZoneDTO> httpEntity = new HttpEntity<>(zone, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Zone with given name already exist!");
    }

    /**
     * Test with not unique firstName
     */
    @Test
    public void saveWithInvalidName() {
        ZoneDTO zone = new ZoneDTO(new Zone(null, NEW_NAME, new HashSet<>(), true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<ZoneDTO> httpEntity = new HttpEntity<>(zone, headers);

        ResponseEntity<ZoneDTO> result1 = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                ZoneDTO.class);

        ZoneDTO body1 = result1.getBody();

        assertThat(result1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body1).isNotNull();

        zone = new ZoneDTO(new Zone(null, NEW_NAME, NEW_LINES, true));
        httpEntity = new HttpEntity<>(zone, headers);

        ResponseEntity<String> result2 = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                String.class);

        String body2 = result2.getBody();

        assertThat(result2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body2).isNotNull();

        assertThat(body2).isEqualTo("Zone with given name already exist!");
    }

    /**
     * Test with to short firstName value
     */
    @Test
    public void saveWithShortName() {
        ZoneDTO zone = new ZoneDTO(new Zone(null, NEW_NAME_SHORT_LENGTH, NEW_LINES, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<ZoneDTO> httpEntity = new HttpEntity<>(zone, headers);

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
        ZoneDTO zone = new ZoneDTO(new Zone(null, NEW_NAME_LONG_LENGTH, NEW_LINES, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<ZoneDTO> httpEntity = new HttpEntity<>(zone, headers);

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
        ZoneDTO zone = new ZoneDTO(new Zone(null, NEW_NAME_MIN_LENGTH, NEW_LINES, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<ZoneDTO> httpEntity = new HttpEntity<>(zone, headers);

        ResponseEntity<ZoneDTO> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                ZoneDTO.class);

        ZoneDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(zone.getName());
        assertThat(body.getLines()).hasSize(zone.getLines().size());
        assertThat(body.isActive()).isEqualTo(zone.isActive());
    }

    /**
     * Test with max length firstName value
     */
    @Test
    public void saveWithMaxLengthName() {
        ZoneDTO zone = new ZoneDTO(new Zone(null, NEW_NAME_MAX_LENGTH, NEW_LINES, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<ZoneDTO> httpEntity = new HttpEntity<>(zone, headers);

        ResponseEntity<ZoneDTO> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                ZoneDTO.class);

        ZoneDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(zone.getName());
        assertThat(body.getLines()).hasSize(zone.getLines().size());
        assertThat(body.isActive()).isEqualTo(zone.isActive());
    }

    /**
     * Test unauthorized user tries to save zone
     */
    @Test
    public void saveUnauthorized() {
        setUnauthorizedUser();

        ZoneDTO zone = new ZoneDTO(new Zone(null, NEW_NAME, NEW_LINES, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<ZoneDTO> httpEntity = new HttpEntity<>(zone, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(body).isNotNull();
    }

    /**
     * Test valid zone deletion
     */
    @Test
    public void delete() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<ZoneDTO> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/" + DEL_ID,
                HttpMethod.DELETE, httpEntity, String.class);

        String body = result.getBody();

        assertThat(body).isEqualTo("Zone successfully deleted!");
    }

    /**
     * Test zone deletion that does not exist in database
     */
    @Test
    public void deleteNotValidId() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<ZoneDTO> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/" + DEL_ID_INVALID,
                HttpMethod.DELETE, httpEntity, String.class);

        String body = result.getBody();

        assertThat(body).isEqualTo("Requested zone does not exist!");
    }

    /**
     * Test default zone deletion that can not be deleted
     */
    @Test
    public void deleteDefaultZone() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<ZoneDTO> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/" + DEFAULT_ZONE_ID,
                HttpMethod.DELETE, httpEntity, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Default zone can not be removed!");
    }

    /**
     * Test unauthorized user tries to delete zone
     */
    @Test
    public void deleteUnauthorized() {
        setUnauthorizedUser();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<ZoneDTO> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/" + DEL_ID,
                HttpMethod.DELETE, httpEntity, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(body).isNotNull();
    }
}