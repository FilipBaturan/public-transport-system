package construction_and_testing.public_transport_system.service;

import construction_and_testing.public_transport_system.domain.Station;

import java.util.List;

public interface StationService {

    /**
     * @return all available stations in database
     */
    List<Station> getAll();

    /**
     * @param id of requested station
     * @return station with requested id
     */
    Station findById(Long id);

    /**
     * @param station that needs to be saved
     * @return saved station in database
     */
    Station save(Station station);

    /**
     * @param id of station that needs to be removed
     */
    void remove(Long id);
}

