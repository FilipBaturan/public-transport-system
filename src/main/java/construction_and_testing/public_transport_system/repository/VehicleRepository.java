package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VehicleRepository extends JpaRepository<Vehicle, Long> {



}
