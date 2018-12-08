package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query( value = "SELECT * FROM Reservation r WHERE r.active = true AND r.owner_id = ?1", nativeQuery = true)
    List<Reservation> getUsersReservation(Long id);
}
