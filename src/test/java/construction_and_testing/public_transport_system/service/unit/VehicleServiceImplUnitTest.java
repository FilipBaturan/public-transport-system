package construction_and_testing.public_transport_system.service.unit;

import construction_and_testing.public_transport_system.domain.Vehicle;
import construction_and_testing.public_transport_system.repository.TransportLineRepository;
import construction_and_testing.public_transport_system.repository.VehicleRepository;
import construction_and_testing.public_transport_system.service.definition.VehicleService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static construction_and_testing.public_transport_system.constants.VehicleConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VehicleServiceImplUnitTest {

    @MockBean
    private VehicleRepository vehicleRepository;

    @MockBean
    private TransportLineRepository transportLineRepository;

    @Autowired
    private VehicleService vehicleService;

    @Before
    public void setUp() throws Exception {
        Mockito.when(transportLineRepository.findById(DB_TR_ID)).thenReturn(Optional.of(DB_TR));
        Mockito.when(transportLineRepository.findById(NEW_LINE_INVALID_ID)).thenReturn(Optional.of(NEW_LINE_INVALID));
        Mockito.when(vehicleRepository.findById(DEL_ID)).thenReturn(Optional.of(DB_VEHICLE))
                .thenReturn(Optional.of(DEL_VEHICLE));
        Mockito.when(vehicleRepository.findById(DEL_ID_INVALID)).thenReturn(Optional.empty());
        Mockito.when(vehicleRepository.findAll()).thenReturn(DB_VEHICLES);
        Mockito.when(vehicleRepository.save(any(Vehicle.class))).then(invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                Vehicle vehicle = (Vehicle) arguments[0];
                if (DEL_ID.equals(vehicle.getId())) {
                    DEL_VEHICLE = vehicle;
                } else {
                    vehicle.setId(NEW_ID);
                    DB_VEHICLES.add(vehicle);
                }
                return vehicle;
            }
            return null;
        });
    }

    /**
     * Test valid vehicle saving
     */
    @Test
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

        Mockito.verify(transportLineRepository, Mockito.times(1)).findById(DB_TR_ID);
        Mockito.verify(vehicleRepository, Mockito.times(1)).save(any(Vehicle.class));
    }

    /**
     * Test with invalid vehicle type and transport line type
     */
    @Test(expected = GeneralException.class)
    public void saveWithInvalidType() {
        Vehicle vehicle = new Vehicle(null, NEW_NAME, NEW_TYPE_INVALID, NEW_LINE, true);
        int countBefore = vehicleService.getAll().size();

        Vehicle dbVehicle = vehicleService.save(vehicle);
        assertThat(dbVehicle).isNotNull();

        assertThat(vehicleService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbVehicle.getName()).isEqualTo(vehicle.getName());
        assertThat(dbVehicle.getType()).isEqualTo(vehicle.getType());
        assertThat(dbVehicle.getCurrentLine().getId()).isEqualTo(vehicle.getCurrentLine().getId());
        assertThat(dbVehicle.isActive()).isEqualTo(vehicle.isActive());

        Mockito.verify(transportLineRepository, Mockito.times(1)).findById(DB_TR_ID);
        Mockito.verify(vehicleRepository, Mockito.never()).save(any(Vehicle.class));
    }

    /**
     * Test with invalid transport line
     */
    @Test(expected = GeneralException.class)
    public void saveWithInvalidLine() {
        Vehicle vehicle = new Vehicle(null, NEW_NAME, NEW_TYPE, NEW_LINE_INVALID, true);
        int countBefore = vehicleService.getAll().size();

        Vehicle dbVehicle = vehicleService.save(vehicle);
        assertThat(dbVehicle).isNotNull();

        assertThat(vehicleService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbVehicle.getName()).isEqualTo(vehicle.getName());
        assertThat(dbVehicle.getType()).isEqualTo(vehicle.getType());
        assertThat(dbVehicle.getCurrentLine().getId()).isEqualTo(vehicle.getCurrentLine().getId());
        assertThat(dbVehicle.isActive()).isEqualTo(vehicle.isActive());

        Mockito.verify(transportLineRepository, Mockito.times(1)).findById(DB_TR_ID);
        Mockito.verify(vehicleRepository, Mockito.never()).save(any(Vehicle.class));
    }

    /**
     * Test with null transport line
     */
    @Test
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

        Mockito.verify(transportLineRepository, Mockito.never()).findById(DB_TR_ID);
        Mockito.verify(vehicleRepository, Mockito.times(1)).save(any(Vehicle.class));
    }

    /**
     * Test with null values
     */
    @Test(expected = GeneralException.class)
    public void saveWithNullValues() {
        Vehicle vehicle = new Vehicle(null, null, null, NEW_LINE, true);
        int countBefore = vehicleService.getAll().size();

        Vehicle dbVehicle = vehicleService.save(vehicle);
        assertThat(dbVehicle).isNotNull();

        assertThat(vehicleService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbVehicle.getName()).isEqualTo(vehicle.getName());
        assertThat(dbVehicle.getType()).isEqualTo(vehicle.getType());
        assertThat(dbVehicle.getCurrentLine().getId()).isEqualTo(vehicle.getCurrentLine().getId());
        assertThat(dbVehicle.isActive()).isEqualTo(vehicle.isActive());

        Mockito.verify(transportLineRepository, Mockito.never()).findById(DB_TR_ID);
        Mockito.verify(vehicleRepository, Mockito.never()).save(any(Vehicle.class));
    }

    /**
     * Test with to short name value
     */
    @Test(expected = GeneralException.class)
    public void saveWithShortName() {
        Vehicle vehicle = new Vehicle(null, NEW_NAME_SHORT_LENGTH, NEW_TYPE, NEW_LINE, true);
        int countBefore = vehicleService.getAll().size();

        Vehicle dbVehicle = vehicleService.save(vehicle);
        assertThat(dbVehicle).isNotNull();

        assertThat(vehicleService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbVehicle.getName()).isEqualTo(vehicle.getName());
        assertThat(dbVehicle.getType()).isEqualTo(vehicle.getType());
        assertThat(dbVehicle.getCurrentLine().getId()).isEqualTo(vehicle.getCurrentLine().getId());
        assertThat(dbVehicle.isActive()).isEqualTo(vehicle.isActive());

        Mockito.verify(transportLineRepository, Mockito.never()).findById(DB_TR_ID);
        Mockito.verify(vehicleRepository, Mockito.never()).save(any(Vehicle.class));
    }

    /**
     * Test with too long name value
     */
    @Test(expected = GeneralException.class)
    public void saveWithLongName() {
        Vehicle vehicle = new Vehicle(null, NEW_NAME_LONG_LENGTH, NEW_TYPE, NEW_LINE, true);
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
     * Test with min length name value
     */
    @Test
    public void saveWithMinLengthName() {
        Vehicle vehicle = new Vehicle(null, NEW_NAME_MIN_LENGTH, NEW_TYPE, NEW_LINE, true);
        int countBefore = vehicleService.getAll().size();

        Vehicle dbVehicle = vehicleService.save(vehicle);
        assertThat(dbVehicle).isNotNull();

        assertThat(vehicleService.getAll().size()).isEqualTo(countBefore + 1);
        assertThat(dbVehicle.getName()).isEqualTo(vehicle.getName());
        assertThat(dbVehicle.getType()).isEqualTo(vehicle.getType());
        assertThat(dbVehicle.getCurrentLine().getId()).isEqualTo(vehicle.getCurrentLine().getId());
        assertThat(dbVehicle.isActive()).isEqualTo(vehicle.isActive());

        Mockito.verify(transportLineRepository, Mockito.times(1)).findById(DB_TR_ID);
        Mockito.verify(vehicleRepository, Mockito.times(1)).save(any(Vehicle.class));
    }

    /**
     * Test with max length name value
     */
    @Test
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

        Mockito.verify(transportLineRepository, Mockito.times(1)).findById(DB_TR_ID);
        Mockito.verify(vehicleRepository, Mockito.times(1)).save(any(Vehicle.class));
    }

    /**
     * Test valid vehicle deletion
     */
    @Test
    public void remove() {
        vehicleService.remove(DEL_ID);

        Mockito.verify(vehicleRepository, Mockito.times(1)).findById(DEL_ID);
        Mockito.verify(vehicleRepository, Mockito.times(1)).save(any(Vehicle.class));

        Vehicle vehicle = vehicleService.findById(DEL_ID);
        assertThat(vehicle.isActive()).isFalse();
    }

    /**
     * Test vehicle deletion that does not exist in database
     */
    @Test(expected = GeneralException.class)
    public void removeWithInvalidID() {
        vehicleService.remove(DEL_ID_INVALID);

        Mockito.verify(vehicleRepository, Mockito.times(1)).findById(DEL_ID_INVALID);
        Mockito.verify(vehicleRepository, Mockito.times(1)).save(any(Vehicle.class));
    }
}