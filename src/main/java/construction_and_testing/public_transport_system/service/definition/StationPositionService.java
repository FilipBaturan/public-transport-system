package construction_and_testing.public_transport_system.service.definition;

import construction_and_testing.public_transport_system.domain.StationPosition;

import java.util.List;

public interface StationPositionService {

    /**
     * @return all available station positions in database
     */
    List<StationPosition> getAll();

    /**
     * @param id of requested station position
     * @return station position with requested id
     */
    StationPosition findById(Long id);

    /**
     * @param stationPosition that needs to be saved
     * @return saved station position in database
     */
    StationPosition save(StationPosition stationPosition);

    /**
     * @param id of station position that needs to be removed
     */
    void remove(Long id);
}
