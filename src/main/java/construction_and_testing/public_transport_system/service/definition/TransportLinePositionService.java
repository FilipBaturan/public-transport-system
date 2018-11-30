package construction_and_testing.public_transport_system.service.definition;

import construction_and_testing.public_transport_system.domain.TransportLinePosition;

import java.util.List;

public interface TransportLinePositionService {

    /**
     * @return all available transport line positions in database
     */
    List<TransportLinePosition> getAll();

    /**
     * @param id of requested transport line position
     * @return transport line position with requested id
     */
    TransportLinePosition findById(Long id);

    /**
     * @param transportLinePosition that needs to be saved
     * @return saved transport line position in database
     */
    TransportLinePosition save(TransportLinePosition transportLinePosition);

    /**
     * @param id of transport line position that needs to be removed
     */
    void remove(Long id);
}
