package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.domain.DTO.VehicleDTO;
import construction_and_testing.public_transport_system.domain.DTO.VehicleSaverDTO;
import construction_and_testing.public_transport_system.domain.Vehicle;
import construction_and_testing.public_transport_system.service.definition.VehicleService;
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

    @Before
    public void setUp() throws Exception {
        Mockito.doNothing().when(vehicleController).validateJSON(any(String.class), any(String.class));
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
    public void save() throws Exception {
        VehicleSaverDTO vehicle = new VehicleSaverDTO(new Vehicle(null, NEW_NAME, NEW_TYPE, NEW_LINE, true));
        String jsonVehicle = TestUtil.json(vehicle);

        ResponseEntity<VehicleDTO> result = testRestTemplate.postForEntity(this.URL, jsonVehicle, VehicleDTO.class);

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
    public void saveWithInvalidType() throws Exception {
        VehicleSaverDTO vehicle = new VehicleSaverDTO(
                new Vehicle(null, NEW_NAME, NEW_TYPE_INVALID, NEW_LINE, true));
        String jsonVehicle = TestUtil.json(vehicle);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URL, jsonVehicle, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Invalid transport line and vehicle types!");
    }

    /**
     * Test with invalid transport line associated
     */
    @Test
    public void saveWithInvalidLine() throws Exception {
        VehicleSaverDTO vehicle = new VehicleSaverDTO(
                new Vehicle(null, NEW_NAME, NEW_TYPE_INVALID, NEW_LINE_INVALID, true));
        String jsonVehicle = TestUtil.json(vehicle);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URL, jsonVehicle, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Invalid transport line data associated!");
    }

    /**
     * Test with null transport line
     */
    @Test
    public void saveWithNullLine() throws Exception {
        VehicleSaverDTO vehicle = new VehicleSaverDTO(
                new Vehicle(null, NEW_NAME, NEW_TYPE_INVALID, null, true));
        String jsonVehicle = TestUtil.json(vehicle);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URL, jsonVehicle, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Invalid transport line and vehicle types!");
    }

    /**
     * Test with null values
     */
    @Test
    public void saveWithNullValues() throws Exception {
        VehicleSaverDTO vehicle = new VehicleSaverDTO(
                new Vehicle(null, null, null, NEW_LINE, true));
        String jsonVehicle = TestUtil.json(vehicle);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URL, jsonVehicle, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Invalid transport line and vehicle types!");
    }

    /**
     * Test with to short name value
     */
    @Test
    public void saveWithShortName() throws Exception {
        VehicleSaverDTO vehicle = new VehicleSaverDTO(
                new Vehicle(null, NEW_NAME_SHORT_LENGTH, NEW_TYPE, NEW_LINE, true));
        String jsonVehicle = TestUtil.json(vehicle);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URL, jsonVehicle, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
    }

    /**
     * Test with too long name value
     */
    @Test
    public void saveWithLongName() throws Exception {
        VehicleSaverDTO vehicle =
                new VehicleSaverDTO(new Vehicle(null, NEW_NAME_LONG_LENGTH, NEW_TYPE, NEW_LINE, true));
        String jsonVehicle = TestUtil.json(vehicle);

        ResponseEntity<String> result = testRestTemplate.postForEntity(this.URL, jsonVehicle, String.class);

        String body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(body).isNotNull();
    }

    /**
     * Test with min length name value
     */
    @Test
    public void saveWithMinLengthName() throws Exception {
        VehicleSaverDTO vehicle = new VehicleSaverDTO(
                new Vehicle(null, NEW_NAME_MIN_LENGTH, NEW_TYPE, NEW_LINE, true));
        String jsonVehicle = TestUtil.json(vehicle);

        ResponseEntity<VehicleDTO> result = testRestTemplate.postForEntity(this.URL, jsonVehicle, VehicleDTO.class);

        VehicleDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(vehicle.getName());
        assertThat(body.getType()).isEqualTo(vehicle.getType());
        assertThat(body.getCurrentLine().getId()).isEqualTo(vehicle.getCurrentLine());
        assertThat(body.isActive()).isEqualTo(vehicle.isActive());
    }

    /**
     * Test with max length name value
     */
    @Test
    public void saveWithMaxLengthName() throws Exception {
        VehicleSaverDTO vehicle = new VehicleSaverDTO(
                new Vehicle(null, NEW_NAME_MAX_LENGTH, NEW_TYPE, NEW_LINE, true));
        String jsonVehicle = TestUtil.json(vehicle);

        ResponseEntity<VehicleDTO> result = testRestTemplate.postForEntity(this.URL, jsonVehicle, VehicleDTO.class);

        VehicleDTO body = result.getBody();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(vehicle.getName());
        assertThat(body.getType()).isEqualTo(vehicle.getType());
        assertThat(body.getCurrentLine().getId()).isEqualTo(vehicle.getCurrentLine());
        assertThat(body.isActive()).isEqualTo(vehicle.isActive());
    }


    /**
     * Test valid vehicle deletion
     */
    @Test
    public void delete() {
        testRestTemplate.delete(this.URL + "/" + DEL_ID, String.class);
    }

    /**
     * Test vehicle deletion that does not exist in database
     */
    @Test
    public void deleteWithInvalidId() {
        testRestTemplate.delete(this.URL + "/" + DEL_ID_INVALID, String.class);
    }
}