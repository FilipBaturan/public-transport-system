package construction_and_testing.public_transport_system.service.integration;

import construction_and_testing.public_transport_system.domain.Vehicle;
import construction_and_testing.public_transport_system.service.definition.VehicleService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static construction_and_testing.public_transport_system.constants.VehicleConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VehicleServiceImplIntegrationTest {

    @Autowired
    private VehicleService vehicleService;

    /**
     * Test get all vehicles from database
     */
    @Test
    public void getAll() {
        List<Vehicle> vehicles = vehicleService.getAll();
        assertThat(vehicles).hasSize(DB_COUNT);
    }

    /**
     * Test with valid id
     */
    @Test
    public void findById() {
        Vehicle dbVehicle = vehicleService.findById(DB_ID);
        assertThat(dbVehicle).isNotNull();

        assertThat(dbVehicle.getId()).isEqualTo(DB_ID);
        assertThat(dbVehicle.getName()).isEqualTo(DB_NAME);
        assertThat(dbVehicle.getType()).isEqualTo(DB_TYPE);
        assertThat(dbVehicle.getCurrentLine().getId()).isEqualTo(DB_LINE);
        assertThat(dbVehicle.isActive()).isEqualTo(DB_ACTIVE);
    }

    /**
     * Test with invalid id
     */
    @Test(expected = GeneralException.class)
    public void findByInvalidId() {
        vehicleService.findById(DB_ID_INVALID);
    }

    /**
     * Test with null id
     */
    @Test(expected = GeneralException.class)
    public void findByNullId() {
        vehicleService.findById(null);
    }

    /**
     * Test valid vehicle saving
     */
    @Test
    @Transactional
    public void save() {
        Vehicle vehicle = new Vehicle(null, NEW_NAME, NEW_TYPE, NEW_LINE, true);
        int countBefore = vehicleService.getAll().size();

        Vehicle dbVehicle = vehicleService.save(vehicle);
        assertThat(dbVehicle).isNotNull();

        assertThat(vehicleService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbVehicle.getName()).isEqualTo(vehicle.getName());
        assertThat(dbVehicle.getType()).isEqualTo(vehicle.getType());
        assertThat(dbVehicle.getCurrentLine().getId()).isEqualTo(vehicle.getCurrentLine().getId());
        assertThat(dbVehicle.isActive()).isEqualTo(vehicle.isActive());
    }

    /**
     * Test with invalid vehicle type and transport line type
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void saveWithInvalidType() {
        Vehicle vehicle = new Vehicle(null, NEW_NAME, NEW_TYPE_INVALID, NEW_LINE, true);
        vehicleService.save(vehicle);
    }

    /**
     * Test with invalid transport line associated
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void saveWithInvalidLine() {
        Vehicle vehicle = new Vehicle(null, NEW_NAME, NEW_TYPE, NEW_LINE_INVALID, true);
        vehicleService.save(vehicle);
    }

    /**
     * Test with null transport line
     */
    @Test
    @Transactional
    public void saveWithNullLine() {
        Vehicle vehicle = new Vehicle(null, NEW_NAME, NEW_TYPE, null, true);
        int countBefore = vehicleService.getAll().size();

        Vehicle dbVehicle = vehicleService.save(vehicle);
        assertThat(dbVehicle).isNotNull();

        assertThat(vehicleService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbVehicle.getName()).isEqualTo(vehicle.getName());
        assertThat(dbVehicle.getType()).isEqualTo(vehicle.getType());
        assertThat(dbVehicle.getCurrentLine()).isNull();
        assertThat(dbVehicle.isActive()).isEqualTo(vehicle.isActive());
    }

    /**
     * Test with null values
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void saveWithNullValues() {
        Vehicle vehicle = new Vehicle(null, null, null, NEW_LINE, true);
        vehicleService.save(vehicle);
    }

    /**
     * Test with to short firstName value
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void saveWithShortName() {
        Vehicle vehicle = new Vehicle(null, NEW_NAME_SHORT_LENGTH, NEW_TYPE, NEW_LINE, true);
        vehicleService.save(vehicle);
    }

    /**
     * Test with too long firstName value
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void saveWithLongName() {
        Vehicle vehicle = new Vehicle(null, NEW_NAME_LONG_LENGTH, NEW_TYPE, NEW_LINE, true);
        vehicleService.save(vehicle);
    }

    /**
     * Test with min length firstName value
     */
    @Test
    @Transactional
    public void saveWithMinLengthName() {
        Vehicle vehicle = new Vehicle(DB_ID, NEW_NAME_MIN_LENGTH, NEW_TYPE, NEW_LINE, true);
        int countBefore = vehicleService.getAll().size();

        Vehicle dbVehicle = vehicleService.save(vehicle);
        assertThat(dbVehicle).isNotNull();

        assertThat(vehicleService.getAll().size()).isEqualTo(countBefore);
        assertThat(dbVehicle.getName()).isEqualTo(vehicle.getName());
        assertThat(dbVehicle.getType()).isEqualTo(vehicle.getType());
        assertThat(dbVehicle.getCurrentLine().getId()).isEqualTo(vehicle.getCurrentLine().getId());
        assertThat(dbVehicle.isActive()).isEqualTo(vehicle.isActive());
    }

    /**
     * Test with max length firstName value
     */
    @Test
    @Transactional
    public void saveWithMaxLengthName() {
        Vehicle vehicle = new Vehicle(null, NEW_NAME_MAX_LENGTH, NEW_TYPE, NEW_LINE, true);
        int countBefore = vehicleService.getAll().size();

        Vehicle dbVehicle = vehicleService.save(vehicle);
        assertThat(dbVehicle).isNotNull();

        assertThat(vehicleService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbVehicle.getName()).isEqualTo(vehicle.getName());
        assertThat(dbVehicle.getType()).isEqualTo(vehicle.getType());
        assertThat(dbVehicle.getCurrentLine().getId()).isEqualTo(vehicle.getCurrentLine().getId());
        assertThat(dbVehicle.isActive()).isEqualTo(vehicle.isActive());
    }

    /**
     * Test valid vehicle deletion
     */
    @Test
    @Transactional
    public void remove() {
        int countBefore = vehicleService.getAll().size();

        vehicleService.remove(DEL_ID);
        assertThat(vehicleService.getAll().size()).isEqualTo(countBefore - 1);
        Vehicle vehicle = vehicleService.findById(DEL_ID);
        assertThat(vehicle.isActive()).isFalse();
    }

    /**
     * Test vehicle deletion that does not exist in database
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void removeWithInvalidId() {
        vehicleService.remove(DEL_ID_INVALID);
    }
}