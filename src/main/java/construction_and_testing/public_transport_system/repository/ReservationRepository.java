package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
