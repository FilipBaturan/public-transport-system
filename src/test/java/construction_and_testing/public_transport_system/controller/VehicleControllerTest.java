package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.domain.DTO.AuthenticationRequestDTO;
import construction_and_testing.public_transport_system.domain.DTO.AuthenticationResponseDTO;
import construction_and_testing.public_transport_system.domain.DTO.VehicleDTO;
import construction_and_testing.public_transport_system.domain.DTO.VehicleSaverDTO;
import construction_and_testing.public_transport_system.domain.Vehicle;
import construction_and_testing.public_transport_system.service.definition.VehicleService;
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

import static construction_and_testing.public_transport_system.constants.VehicleConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class VehicleControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private VehicleService vehicleService;

    @SpyBean
    private VehicleController vehicleController;

    private final String URL = "/api/vehicle";

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
        Mockito.doNothing().when(vehicleController).validateJSON(any(String.class), any(String.class));
        ResponseEntity<AuthenticationResponseDTO> responseEntity = testRestTemplate.postForEntity("/api/user/auth",
                new AuthenticationRequestDTO("null", "null"),
                AuthenticationResponseDTO.class);
        if (responseEntity.getBody() != null) {
            accessToken = responseEntity.getBody().getToken();
        }
    }

    /**
     * Test get all vehicle from database
     */
    @Test
    public void findAll() {
        ResponseEntity<VehicleDTO[]> result = testRestTemplate
                .getForEntity(this.URL, VehicleDTO[].class);

        VehicleDTO[] body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body).hasSize(DB_COUNT);
    }

    /**
     * Test with valid id
     */
    @Test
    public void findById() {
        ResponseEntity<VehicleDTO> result = testRestTemplate
                .getForEntity(this.URL + "/" + DB_ID, VehicleDTO.class);

        VehicleDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(body).isNotNull();
        assertThat(body.getId()).isEqualTo(DB_ID);
        assertThat(body.getName()).isEqualTo(DB_NAME);
        assertThat(body.getType()).isEqualTo(DB_TYPE);
        assertThat(body.getCurrentLine().getId()).isEqualTo(DB_LINE);
        assertThat(body.isActive()).isEqualTo(DB_ACTIVE);
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
        assertThat(body).isEqualTo("Requested vehicle does not exist!");
    }

    /**
     * Test valid vehicle saving
     */
    @Test
    public void save() {
        VehicleSaverDTO vehicle = new VehicleSaverDTO(new Vehicle(null, NEW_NAME, NEW_TYPE, NEW_LINE, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<VehicleSaverDTO> httpEntity = new HttpEntity<>(vehicle, headers);

        ResponseEntity<VehicleDTO> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                VehicleDTO.class);

        VehicleDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(vehicle.getName());
        assertThat(body.getType()).isEqualTo(vehicle.getType());
        assertThat(body.getCurrentLine().getId()).isEqualTo(vehicle.getCurrentLine());
        assertThat(body.isActive()).isEqualTo(vehicle.isActive());

    }

    /**
     * Test with invalid vehicle type and transport line type
     */
    @Test
    public void saveWithInvalidType() {
        VehicleSaverDTO vehicle = new VehicleSaverDTO(
                new Vehicle(null, NEW_NAME, NEW_TYPE_INVALID, NEW_LINE, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<VehicleSaverDTO> httpEntity = new HttpEntity<>(vehicle, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Invalid transport line and vehicle types!");
    }

    /**
     * Test with invalid transport line associated
     */
    @Test
    public void saveWithInvalidLine() {
        VehicleSaverDTO vehicle = new VehicleSaverDTO(
                new Vehicle(null, NEW_NAME, NEW_TYPE, NEW_LINE_INVALID, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<VehicleSaverDTO> httpEntity = new HttpEntity<>(vehicle, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Invalid transport line data associated!");
    }

    /**
     * Test with null transport line
     */
    @Test
    public void saveWithNullLine() {
        VehicleSaverDTO vehicle = new VehicleSaverDTO(
                new Vehicle(null, NEW_NAME, NEW_TYPE, null, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<VehicleSaverDTO> httpEntity = new HttpEntity<>(vehicle, headers);

        ResponseEntity<VehicleDTO> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                VehicleDTO.class);

        VehicleDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(vehicle.getName());
        assertThat(body.getType()).isEqualTo(vehicle.getType());
        assertThat(body.getCurrentLine()).isNull();
        assertThat(body.isActive()).isEqualTo(vehicle.isActive());
    }

    /**
     * Test with null values
     */
    @Test
    public void saveWithNullValues() {
        VehicleSaverDTO vehicle = new VehicleSaverDTO(
                new Vehicle(null, null, null, NEW_LINE, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<VehicleSaverDTO> httpEntity = new HttpEntity<>(vehicle, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Invalid transport line and vehicle types!");
    }

    /**
     * Test with to short firstName value
     */
    @Test
    public void saveWithShortName() {
        VehicleSaverDTO vehicle = new VehicleSaverDTO(
                new Vehicle(null, NEW_NAME_SHORT_LENGTH, NEW_TYPE, NEW_LINE, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<VehicleSaverDTO> httpEntity = new HttpEntity<>(vehicle, headers);

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
        VehicleSaverDTO vehicle =
                new VehicleSaverDTO(new Vehicle(null, NEW_NAME_LONG_LENGTH, NEW_TYPE, NEW_LINE, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<VehicleSaverDTO> httpEntity = new HttpEntity<>(vehicle, headers);

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
        VehicleSaverDTO vehicle = new VehicleSaverDTO(
                new Vehicle(null, NEW_NAME_MIN_LENGTH, NEW_TYPE, NEW_LINE, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<VehicleSaverDTO> httpEntity = new HttpEntity<>(vehicle, headers);

        ResponseEntity<VehicleDTO> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                VehicleDTO.class);

        VehicleDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(vehicle.getName());
        assertThat(body.getType()).isEqualTo(vehicle.getType());
        assertThat(body.getCurrentLine().getId()).isEqualTo(vehicle.getCurrentLine());
        assertThat(body.isActive()).isEqualTo(vehicle.isActive());
    }

    /**
     * Test with max length firstName value
     */
    @Test
    public void saveWithMaxLengthName() {
        VehicleSaverDTO vehicle = new VehicleSaverDTO(
                new Vehicle(null, NEW_NAME_MAX_LENGTH, NEW_TYPE, NEW_LINE, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<VehicleSaverDTO> httpEntity = new HttpEntity<>(vehicle, headers);

        ResponseEntity<VehicleDTO> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                VehicleDTO.class);

        VehicleDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(vehicle.getName());
        assertThat(body.getType()).isEqualTo(vehicle.getType());
        assertThat(body.getCurrentLine().getId()).isEqualTo(vehicle.getCurrentLine());
        assertThat(body.isActive()).isEqualTo(vehicle.isActive());
    }

    /**
     * Test unauthorized user tries to save vehicle
     */
    @Test
    public void saveUnauthorized() {
        setUnauthorizedUser();

        VehicleSaverDTO vehicle = new VehicleSaverDTO(new Vehicle(null, NEW_NAME, NEW_TYPE, NEW_LINE, true));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<VehicleSaverDTO> httpEntity = new HttpEntity<>(vehicle, headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, httpEntity,
                String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(body).isNotNull();
    }


    /**
     * Test valid vehicle deletion
     */
    @Test
    public void delete() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<VehicleSaverDTO> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/" + DEL_ID,
                HttpMethod.DELETE, httpEntity, String.class);
        String body = result.getBody();

        assertThat(body).isEqualTo("Vehicle successfully deleted!");
    }

    /**
     * Test vehicle deletion that does not exist in database
     */
    @Test
    public void deleteWithInvalidId() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<VehicleSaverDTO> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/" + DEL_ID_INVALID,
                HttpMethod.DELETE, httpEntity, String.class);
        String body = result.getBody();

        assertThat(body).isEqualTo("Requested vehicle does not exist!");
    }

    /**
     * Test unauthorized user tries to delete vehicle
     */
    @Test
    public void deleteUnauthorized() {
        setUnauthorizedUser();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);
        HttpEntity<VehicleSaverDTO> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> result = testRestTemplate.exchange(this.URL + "/" + DEL_ID,
                HttpMethod.DELETE, httpEntity, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(body).isNotNull();
    }
}