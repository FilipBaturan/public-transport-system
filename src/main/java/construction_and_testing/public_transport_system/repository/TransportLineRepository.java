package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.TransportLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportLineRepository extends JpaRepository<TransportLine, Long> {


}
