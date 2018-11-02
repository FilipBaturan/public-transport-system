package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {
}
