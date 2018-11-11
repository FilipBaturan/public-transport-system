package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduelRepository extends JpaRepository<Schedule,Long> {
}
