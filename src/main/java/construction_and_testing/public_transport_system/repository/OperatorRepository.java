package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operator, Long> {
}
