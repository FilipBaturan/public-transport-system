package construction_and_testing.public_transport_system.service.definition;

import construction_and_testing.public_transport_system.util.TrackedVehicle;

import java.util.List;

public interface TrackerService {

    /**
     * @return all vehicle with current index position
     */
    List<TrackedVehicle> getAll();

    /**
     * @param vehicles that current positions needs to be update
     */
    void update(List<TrackedVehicle> vehicles);
}
