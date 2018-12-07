package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.TransportLinePosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportLinePositionRepository extends JpaRepository<TransportLinePosition, Long> {
}
