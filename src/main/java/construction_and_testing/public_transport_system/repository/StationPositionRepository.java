package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.StationPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationPositionRepository extends JpaRepository<StationPosition,Long> {
}
