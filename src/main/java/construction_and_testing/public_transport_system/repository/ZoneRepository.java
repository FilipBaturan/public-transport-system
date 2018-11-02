package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
}
