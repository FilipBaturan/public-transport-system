package construction_and_testing.public_transport_system.service;

import construction_and_testing.public_transport_system.domain.Zone;

import java.util.List;

public interface ZoneService {

    /**
     * @return all available zones in database
     */
    List<Zone> getAll();

    /**
     * @param id of requested zone
     * @return zone with requested id
     */
    Zone findById(Long id);

    /**
     * @param newZone that needs to be saved
     * @return saved zone in database
     */
    Zone add(Zone newZone);

    /**
     * @param updatedZone modified zone that needs to be saved
     * @return modified zone saved in database
     */
    Zone update(Zone updatedZone);

    /**
     * @param id of zone that needs to be removed
     */
    void remove(Long id);
}
