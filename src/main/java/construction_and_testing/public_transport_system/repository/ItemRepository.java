package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
