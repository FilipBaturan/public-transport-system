package construction_and_testing.public_transport_system.service.definition;

import construction_and_testing.public_transport_system.domain.TransportLine;

import java.util.List;
import java.util.Optional;

public interface TransportLineService {

    /**
     * @return all available transport lines in database
     */
    List<TransportLine> getAll();

    /**
     * @param id of requested transport line
     * @return transport lines  with requested id
     */
    TransportLine findById(Long id);

    /**
     * @param transportLine that needs to be saved
     * @return saved transport line  in database
     */
    TransportLine save(TransportLine transportLine);

    /**
     * @param id of transport line that needs to be removed
     */
    void remove(Long id);
}
