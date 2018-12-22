package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT v from Vehicle v WHERE v.currentLine.id IN :lines")
    List<Vehicle> findByTransportLine(@Param("lines") List<Long> transportLineIds);
}
