package construction_and_testing.public_transport_system.service.definition;

import construction_and_testing.public_transport_system.domain.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {

    /**
     * @return all available vehicles in database
     */
    List<Vehicle> getAll();

    /**
     * @param id of requested vehicle
     * @return vehicle with requested id
     */
    Vehicle findById(Long id);

    /**
     * @param vehicle that needs to be saved
     * @return saved vehicle in database
     */
    Vehicle save(Vehicle vehicle);

    /**
     * @param id of vehicle that needs to be removed
     */
    void remove(Long id);
}
