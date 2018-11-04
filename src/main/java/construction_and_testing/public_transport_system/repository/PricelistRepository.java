package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.Pricelist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricelistRepository extends JpaRepository<Pricelist, Long> {
}
